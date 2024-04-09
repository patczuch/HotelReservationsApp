package pl.edu.agh.to2.hotel.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.reservation.ReservationDTO;
import pl.edu.agh.to2.hotel.model.room.RoomDTO;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.view.ExceptionAlert;

import java.text.ParseException;

public class RoomEditDialogPresenter {

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
	private RoomDTO roomDTO;
	private RoomRepository roomRepository;
	private ReservationDTO reservationDTO;
	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}
	public void setData(RoomDTO roomDTO) {
		this.roomDTO = roomDTO;
		roomNumberField.setText(Integer.toString(roomDTO.getRoomNumber()));
		floorNumberField.setText(Integer.toString(roomDTO.getFloorNumber()));
		singleBedsField.setText(Integer.toString(roomDTO.getNumberOfSingleBeds()));
		doubleBedsField.setText(Integer.toString(roomDTO.getNumberOfDoubleBeds()));
		priceField.setText(Integer.toString(roomDTO.getPricePerDay()));
		tvPresentField.setSelected(roomDTO.isTVPresent());
		balconyPresentField.setSelected(roomDTO.isBalconyPresent());
		windowDirectionField.setValue(roomDTO.getWindowWorldDirection());
		windowDirectionField.setItems(FXCollections.observableArrayList(WorldDirection.North, WorldDirection.East, WorldDirection.South, WorldDirection.West));
	}

	@FXML
	private void handleOkAction(ActionEvent event) {
		RoomDTO roomDTOBackup = new RoomDTO(roomDTO.getRoom());
		try {
			roomDTO.setRoomNumber(Integer.parseInt(roomNumberField.getText()));
			roomDTO.setFloorNumber(Integer.parseInt(floorNumberField.getText()));
			roomDTO.setNumberOfSingleBeds(Integer.parseInt(singleBedsField.getText()));
			roomDTO.setNumberOfDoubleBeds(Integer.parseInt(doubleBedsField.getText()));
			roomDTO.setPricePerDay(Integer.parseInt(priceField.getText()));
			roomDTO.setTVPresent(tvPresentField.isSelected());
			roomDTO.setBalconyPresent(balconyPresentField.isSelected());
			roomDTO.setWindowWorldDirection(windowDirectionField.getValue());
			roomRepository.save(roomDTO.toRoom());
		} catch (Exception e) {
			roomDTOBackup.toRoom();
			roomDTO.update();
			new ExceptionAlert(e).showAndWait();
			return;
		}
		dialogStage.close();
	}

	@FXML
	private void handleCancelAction(ActionEvent event) {
		dialogStage.close();
	}
}
