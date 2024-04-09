package pl.edu.agh.to2.hotel.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;

import java.io.IOException;

@Component
public class HotelAppController {
    private Stage primaryStage;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public HotelAppController(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public void initRootLayout() {
        try {
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Reservations in Our Hotel");

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/RootOverviewPane.fxml"));
            TabPane rootLayout = loader.load();

            // load the controller
            RootOverviewController controller = loader.getController();
            controller.setAppController(this);
            controller.setRepositories(roomRepository, reservationRepository);
            controller.init();

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> {
                primaryStage.close();
                Platform.exit();
                System.exit(0);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }
}
