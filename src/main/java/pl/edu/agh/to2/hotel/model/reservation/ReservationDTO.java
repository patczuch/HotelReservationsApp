package pl.edu.agh.to2.hotel.model.reservation;
import javafx.beans.property.*;
import pl.edu.agh.to2.hotel.model.customer.Customer;
import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.room.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationDTO {
    private final SimpleIntegerProperty idProperty;
    private final SimpleObjectProperty<LocalDate> startDateProperty;
    private final SimpleObjectProperty<LocalDate> finishDateProperty;
    private final SimpleObjectProperty<Room> roomProperty;
    private final SimpleStringProperty customerFirstNameProperty;
    private final SimpleStringProperty customerLastNameProperty;
    private final SimpleStringProperty customerPhoneNumberProperty;
    private final Reservation reservation;
    private final SimpleBooleanProperty paidProperty;
    private final SimpleObjectProperty<ReservationState> stateProperty;
    private final SimpleIntegerProperty totalPriceProperty;
    private final SimpleIntegerProperty durationProperty;

    public ReservationDTO(Reservation reservation) {
        this.idProperty = new SimpleIntegerProperty(reservation.getId());
        this.startDateProperty = new SimpleObjectProperty<>(reservation.getStartDate());
        this.finishDateProperty = new SimpleObjectProperty<>(reservation.getFinishDate());
        this.roomProperty = new SimpleObjectProperty<>(reservation.getRoom());
        this.customerFirstNameProperty = new SimpleStringProperty(reservation.getCustomer().getFirstName());
        this.customerLastNameProperty = new SimpleStringProperty(reservation.getCustomer().getLastName());
        this.customerPhoneNumberProperty = new SimpleStringProperty(reservation.getCustomer().getPhoneNumber());
        this.paidProperty = new SimpleBooleanProperty(reservation.isPaid());
        this.stateProperty = new SimpleObjectProperty<>(reservation.getState());
        this.reservation = reservation;
        this.durationProperty = new SimpleIntegerProperty((int) ChronoUnit.DAYS.between(reservation.getStartDate(),reservation.getFinishDate()));
        this.totalPriceProperty = new SimpleIntegerProperty(durationProperty.getValue() * reservation.getRoom().getPricePerDay());
    }

    public Reservation toReservation() {
        reservation.setCustomer(new Customer(getCustomerFirstName(), getCustomerLastName(), getCustomerPhoneNumber()));
        reservation.setRoom(getRoom());
        reservation.setStartDate(getStartDate());
        reservation.setFinishDate(getFinishDate());
        reservation.setPaid(isPaid());
        reservation.setState(getState());
        return reservation;
    }

    public void update() {
        this.idProperty.set(reservation.getId());
        this.startDateProperty.set(reservation.getStartDate());
        this.finishDateProperty.set(reservation.getFinishDate());
        this.roomProperty.set(reservation.getRoom());
        this.customerFirstNameProperty.set(reservation.getCustomer().getFirstName());
        this.customerLastNameProperty.set(reservation.getCustomer().getLastName());
        this.customerPhoneNumberProperty.set(reservation.getCustomer().getPhoneNumber());
        this.paidProperty.set(reservation.isPaid());
        this.stateProperty.set(reservation.getState());
        this.durationProperty.set((int) ChronoUnit.DAYS.between(reservation.getStartDate(),reservation.getFinishDate()));
        this.totalPriceProperty.set(durationProperty.getValue() * reservation.getRoom().getPricePerDay());
    }
    public int getTotalPrice() {
        return totalPriceProperty.get();
    }
    public int getDuration() {
        return durationProperty.get();
    }
    public SimpleIntegerProperty durationProperty() {
        return durationProperty;
    }
    public SimpleIntegerProperty totalPriceProperty() {
        return totalPriceProperty;
    }

    public LocalDate getStartDate() {
        return startDateProperty.get();
    }

    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDateProperty;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDateProperty.set(startDate);
        this.durationProperty.set((int) ChronoUnit.DAYS.between(getStartDate(),getFinishDate()));
        this.totalPriceProperty.set(durationProperty.getValue() * getRoom().getPricePerDay());
    }

    public LocalDate getFinishDate() {
        return finishDateProperty.get();
    }

    public SimpleObjectProperty<LocalDate> finishDateProperty() {
        return finishDateProperty;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDateProperty.set(finishDate);
        this.durationProperty.set((int) ChronoUnit.DAYS.between(getStartDate(),getFinishDate()));
        this.totalPriceProperty.set(durationProperty.getValue() * getRoom().getPricePerDay());
    }

    public Room getRoom() {
        return roomProperty.get();
    }

    public SimpleObjectProperty<Room> roomProperty() {
        return roomProperty;
    }

    public void setRoom(Room roomProperty) {
        this.roomProperty.set(roomProperty);
    }

    public String getCustomerFirstName() {
        return customerFirstNameProperty.get();
    }

    public SimpleStringProperty customerFirstNameProperty() {
        return customerFirstNameProperty;
    }

    public void setCustomerFirstName(String customerFirstNameProperty) {
        this.customerFirstNameProperty.set(customerFirstNameProperty);
    }

    public String getCustomerLastName() {
        return customerLastNameProperty.get();
    }

    public SimpleStringProperty customerLastNameProperty() {
        return customerLastNameProperty;
    }

    public void setCustomerLastName(String customerLastNameProperty) {
        this.customerLastNameProperty.set(customerLastNameProperty);
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumberProperty.get();
    }

    public SimpleStringProperty customerPhoneNumberProperty() {
        return customerPhoneNumberProperty;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumberProperty) {
        this.customerPhoneNumberProperty.set(customerPhoneNumberProperty);
    }

    public SimpleIntegerProperty idProperty() { return idProperty; }

    public boolean isPaid() {
        return paidProperty.get();
    }
    public void setPaid(boolean paid) {this.paidProperty.set(paid);}

    public SimpleBooleanProperty paidProperty() {
        return paidProperty;
    }

    public ReservationState getState() {
        return stateProperty.get();
    }

    public void setState(ReservationState reservationState) {
        this.stateProperty.set(reservationState);
    }

    public SimpleObjectProperty<ReservationState> stateProperty() {
        return stateProperty;
    }

    public int getId() {
        return idProperty.get();
    }
    public Reservation getReservation()
    {
        return this.reservation;
    }
}