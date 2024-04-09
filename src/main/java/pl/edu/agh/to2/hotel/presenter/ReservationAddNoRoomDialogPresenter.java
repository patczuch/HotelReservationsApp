package pl.edu.agh.to2.hotel.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.model.reservation.ReservationDTO;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.view.ExceptionAlert;

public class ReservationAddNoRoomDialogPresenter {
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField phoneNumberField;

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

	}
	public void setData(ReservationDTO reservationDTO) {
		this.reservationDTO = reservationDTO;
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) {
		ReservationDTO reservationDTOBackup = new ReservationDTO(reservationDTO.getReservation());
		try {
			reservationDTO.setCustomerFirstName(firstNameField.getText());
			reservationDTO.setCustomerLastName(lastNameField.getText());
			reservationDTO.setCustomerPhoneNumber(phoneNumberField.getText());
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
