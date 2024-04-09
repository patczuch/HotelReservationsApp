package pl.edu.agh.to2.hotel.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.view.ExceptionAlert;

import java.text.ParseException;

public class RoomAddDialogPresenter {

	@FXML
	private TextField roomNumberField;
	@FXML
	private TextField floorNumberField;
	@FXML
	private TextField singleBedsField;
	@FXML
	private TextField doubleBedsField;
	@FXML
	private TextField priceField;
	@FXML
	private CheckBox tvPresentField;
	@FXML
	private CheckBox balconyPresentField;
	@FXML
	private ComboBox<WorldDirection> windowDirectionField;
	private Room room;
	private Stage dialogStage;
	private boolean approved = false;
	private RoomRepository roomRepository;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@FXML
	void initialize(){
		windowDirectionField.setItems(FXCollections.observableArrayList(WorldDirection.North, WorldDirection.East, WorldDirection.South, WorldDirection.West));
	}

	@FXML
	private void handleOkAction(ActionEvent event) throws ParseException {
		room = new Room();
		try {
			room.setRoomNumber(Integer.parseInt(roomNumberField.getText()));
			room.setFloorNumber(Integer.parseInt(floorNumberField.getText()));
			room.setNumberOfSingleBeds(Integer.parseInt(singleBedsField.getText()));
			room.setNumberOfDoubleBeds(Integer.parseInt(doubleBedsField.getText()));
			room.setPricePerDay(Integer.parseInt(priceField.getText()));
			room.setTVpresent(tvPresentField.isSelected());
			room.setBalconyPresent(balconyPresentField.isSelected());
			room.setWindowWorldDirection(windowDirectionField.getValue());
			roomRepository.save(room);
		} catch (Exception e) {
			new ExceptionAlert(e).showAndWait();
			return;
		}
		approved = true;
		dialogStage.close();
	}

	public Room getRoom() {
		return room;
	}

	public boolean isApproved()
	{
		return approved;
	}

	@FXML
	private void handleCancelAction(ActionEvent event) {
		dialogStage.close();
	}
}
