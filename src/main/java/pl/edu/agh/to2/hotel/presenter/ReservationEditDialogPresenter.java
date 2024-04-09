package pl.edu.agh.to2.hotel.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.reservation.Reservation;
import pl.edu.agh.to2.hotel.model.reservation.ReservationDTO;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.view.ExceptionAlert;
import pl.edu.agh.to2.hotel.view.ReservationOverviewController;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationEditDialogPresenter {
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private ComboBox<Room> roomField;
	@FXML
	public DatePicker startDatePicker;
	@FXML
	public DatePicker finishDatePicker;
	@FXML
	public CheckBox paidField;
	@FXML
	public ComboBox<ReservationState> stateField;
	private ReservationRepository reservationRepository;
	private RoomRepository roomRepository;
	private ReservationDTO reservationDTO;
	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	public void setRepositories(ReservationRepository reservationRepository, RoomRepository roomRepository) {
		this.reservationRepository = reservationRepository;
		this.roomRepository = roomRepository;
		roomField.setItems(FXCollections.observableArrayList(roomRepository.findAll()));
	}
	public void setData(ReservationDTO reservationDTO) {
		this.reservationDTO = reservationDTO;
		firstNameField.setText(reservationDTO.getCustomerFirstName());
		lastNameField.setText(reservationDTO.getCustomerLastName());
		phoneNumberField.setText(reservationDTO.getCustomerPhoneNumber());
		startDatePicker.setValue(reservationDTO.getStartDate());
		finishDatePicker.setValue(reservationDTO.getFinishDate());
		roomField.setValue(reservationDTO.getRoom());
		paidField.setSelected(reservationDTO.isPaid());
		stateField.setValue(reservationDTO.getState());
		stateField.setItems(FXCollections.observableArrayList(ReservationState.New, ReservationState.Checked_in, ReservationState.Checked_out));
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) {
		ReservationDTO reservationDTOBackup = new ReservationDTO(reservationDTO.getReservation());
		try {
			reservationDTO.setCustomerFirstName(firstNameField.getText());
			reservationDTO.setCustomerLastName(lastNameField.getText());
			reservationDTO.setCustomerPhoneNumber(phoneNumberField.getText());
			reservationDTO.setRoom(roomField.getValue());
			reservationDTO.setStartDate(startDatePicker.getValue());
			reservationDTO.setFinishDate(finishDatePicker.getValue());
			reservationDTO.setPaid(paidField.isSelected());
			reservationDTO.setState(stateField.getValue());
			ArrayList<Reservation> reservationArrayList = reservationRepository.findByRoomRoomNumber(roomField.getValue().getRoomNumber());
			for (Reservation r : reservationArrayList)
				if (r.getId() != reservationDTO.getId())
					if(!finishDatePicker.getValue().isBefore(r.getStartDate())
							&& !startDatePicker.getValue().isAfter(r.getFinishDate())
							&& !finishDatePicker.getValue().isEqual(r.getStartDate())
							&& !r.getFinishDate().isEqual(startDatePicker.getValue()))
						throw new Exception("This room is already reserved between selected dates, reservation id " + r.getId());

			reservationRepository.save(reservationDTO.toReservation());
		} catch (Exception e) {
			reservationDTOBackup.toReservation();
			reservationDTO.update();
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
