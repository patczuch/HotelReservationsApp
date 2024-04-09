package pl.edu.agh.to2.hotel.model.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>
{
    ArrayList<Reservation> findByRoomRoomNumber(int roomNumber);
}
