package pl.edu.agh.to2.hotel.model.room;


import pl.edu.agh.to2.hotel.model.enums.WorldDirection;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomID;
    @Column(unique=true)
    private int roomNumber;
    @Positive
    private int pricePerDay;
    @PositiveOrZero
    private int numberOfSingleBeds;
    @PositiveOrZero
    private int numberOfDoubleBeds;
    private boolean TVpresent;
    @PositiveOrZero
    private int floorNumber; // counting from 0 - ground floor
    private boolean balconyPresent;
    private WorldDirection directionOfWindow;

    public Room() {

    }
    public Room(int roomNumber, int pricePerDay, int numberOfSingleBeds, int numberOfDoubleBeds, boolean TVpresent,
                int floorNumber, boolean balconyPresent, WorldDirection directionOfWindow)
    {
        this.roomNumber = roomNumber;
        this.pricePerDay = pricePerDay;
        this.numberOfSingleBeds = numberOfSingleBeds;
        this.numberOfDoubleBeds = numberOfDoubleBeds;
        this.TVpresent = TVpresent;
        this.floorNumber = floorNumber;
        this.balconyPresent = balconyPresent;
        this.directionOfWindow = directionOfWindow;
    }

    public int getNumberOfSingleBeds() {
        return numberOfSingleBeds;
    }

    public void setNumberOfSingleBeds(int numberOfSingleBeds) {
        this.numberOfSingleBeds = numberOfSingleBeds;
    }

    public int getNumberOfDoubleBeds() {
        return numberOfDoubleBeds;
    }

    public void setNumberOfDoubleBeds(int numberOfDoubleBeds) {
        this.numberOfDoubleBeds = numberOfDoubleBeds;
    }

    public boolean isTVpresent() {
        return TVpresent;
    }

    public void setTVpresent(boolean TVpresent) {
        this.TVpresent = TVpresent;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isBalconyPresent() {
        return balconyPresent;
    }

    public void setBalconyPresent(boolean balconyPresent) {
        this.balconyPresent = balconyPresent;
    }

    public WorldDirection getDirectionOfWindow() {
        return directionOfWindow;
    }

    public void setWindowWorldDirection(WorldDirection directionOfWindow) {
        this.directionOfWindow = directionOfWindow;
    }

    public int getId() {
        return roomID;
    }

    public void setId(int id) {
        this.roomID = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public String toString()
    {
        return "" + roomNumber;
    }

}
