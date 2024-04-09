package pl.edu.agh.to2.hotel;

import javafx.application.Platform;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.to2.hotel.view.HotelAppController;

@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        HotelAppController hotelAppController = context.getBean(HotelAppController.class);
        Platform.startup(hotelAppController::initRootLayout);
    }

}