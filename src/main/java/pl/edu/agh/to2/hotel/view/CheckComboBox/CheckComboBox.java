package pl.edu.agh.to2.hotel.view.CheckComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public abstract class CheckComboBox
{
    public static <T> void convertToCheckComboBox(ComboBox<CheckComboBoxItemWrapper<T>> comboBox)
    {
        comboBox.setConverter(new StringConverter<CheckComboBoxItemWrapper<T>>()
        {
            @Override
            public String toString(CheckComboBoxItemWrapper<T> x)
            {
                StringBuilder stringBuilder = new StringBuilder();
                comboBox.getItems().filtered(a -> a != null && a.isChecked())
                        .forEach(q -> stringBuilder.append(", ").append(q.getItem()));
                final String string = stringBuilder.toString();
                if (string.length() > 2) return string.substring(2);
                else return string;
            }

            @Override
            public CheckComboBoxItemWrapper<T> fromString(String x)
            {
                return null;
            }
        });

        comboBox.setCellFactory(x ->
        {
            ListCell<CheckComboBoxItemWrapper<T>> cell = new ListCell<>()
            {
                @Override
                protected void updateItem(CheckComboBoxItemWrapper<T> item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (!empty)
                    {
                        final CheckBox checkBox = new CheckBox(item.toString());
                        checkBox.selectedProperty().bind(item.checkedProperty());
                        setGraphic(checkBox);
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_RELEASED, event ->
            {
                cell.getItem().checkedProperty().set(!cell.getItem().checkedProperty().get());
                StringBuilder stringBuilder = new StringBuilder();
                comboBox.getItems().filtered(a -> a != null && a.isChecked())
                        .forEach(q -> stringBuilder.append(", ").append(q.getItem()));
                final String string = stringBuilder.toString();
                if (string.length() > 2) comboBox.setPromptText(string.substring(2));
                else comboBox.setPromptText(string);
            });

            return cell;
        });
    }

    public static <T> void setItems(ComboBox<CheckComboBoxItemWrapper<T>> comboBox, List<T> items)
    {
        ObservableList<CheckComboBoxItemWrapper<T>> options = FXCollections.observableArrayList(
                items.stream().map(CheckComboBoxItemWrapper<T>::new).toList());
        comboBox.setItems(options);
    }

    public static <T> ArrayList<T> getCheckedItems(ComboBox<CheckComboBoxItemWrapper<T>> comboBox)
    {
        ArrayList<T> checked = new ArrayList<>();
        comboBox.getItems().filtered(x -> x.isChecked()).forEach(x -> checked.add(x.getItem()));
        if(checked.size() == 0) comboBox.getItems().forEach(x -> checked.add(x.getItem()));
        return checked;
    }

    public static <T> void uncheckEverything(ComboBox<CheckComboBoxItemWrapper<T>> comboBox)
    {
        comboBox.getItems().forEach(x -> x.setChecked(false));
        comboBox.setPromptText("");
    }
}
