package pl.edu.agh.to2.hotel.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.model.customer.Customer;
import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.reservation.Reservation;
import pl.edu.agh.to2.hotel.model.reservation.ReservationDTO;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomDTO;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.presenter.ReservationAddNoRoomDialogPresenter;
import pl.edu.agh.to2.hotel.view.CheckComboBox.CheckComboBox;
import pl.edu.agh.to2.hotel.view.CheckComboBox.CheckComboBoxItemWrapper;

import java.util.*;
import java.util.stream.Collectors;

public class RoomSearchController {

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<CheckComboBoxItemWrapper<Integer>> floorNumberField;
    @FXML
    private ComboBox<CheckComboBoxItemWrapper<Integer>>  singleBedsField;
    @FXML
    private ComboBox<CheckComboBoxItemWrapper<Integer>>  doubleBedsField;
    @FXML
    private ComboBox<CheckComboBoxItemWrapper<Boolean>> tvPresentField;
    @FXML
    private ComboBox<CheckComboBoxItemWrapper<Boolean>> balconyPresentField;
    @FXML
    private ComboBox<CheckComboBoxItemWrapper<WorldDirection>> windowDirectionField;

    @FXML
    private BorderPane search;
    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;
    private HotelAppController appController;

    @FXML
    private TableView<RoomDTO> roomsTable;
    @FXML
    private TableColumn<RoomDTO, Integer> idColumn;
    @FXML
    private TableColumn<RoomDTO, Integer> roomNumberColumn;
    @FXML
    private TableColumn<RoomDTO, Integer> pricePerDayColumn;
    @FXML
    private TableColumn<RoomDTO, Integer> numberOfSingleBedsColumn;
    @FXML
    private TableColumn<RoomDTO, Integer> numberOfDoubleBedsColumn;
    @FXML
    private TableColumn<RoomDTO, Boolean> TVpresentColumn;
    @FXML
    private TableColumn<RoomDTO, Integer> floorNumberColumn;
    @FXML
    private TableColumn<RoomDTO, Boolean> balconyPresentColumn;
    @FXML
    private TableColumn<RoomDTO, WorldDirection> directionOfWindowColumn;
    @FXML
    private Button addReservationButton;

    private BooleanProperty canReservate = new SimpleBooleanProperty(false);

    @FXML
    private void initialize() {
        roomsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        idColumn.setCellValueFactory(dataValue -> dataValue.getValue().idProperty().asObject());
        roomNumberColumn.setCellValueFactory(dataValue -> dataValue.getValue().roomNumberProperty().asObject());
        pricePerDayColumn.setCellValueFactory(dataValue -> dataValue.getValue().pricePerDayProperty().asObject());
        numberOfSingleBedsColumn.setCellValueFactory(dataValue -> dataValue.getValue().numberOfSingleBedsProperty().asObject());
        numberOfDoubleBedsColumn.setCellValueFactory(dataValue -> dataValue.getValue().numberOfDoubleBedsProperty().asObject());
        TVpresentColumn.setCellValueFactory(dataValue -> dataValue.getValue().TVPresentProperty());
        floorNumberColumn.setCellValueFactory(dataValue -> dataValue.getValue().floorNumberProperty().asObject());
        balconyPresentColumn.setCellValueFactory(dataValue -> dataValue.getValue().balconyPresentProperty());
        directionOfWindowColumn.setCellValueFactory(dataValue -> dataValue.getValue().windowWorldDirectionsProperty());

        addReservationButton.disableProperty().bind((Bindings.size(roomsTable.getSelectionModel().getSelectedItems()).isNotEqualTo(1)).or(canReservate.not()));
        addReservationButton.setAlignment(Pos.CENTER);
    }

    public void setRoomRepository(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        Set<Integer> floorNumbers = new HashSet<>();
        Set<Integer> singleBeds = new HashSet<>();
        Set<Integer> doubleBeds = new HashSet<>();

        endDatePicker.valueProperty().set(null);
        startDatePicker.valueProperty().set(null);

        for (Room r : roomRepository.findAll())
        {
            floorNumbers.add(r.getFloorNumber());
            singleBeds.add(r.getNumberOfSingleBeds());
            doubleBeds.add(r.getNumberOfDoubleBeds());
        }

        CheckComboBox.convertToCheckComboBox(floorNumberField);
        CheckComboBox.setItems(floorNumberField, new ArrayList<>(floorNumbers).stream().sorted().toList());

        CheckComboBox.convertToCheckComboBox(singleBedsField);
        CheckComboBox.setItems(singleBedsField, new ArrayList<>(singleBeds).stream().sorted().toList());

        CheckComboBox.convertToCheckComboBox(doubleBedsField);
        CheckComboBox.setItems(doubleBedsField, new ArrayList<>(doubleBeds).stream().sorted().toList());

        CheckComboBox.convertToCheckComboBox(windowDirectionField);
        CheckComboBox.setItems(windowDirectionField, new ArrayList<>(Arrays.asList(WorldDirection.North, WorldDirection.East, WorldDirection.South, WorldDirection.West)));

        CheckComboBox.convertToCheckComboBox(tvPresentField);
        CheckComboBox.setItems(tvPresentField, new ArrayList<>(Arrays.asList(true, false)));

        CheckComboBox.convertToCheckComboBox(balconyPresentField);
        CheckComboBox.setItems(balconyPresentField, new ArrayList<>(Arrays.asList(true, false)));
    }
    public void setAppController(HotelAppController appController) {
        this.appController = appController;
    }

