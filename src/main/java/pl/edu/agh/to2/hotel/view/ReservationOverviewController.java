package pl.edu.agh.to2.hotel.view;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.reservation.ReservationDTO;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.presenter.ReservationAddDialogPresenter;
import pl.edu.agh.to2.hotel.presenter.ReservationEditDialogPresenter;

public class ReservationOverviewController {
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;
    private HotelAppController appController;
    @FXML
    private Button editReservationButton;
    @FXML
    private Button addReservationButton;
    @FXML
    private Button deleteReservationButton;
    @FXML
    public Button nextStateButton;
    @FXML
    public Button confirmPaymentButton;
    @FXML
    private TableView<ReservationDTO> reservationsTable;
    @FXML
    private BorderPane reservations;
    @FXML
    private TableColumn<ReservationDTO, Integer> idColumn;
    @FXML
    private TableColumn<ReservationDTO, LocalDate> startDateColumn;
    @FXML
    private TableColumn<ReservationDTO, LocalDate> finishDateColumn;
    @FXML
    private TableColumn<ReservationDTO, Room> roomColumn;
    @FXML
    private TableColumn<ReservationDTO, String> customerFirstNameColumn;
    @FXML
    private TableColumn<ReservationDTO, String> customerLastNameColumn;
    @FXML
    private TableColumn<ReservationDTO, String> customerPhoneNumberColumn;
    @FXML
    public TableColumn<ReservationDTO, Boolean> paidColumn;
    @FXML
    public TableColumn<ReservationDTO, ReservationState> stateColumn;
    @FXML
    public TableColumn<ReservationDTO, Integer> durationColumn;
    @FXML
    public TableColumn<ReservationDTO, Integer> totalPriceColumn;
    @FXML
    private ComboBox<Integer> roomNumberField;
    @FXML
    private ComboBox<ReservationState> reservationStateField;
    @FXML
    private ComboBox<Boolean> paidField;
    @FXML
    private TextField customerLastNameField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField customerFirstNameField;

