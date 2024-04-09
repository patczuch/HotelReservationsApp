package pl.edu.agh.to2.hotel.model.room;

import javafx.beans.property.*;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;

public class RoomDTO {
    private final SimpleIntegerProperty idProperty;
    private final SimpleIntegerProperty roomNumberProperty;
    private final SimpleIntegerProperty pricePerDayProperty;
    private final SimpleIntegerProperty numberOfSingleBedsProperty;
    private final SimpleIntegerProperty numberOfDoubleBedsProperty;
    private final BooleanProperty TVPresentProperty;
    private final SimpleIntegerProperty floorNumberProperty;
    private final BooleanProperty balconyPresentProperty;
    private final SimpleObjectProperty<WorldDirection> windowWorldDirectionsProperty;

    public Room getRoom() {
        return room;
    }

    private final Room room;

    public RoomDTO(Room room) {
        this.idProperty = new SimpleIntegerProperty(room.getId());
        this.roomNumberProperty = new SimpleIntegerProperty(room.getRoomNumber());
        this.pricePerDayProperty = new SimpleIntegerProperty(room.getPricePerDay());
        this.numberOfSingleBedsProperty = new SimpleIntegerProperty(room.getNumberOfSingleBeds());
        this.numberOfDoubleBedsProperty = new SimpleIntegerProperty(room.getNumberOfDoubleBeds());
        this.TVPresentProperty = new SimpleBooleanProperty(room.isTVpresent());
        this.floorNumberProperty = new SimpleIntegerProperty(room.getFloorNumber());
        this.balconyPresentProperty = new SimpleBooleanProperty(room.isBalconyPresent());
        this.windowWorldDirectionsProperty = new SimpleObjectProperty<>(room.getDirectionOfWindow());
        this.room = room;
    }

    public void update() {
        this.idProperty.set(room.getId());
        this.roomNumberProperty.set(room.getRoomNumber());
        this.pricePerDayProperty.set(room.getPricePerDay());
        this.numberOfSingleBedsProperty.set(room.getNumberOfSingleBeds());
        this.numberOfDoubleBedsProperty.set(room.getNumberOfDoubleBeds());
        this.TVPresentProperty.set(room.isTVpresent());
        this.floorNumberProperty.set(room.getFloorNumber());
        this.balconyPresentProperty.set(room.isBalconyPresent());
        this.windowWorldDirectionsProperty.set(room.getDirectionOfWindow());
    }

    public Room toRoom() {
        room.setRoomNumber(getRoomNumber());
        room.setPricePerDay(getPricePerDay());
        room.setNumberOfSingleBeds(getNumberOfSingleBeds());
        room.setNumberOfDoubleBeds(getNumberOfDoubleBeds());
        room.setTVpresent(isTVPresent());
        room.setFloorNumber(getFloorNumber());
        room.setBalconyPresent(isBalconyPresent());
        room.setWindowWorldDirection(getWindowWorldDirection());
        return room;
    }

    public int getId() {
        return idProperty.get();
    }

    public SimpleIntegerProperty idProperty() {
        return idProperty;
    }

    public int getRoomNumber() {
        return roomNumberProperty.get();
    }

    public SimpleIntegerProperty roomNumberProperty() {
        return roomNumberProperty;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumberProperty.set(roomNumber);
    }

    public int getPricePerDay() {
        return pricePerDayProperty.get();
    }

    public SimpleIntegerProperty pricePerDayProperty() {
        return pricePerDayProperty;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDayProperty.set(pricePerDay);
    }

    public int getNumberOfSingleBeds() {
        return numberOfSingleBedsProperty.get();
    }

    public SimpleIntegerProperty numberOfSingleBedsProperty() {
        return numberOfSingleBedsProperty;
    }

    public void setNumberOfSingleBeds(int numberOfSingleBeds) {
        this.numberOfSingleBedsProperty.set(numberOfSingleBeds);
    }

    public int getNumberOfDoubleBeds() {
        return numberOfDoubleBedsProperty.get();
    }

    public SimpleIntegerProperty numberOfDoubleBedsProperty() {
        return numberOfDoubleBedsProperty;
    }

    public void setNumberOfDoubleBeds(int numberOfDoubleBeds) {
        this.numberOfDoubleBedsProperty.set(numberOfDoubleBeds);
    }

    public boolean isTVPresent() {
        return TVPresentProperty.get();
    }

    public BooleanProperty TVPresentProperty() {
        return TVPresentProperty;
    }

    public void setTVPresent(boolean TVPresent) {
        this.TVPresentProperty.set(TVPresent);
    }

    public int getFloorNumber() {
        return floorNumberProperty.get();
    }

    public SimpleIntegerProperty floorNumberProperty() {
        return floorNumberProperty;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumberProperty.set(floorNumber);
    }

    public boolean isBalconyPresent() {
        return balconyPresentProperty.get();
    }

    public BooleanProperty balconyPresentProperty() {
        return balconyPresentProperty;
    }

    public void setBalconyPresent(boolean balconyPresent) {
        this.balconyPresentProperty.set(balconyPresent);
    }

    public WorldDirection getWindowWorldDirection() {
        return windowWorldDirectionsProperty.get();
    }

    public SimpleObjectProperty<WorldDirection> windowWorldDirectionsProperty() {
        return windowWorldDirectionsProperty;
    }

    public void setWindowWorldDirection(WorldDirection worldDirection) {
        this.windowWorldDirectionsProperty.set(worldDirection);
    }
}