    @FXML
    private void handleResetFiltersAction(ActionEvent actionEvent)
    {
        CheckComboBox.uncheckEverything(floorNumberField);
        CheckComboBox.uncheckEverything(singleBedsField);
        CheckComboBox.uncheckEverything(doubleBedsField);
        CheckComboBox.uncheckEverything(windowDirectionField);
        CheckComboBox.uncheckEverything(balconyPresentField);
        CheckComboBox.uncheckEverything(tvPresentField);
        handleFilterAction(null);
        canReservate.set(false);
    }

    @FXML
    private void handleFilterAction(ActionEvent actionEvent)
    {
        List<RoomDTO> roomDTOS = roomRepository.findAll().stream().map(RoomDTO::new).toList();

        if (endDatePicker.getValue() == null ||  startDatePicker.getValue() == null)
        {
            if(actionEvent != null)
            {
                new ExceptionAlert(new Exception("Both dates must be set.")).showAndWait();
                return;
            }
        }

        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null)
        {
            if(endDatePicker.getValue().isBefore(startDatePicker.getValue()) && actionEvent != null)
            {
                new ExceptionAlert(new Exception("End date must be after start date.")).showAndWait();
                return;
            }
        }

        canReservate.set(true);

        ArrayList<Boolean> checkedBalconyPresentField = CheckComboBox.getCheckedItems(balconyPresentField);
        if(balconyPresentField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> checkedBalconyPresentField.contains(x.isBalconyPresent()))
                    .collect(Collectors.toList());

        ArrayList<Integer> checkedFloorNumber = CheckComboBox.getCheckedItems(floorNumberField);
        if(floorNumberField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> checkedFloorNumber.contains(x.getFloorNumber()))
                    .collect(Collectors.toList());

        ArrayList<Integer> checkedSingleBedsField = CheckComboBox.getCheckedItems(singleBedsField);
        if(singleBedsField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x ->checkedSingleBedsField.contains(x.getNumberOfSingleBeds()))
                    .collect(Collectors.toList());

        ArrayList<Integer> checkedDoubleBedsField = CheckComboBox.getCheckedItems(doubleBedsField);
        if(doubleBedsField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> checkedDoubleBedsField.contains(x.getNumberOfDoubleBeds()))
                    .collect(Collectors.toList());

        ArrayList<Boolean> checkedTvPresentField = CheckComboBox.getCheckedItems(tvPresentField);
        if(tvPresentField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> checkedTvPresentField.contains(x.isTVPresent()))
                    .collect(Collectors.toList());

        ArrayList<WorldDirection> checkedWindowDirectionField = CheckComboBox.getCheckedItems(windowDirectionField);
        if(windowDirectionField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> checkedWindowDirectionField.contains(x.getWindowWorldDirection()))
                    .collect(Collectors.toList());

        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null)
        {
            roomDTOS = new ArrayList<>(roomDTOS);
            ArrayList<RoomDTO> toDelete = new ArrayList<>();
            for (RoomDTO roomDTO : roomDTOS)
            {
                ArrayList<Reservation> reservationArrayList = reservationRepository.findByRoomRoomNumber(roomDTO.getRoomNumber());
                for (Reservation r : reservationArrayList)
                {
                    if(((r.getStartDate().isBefore(startDatePicker.getValue()) || r.getStartDate().equals(startDatePicker.getValue())) && (r.getFinishDate().isAfter(startDatePicker.getValue()) || r.getFinishDate().equals(startDatePicker.getValue())))
                            || ((r.getStartDate().isBefore(endDatePicker.getValue())) || r.getStartDate().equals(endDatePicker.getValue())) && (r.getStartDate().isAfter(startDatePicker.getValue())) || r.getStartDate().equals(startDatePicker.getValue()))
                    {
                        toDelete.add(roomDTO);
                        break;
                    }
                }
            }
            roomDTOS.removeAll(toDelete);
        }


        roomsTable.setItems(FXCollections.observableArrayList(roomDTOS));

    }

    public void handleAddReservation(ActionEvent actionEvent)
    {
        Reservation reservation = new Reservation();
        reservation.setRoom(roomsTable.getSelectionModel().getSelectedItem().getRoom());
        reservation.setStartDate(startDatePicker.getValue());
        reservation.setFinishDate(endDatePicker.getValue());
        reservation.setCustomer(new Customer());
        reservation.setPaid(false);
        reservation.setState(ReservationState.New);
        ReservationDTO reservationDTO = new ReservationDTO(reservation);

        if (reservationDTO != null)
            try {
                // Load the fxml file and create a new stage for the dialog
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("view/ReservationAddNoRoomDialog.fxml"));
                BorderPane page = loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add reservation");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(appController.getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the transaction into the presenter.
                ReservationAddNoRoomDialogPresenter presenter = loader.getController();
                presenter.setRepositories(reservationRepository, roomRepository);
                presenter.setDialogStage(dialogStage);
                presenter.setData(reservationDTO);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }

        handleFilterAction(null);
    }


    public void handleFiltersChanged(ActionEvent actionEvent)
    {
        canReservate.set(false);
    }
}
