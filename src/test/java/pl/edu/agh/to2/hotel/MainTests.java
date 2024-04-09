package pl.edu.agh.to2.hotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.reservation.Reservation;
import pl.edu.agh.to2.hotel.model.reservation.ReservationDTO;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class MainTests {

	@Autowired
	private RoomRepository roomRepository = null;

	@Autowired
	private ReservationRepository reservationRepository = null;

	@Test
	void testOfSpringBeans()
	{
		assertNotNull(roomRepository);
		assertNotNull(reservationRepository);
	}

	@Test
	void testOfReservationState () {
		ReservationState reservationState= ReservationState.New;
		assertEquals(reservationState.nextState(), ReservationState.Checked_in);
		reservationState= reservationState.nextState();

		assertEquals(reservationState.nextState(), ReservationState.Checked_out);
		reservationState= reservationState.nextState();

		assertNull(reservationState.nextState());
		reservationState= reservationState.nextState();

		ReservationState finalReservationState = reservationState;
		assertThrows(NullPointerException.class, () -> finalReservationState.nextState());
	}

}
