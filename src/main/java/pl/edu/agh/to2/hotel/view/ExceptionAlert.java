package pl.edu.agh.to2.hotel.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ExceptionAlert extends Alert {
    public ExceptionAlert(Exception e) {
        super(AlertType.WARNING, "", ButtonType.OK);
        Throwable t = e;
        while (t.getCause() != null)
            t = t.getCause();
        setContentText(t.getMessage());
    }
}