    @FXML
    private void initialize() {
        reservationsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        idColumn.setCellValueFactory(dataValue -> dataValue.getValue().idProperty().asObject());
        startDateColumn.setCellValueFactory(dataValue -> dataValue.getValue().startDateProperty());
        finishDateColumn.setCellValueFactory(dataValue -> dataValue.getValue().finishDateProperty());
        roomColumn.setCellValueFactory(dataValue -> dataValue.getValue().roomProperty());
        customerFirstNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().customerFirstNameProperty());
        customerLastNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().customerLastNameProperty());
        customerPhoneNumberColumn.setCellValueFactory(dataValue -> dataValue.getValue().customerPhoneNumberProperty());
        paidColumn.setCellValueFactory(dataValue -> dataValue.getValue().paidProperty());
        stateColumn.setCellValueFactory(dataValue -> dataValue.getValue().stateProperty());

        totalPriceColumn.setCellValueFactory(dataValue -> dataValue.getValue().totalPriceProperty().asObject());
        durationColumn.setCellValueFactory(dataValue -> dataValue.getValue().durationProperty().asObject());

        deleteReservationButton.disableProperty().bind(Bindings.isEmpty(reservationsTable.getSelectionModel().getSelectedItems()));
        editReservationButton.disableProperty().bind(Bindings.size(reservationsTable.getSelectionModel().getSelectedItems()).isNotEqualTo(1));


        reservationsTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ReservationDTO>) c -> {
            confirmPaymentButton.setDisable(c.getList().size() != 1 || (c.getList().size() == 1 && c.getList().get(0).isPaid()));
            nextStateButton.setDisable(c.getList().size() != 1 || (c.getList().size() == 1 && c.getList().get(0).getState() == ReservationState.Checked_out));
            ReservationState state = c.getList().get(0).getState().nextState();
            if (state == null) nextStateButton.setText("Guest already left");
            else if(state.equals(ReservationState.Checked_in)) nextStateButton.setText("Check in guest");
            else nextStateButton.setText("Check out guest");
        });
    }

    public void setRepositories(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        reservationsTable.setItems(FXCollections.observableArrayList(reservationRepository.findAll().stream().map(ReservationDTO::new).toList()));

        Set<Integer> roomNumbers = new HashSet<>();
        for (Room r : roomRepository.findAll())
            roomNumbers.add(r.getRoomNumber());

        roomNumberField.valueProperty().set(null);
        startDatePicker.valueProperty().set(null);
        endDatePicker.valueProperty().set(null);
        customerLastNameField.setText("");
        customerPhoneField.setText("");
        paidField.valueProperty().set(null);
        reservationStateField.valueProperty().set(null);

        roomNumberField.setItems(FXCollections.observableArrayList(roomNumbers).sorted());
        reservationStateField.setItems(FXCollections.observableArrayList(ReservationState.New, ReservationState.Checked_in, ReservationState.Checked_out));
        paidField.setItems(FXCollections.observableArrayList(true, false));
    }
    public void setAppController(HotelAppController appController) {
        this.appController = appController;
    }

    public void handleDeleteReservationAction(ActionEvent actionEvent)
    {
        ArrayList<ReservationDTO> reservationDTOStoDelete = new ArrayList<>();
        for (ReservationDTO reservationDTO : reservationsTable.getSelectionModel().getSelectedItems()) {
            try {
                reservationRepository.delete(reservationDTO.getReservation());
            } catch (Exception e) {
                //e.printStackTrace();
                Throwable t = e;
                while (t.getCause() != null)
                    t = t.getCause();
                Alert alert = new Alert(Alert.AlertType.WARNING, "Cant delete reservation:\n"+t.getMessage(), ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                continue;
            }
            reservationDTOStoDelete.add(reservationDTO);
        }
        reservationsTable.getItems().removeAll(reservationDTOStoDelete);
    }

    public void handleEditReservationAction(ActionEvent actionEvent)
    {
        ReservationDTO reservationDTO = reservationsTable.getSelectionModel().getSelectedItem();
        if (reservationDTO != null)
            try {
                // Load the fxml file and create a new stage for the dialog
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("view/ReservationEditDialog.fxml"));
                BorderPane page = loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit reservation");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(appController.getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the transaction into the presenter.
                ReservationEditDialogPresenter presenter = loader.getController();
                presenter.setRepositories(reservationRepository, roomRepository);
                presenter.setDialogStage(dialogStage);
                presenter.setData(reservationDTO);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void handleAddReservationAction(ActionEvent actionEvent)
    {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/ReservationAddDialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(appController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the transaction into the presenter.
            ReservationAddDialogPresenter presenter = loader.getController();
            presenter.setRepositories(reservationRepository, roomRepository);
            presenter.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            if (presenter.isApproved())
                reservationsTable.getItems().add(new ReservationDTO(presenter.getReservation()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFilterAction(ActionEvent actionEvent)
    {
        List<ReservationDTO> reservationDTOS = reservationRepository.findAll().stream().map(ReservationDTO::new).toList();

        if(roomNumberField.getValue() != null)
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> x.getRoom().getRoomNumber() == roomNumberField.getValue())
                    .collect(Collectors.toList());

        if(paidField.getValue() != null)
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> x.isPaid() == paidField.getValue())
                    .collect(Collectors.toList());

        if(reservationStateField.getValue() != null)
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> x.getState() == reservationStateField.getValue())
                    .collect(Collectors.toList());

        if(!customerLastNameField.getText().equals(""))
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> x.getCustomerLastName().toLowerCase().startsWith(customerLastNameField.getText().toLowerCase()))
                    .collect(Collectors.toList());

        if(!customerPhoneField.getText().equals(""))
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> x.getCustomerPhoneNumber().startsWith(customerPhoneField.getText()))
                    .collect(Collectors.toList());

        if(!customerFirstNameField.getText().equals(""))
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> x.getCustomerFirstName().toLowerCase().startsWith(customerFirstNameField.getText().toLowerCase()))
                    .collect(Collectors.toList());

        if(startDatePicker.getValue() != null)
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> !x.getStartDate().isBefore(startDatePicker.getValue()))
                    .collect(Collectors.toList());

        if(endDatePicker.getValue() != null)
            reservationDTOS = reservationDTOS.stream()
                    .filter(x -> !x.getFinishDate().isAfter(endDatePicker.getValue()))
                    .collect(Collectors.toList());

        reservationsTable.setItems(FXCollections.observableArrayList(reservationDTOS));
    }
    @FXML
    private void handleResetFiltersAction(ActionEvent actionEvent)
    {
        roomNumberField.valueProperty().set(null);
        startDatePicker.valueProperty().set(null);
        endDatePicker.valueProperty().set(null);
        customerLastNameField.setText("");
        customerPhoneField.setText("");
        customerFirstNameField.setText("");
        paidField.valueProperty().set(null);
        reservationStateField.valueProperty().set(null);

        handleFilterAction(null);
    }

    @FXML
    private void handleFilterTextAction(KeyEvent keyEvent)
    {
        handleFilterAction(null);
    }

    @FXML
    public void handleConfirmPaymentAction(ActionEvent actionEvent) {
        ReservationDTO reservationDTO = reservationsTable.getSelectionModel().getSelectedItem();
        if (reservationDTO != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm payment for " + reservationDTO.getTotalPrice() + "$?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                reservationDTO.setPaid(true);
                try {
                    reservationRepository.save(reservationDTO.toReservation());
                } catch (Exception e) {
                    reservationDTO.setPaid(false);
                    new ExceptionAlert(e).showAndWait();
                }
                confirmPaymentButton.setDisable(true);
            }
        }
    }

    @FXML
    public void handleNextStateAction(ActionEvent actionEvent) {
        ReservationDTO reservationDTO = reservationsTable.getSelectionModel().getSelectedItem();
        if (reservationDTO != null)
        {
            ReservationState state = reservationDTO.getState();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm " + state.nextState() + "?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                reservationDTO.setState(state.nextState());
                try {
                    reservationRepository.save(reservationDTO.toReservation());
                } catch (Exception e) {
                    reservationDTO.setState(state);
                    new ExceptionAlert(e).showAndWait();
                }
                if (reservationDTO.getState() == ReservationState.Checked_out)
                    nextStateButton.setDisable(true);
            }
            ReservationState buttonState = reservationDTO.getState().nextState();
            if (buttonState == null) nextStateButton.setText("Guest already left");
            else if(buttonState.equals(ReservationState.Checked_in)) nextStateButton.setText("Check in guest");
            else nextStateButton.setText("Check out guest");
        }
    }
}
