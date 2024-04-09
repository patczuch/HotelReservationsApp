package pl.edu.agh.to2.hotel.model.enums;

public enum ReservationState
{
    New("New"), Checked_in("Checked in"), Checked_out("Checked out");

    private String name;
    private ReservationState(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public ReservationState nextState()
    {
        return switch (this) {
            case New -> Checked_in;
            case Checked_in -> Checked_out;
            case Checked_out -> null;
        };
    }
}
