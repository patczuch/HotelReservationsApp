package pl.edu.agh.to2.hotel.model.reservation;


import pl.edu.agh.to2.hotel.model.customer.Customer;
import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.room.Room;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationID;
    //@FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;
    //@FutureOrPresent(message = "Finish date must be in the present or future")
    private LocalDate finishDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;
    @Embedded
    private Customer customer;
    @NotNull
    private ReservationState state;
    private boolean paid;
    public Reservation() {

    }

    public Reservation(LocalDate startDate, LocalDate finishDate, Room room, String customerFirstName, String customerLastName, String customerPhoneNumber)
    {
        this(startDate, finishDate, room, new Customer(customerFirstName, customerLastName, customerPhoneNumber));
    }

    public Reservation(LocalDate startDate, LocalDate finishDate, Room room, Customer customer)
    {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.customer = customer;
        this.room = room;
        this.state = ReservationState.New;
        this.paid = false;
    }

    public int getId() {
        return reservationID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PrePersist
    @PostUpdate
    public void validateReservation() {
        if (getStartDate() != null && getFinishDate() != null && (getStartDate().isAfter(getFinishDate()) || getStartDate().equals(getFinishDate()))) {
            throw new IllegalStateException("Invalid reservation: Start date must be before finish date.");
        }
        if (!isPaid() && getState() == ReservationState.Checked_out) {
            throw new IllegalStateException("Can't check out without the reservation being paid for.");
        }
    }

    public ReservationState getState() {
        return state;
    }

    public void setState(ReservationState reservationState) {
        this.state = reservationState;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
