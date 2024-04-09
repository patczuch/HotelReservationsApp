package pl.edu.agh.to2.hotel.presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.model.customer.Customer;
import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.reservation.Reservation;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;
import pl.edu.agh.to2.hotel.view.ExceptionAlert;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationAddDialogPresenter {
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	public ComboBox<Room> roomField;
	@FXML
	public DatePicker startDatePicker;
	@FXML
	public DatePicker finishDatePicker;
	private RoomRepository roomRepository;
	private ReservationRepository reservationRepository;
	private Reservation reservation;
	private Stage dialogStage;
	private boolean approved = false;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	public void setRepositories(ReservationRepository reservationRepository, RoomRepository roomRepository) {
		this.reservationRepository = reservationRepository;
		this.roomRepository = roomRepository;
		roomField.setItems(FXCollections.observableArrayList(roomRepository.findAll()));
	}

	@FXML
	void initialize(){
		startDatePicker.setValue(LocalDate.now());
		finishDatePicker.setValue(LocalDate.now().plusDays(1));
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) throws ParseException {
		reservation = new Reservation();
		try {
			reservation.setCustomer(new Customer(firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText()));
			reservation.setRoom(roomField.getValue());
			reservation.setStartDate(startDatePicker.getValue());
			reservation.setFinishDate(finishDatePicker.getValue());
			reservation.setPaid(false);
			reservation.setState(ReservationState.New);
			ArrayList<Reservation> reservationArrayList = reservationRepository.findByRoomRoomNumber(roomField.getValue().getRoomNumber());
			LocalDate s = startDatePicker.getValue();
			LocalDate e = finishDatePicker.getValue();
			for (Reservation r : reservationArrayList)
			{
				if(!(r.getStartDate().compareTo(e) >= 0 || s.compareTo(r.getFinishDate()) >= 0))
					throw new Exception("This room is already reserved between selected dates.");
			}
			reservationRepository.save(reservation);
		} catch (Exception e) {
			new ExceptionAlert(e).showAndWait();
			return;
		}
		approved = true;
		dialogStage.close();
	}

	public Reservation getReservation() {
		return reservation;
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
