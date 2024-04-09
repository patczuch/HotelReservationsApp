package pl.edu.agh.to2.hotel.view.CheckComboBox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CheckComboBoxItemWrapper<T>
{
    private BooleanProperty checked = new SimpleBooleanProperty(false);
    private ObjectProperty<T> item = new SimpleObjectProperty<>();

    CheckComboBoxItemWrapper()
    {

    }

    CheckComboBoxItemWrapper(T item)
    {
        this.item.set(item);
    }

    CheckComboBoxItemWrapper(T item, Boolean checked)
    {
        this.item.set(item);
        this.checked.set(checked);
    }

    public BooleanProperty checkedProperty()
    {
        return checked;
    }

    public Boolean isChecked()
    {
        return checked.getValue();
    }

    public void setChecked(Boolean value)
    {
        checked.set(value);
    }

    public ObjectProperty<T> itemProperty()
    {
        return item;
    }

    public T getItem()
    {
        return item.getValue();
    }

    public void setItem(T value)
    {
        item.setValue(value);
    }

    @Override
    public String toString()
    {
        return item.getValue().toString();
    }
}
