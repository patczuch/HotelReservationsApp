package pl.edu.agh.to2.hotel.view;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomDTO;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.presenter.RoomAddDialogPresenter;
import pl.edu.agh.to2.hotel.presenter.RoomEditDialogPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoomOverviewController {

    @FXML
    private ComboBox<Integer> floorNumberField;
    @FXML
    private ComboBox<Integer>  singleBedsField;
    @FXML
    private ComboBox<Integer>  doubleBedsField;
    @FXML
    private ComboBox<Boolean> tvPresentField;
    @FXML
    private ComboBox<Boolean> balconyPresentField;
    @FXML
    private ComboBox<WorldDirection> windowDirectionField;

    @FXML
    private BorderPane rooms;
    private RoomRepository roomRepository;
    private HotelAppController appController;
    @FXML
    private Button addRoomButton;
    @FXML
    private Button editRoomButton;
    @FXML
    private Button deleteRoomButton;
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

        deleteRoomButton.disableProperty().bind(Bindings.isEmpty(roomsTable.getSelectionModel().getSelectedItems()));
        editRoomButton.disableProperty().bind(Bindings.size(roomsTable.getSelectionModel().getSelectedItems()).isNotEqualTo(1));
    }

    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        roomsTable.setItems(FXCollections.observableArrayList(roomRepository.findAll().stream().map(RoomDTO::new).toList()));
        Set<Integer> floorNumbers = new HashSet<>();
        Set<Integer> singleBeds = new HashSet<>();
        Set<Integer> doubleBeds = new HashSet<>();

        for (Room r : roomRepository.findAll())
        {
            floorNumbers.add(r.getFloorNumber());
            singleBeds.add(r.getNumberOfSingleBeds());
            doubleBeds.add(r.getNumberOfDoubleBeds());
        }

        floorNumberField.setItems(FXCollections.observableArrayList(floorNumbers).sorted());
        singleBedsField.setItems(FXCollections.observableArrayList(singleBeds).sorted());
        doubleBedsField.setItems(FXCollections.observableArrayList(doubleBeds).sorted());
        windowDirectionField.setItems(FXCollections.observableArrayList(WorldDirection.North, WorldDirection.East, WorldDirection.South, WorldDirection.West));
        tvPresentField.setItems(FXCollections.observableArrayList(true, false));
        balconyPresentField.setItems(FXCollections.observableArrayList(true, false));
    }
    public void setAppController(HotelAppController appController) {
        this.appController = appController;
    }

    public void handleDeleteRoomAction(ActionEvent actionEvent)
    {
        ArrayList<RoomDTO> roomDTOStoDelete = new ArrayList<>();
        for (RoomDTO roomDTO : roomsTable.getSelectionModel().getSelectedItems()) {
            try {
                roomRepository.delete(roomDTO.getRoom());
            } catch (Exception e) {
                //e.printStackTrace();
                Throwable t = e;
                while (t.getCause() != null)
                    t = t.getCause();
                Alert alert = new Alert(Alert.AlertType.WARNING, "Cant delete room:\n"+t.getMessage(), ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                continue;
            }
            roomDTOStoDelete.add(roomDTO);
        }
        roomsTable.getItems().removeAll(roomDTOStoDelete);
    }

    public void handleEditRoomAction(ActionEvent actionEvent)
    {
        RoomDTO roomDTO = roomsTable.getSelectionModel().getSelectedItem();
        if (roomDTO != null)
            try {
                // Load the fxml file and create a new stage for the dialog
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("view/RoomEditDialog.fxml"));
                BorderPane page = loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit room");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(appController.getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the transaction into the presenter.
                RoomEditDialogPresenter presenter = loader.getController();
                presenter.setRoomRepository(roomRepository);
                presenter.setDialogStage(dialogStage);
                presenter.setData(roomDTO);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void handleAddRoomAction(ActionEvent actionEvent)
    {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/RoomAddDialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New room");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(appController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the transaction into the presenter.
            RoomAddDialogPresenter presenter = loader.getController();
            presenter.setRoomRepository(roomRepository);
            presenter.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            if (presenter.isApproved())
                roomsTable.getItems().add(new RoomDTO(presenter.getRoom()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void handleResetFiltersAction(ActionEvent actionEvent)
    {
        balconyPresentField.valueProperty().set(null);
        floorNumberField.valueProperty().set(null);
        singleBedsField.valueProperty().set(null);
        doubleBedsField.valueProperty().set(null);
        tvPresentField.valueProperty().set(null);
        windowDirectionField.valueProperty().set(null);
    }

    @FXML
    private void handleFilterAction(ActionEvent actionEvent)
    {
        List<RoomDTO> roomDTOS = roomRepository.findAll().stream().map(RoomDTO::new).toList();

        if(balconyPresentField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> x.isBalconyPresent() == balconyPresentField.getValue())
                    .collect(Collectors.toList());

        if(floorNumberField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> x.getFloorNumber() == floorNumberField.getValue())
                    .collect(Collectors.toList());

        if(singleBedsField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> x.getNumberOfSingleBeds() == singleBedsField.getValue())
                    .collect(Collectors.toList());

        if(doubleBedsField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> x.getNumberOfDoubleBeds() == doubleBedsField.getValue())
                    .collect(Collectors.toList());

        if(tvPresentField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> x.isTVPresent() == tvPresentField.getValue())
                    .collect(Collectors.toList());

        if(windowDirectionField.getValue() != null)
            roomDTOS = roomDTOS.stream()
                    .filter(x -> x.getWindowWorldDirection() == windowDirectionField.getValue())
                    .collect(Collectors.toList());


        roomsTable.setItems(FXCollections.observableArrayList(roomDTOS));

    }
}
