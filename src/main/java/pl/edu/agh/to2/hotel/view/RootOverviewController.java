package pl.edu.agh.to2.hotel.view;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;

public class RootOverviewController {
    @FXML
    public BorderPane rooms;
    @FXML
    public BorderPane reservations;
    @FXML
    public BorderPane search;

    private HotelAppController appController;
    @FXML
    private TabPane root;
    @FXML
    private Tab reservationsTab;
    @FXML
    private Tab roomsTab;
    @FXML
    private Tab searchTab;
    @FXML
    private ReservationOverviewController reservationsController;
    @FXML
    private RoomOverviewController roomsController;
    @FXML
    private RoomSearchController searchController;
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;

    public void init() {
        reservationsController.setAppController(appController);
        reservationsController.setRepositories(roomRepository, reservationRepository);
        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        root.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable,
                                                                        Tab oldValue, Tab newValue) -> {
            if (newValue == reservationsTab)
            {
                reservationsController.setAppController(appController);
                reservationsController.setRepositories(roomRepository, reservationRepository);
            }
            else if (newValue == roomsTab)
            {
                roomsController.setAppController(appController);
                roomsController.setRoomRepository(roomRepository);
            }
            else if (newValue == searchTab)
            {
                searchController.setAppController(appController);
                searchController.setRoomRepository(roomRepository, reservationRepository);
            }
        });
    }

    public void setRepositories(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
     }
    public void setAppController(HotelAppController appController) {
        this.appController = appController;
    }

}
