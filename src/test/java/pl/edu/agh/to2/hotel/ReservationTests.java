package pl.edu.agh.to2.hotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import pl.edu.agh.to2.hotel.model.enums.ReservationState;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.reservation.Reservation;
import pl.edu.agh.to2.hotel.model.reservation.ReservationDTO;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest()
public class ReservationTests {

    @Autowired
    private RoomRepository roomRepository = null;

    @Autowired
    private ReservationRepository reservationRepository = null;

    @Test
    void testOfReservationDto()
    {
        Room room = new Room(-3, 7, 1, 1, true, 1, false, WorldDirection.East);

        Reservation reservation = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 7),
                LocalDate.of(2023, Month.DECEMBER, 7),
                room,
                "Test",
                "tesT",
                "1111"
        );

        ReservationDTO reservationDTO = new ReservationDTO(reservation);

        assertEquals(reservationDTO.getId(), reservationDTO.idProperty().get());
        assertEquals(reservationDTO.getRoom(), reservationDTO.roomProperty().get());
    }

    @Test
    void testOfFindInReservationRepository()
    {
        Room room = new Room(-20, 7, 1, 1, true, 1, false, WorldDirection.East);
        roomRepository.save(room);

        Reservation reservation = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 7),
                LocalDate.of(2023, Month.DECEMBER, 7),
                room,
                "Test",
                "tesT",
                "1111"
        );
        reservationRepository.save(reservation);

        Example<Reservation> example = Example.of(reservation);

        assertTrue(reservationRepository.findOne(example).isPresent());
    }

    @Test
    @Transactional
    void testOfFindByRoomRoomNumber () {
        Room room1 = new Room(-21, 7, 1, 1, true, 1, false, WorldDirection.East);
        Room room2 = new Room(-22, 18, 3, 0, false, 2, true,  WorldDirection.North);
        roomRepository.save(room1);
        roomRepository.save(room2);

        Reservation reservation1 = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 12),
                LocalDate.of(2023, Month.DECEMBER, 22),
                room1,
                "Sample",
                "Name",
                "123333333"
        );
        Reservation reservation2 = new Reservation(
                LocalDate.of(2023, Month.DECEMBER, 20),
                LocalDate.of(2023, Month.DECEMBER, 23),
                room1,
                "John",
                "Novak",
                "111111111"
        );
        Reservation reservation3 = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 12),
                LocalDate.of(2023, Month.DECEMBER, 22),
                room2,
                "Bob",
                "Smith",
                "222333777"
        );
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);

        List<Reservation> reservationList = reservationRepository.findByRoomRoomNumber(room1.getRoomNumber());
        List<Reservation> goodReservationList = Stream.of(reservation1, reservation2).toList();

        assertEquals(reservationList.size(), goodReservationList.size());
        assertTrue(reservationList.containsAll(goodReservationList)
            && goodReservationList.containsAll(reservationList));
    }

    @Test
    void testOfWrongDate () {
        Room room1 = new Room(-23, 7, 1, 1, true, 1, false, WorldDirection.East);
        roomRepository.save(room1);
        Reservation reservation1 = new Reservation(
                LocalDate.of(2023, Month.DECEMBER, 20),
                LocalDate.of(2023, Month.NOVEMBER, 23),
                room1,
                "John",
                "Novak",
                "111111111"
        );
        Reservation reservation2 = new Reservation(
                LocalDate.of(2023, Month.DECEMBER, 20),
                LocalDate.of(2023, Month.DECEMBER, 17),
                room1,
                "Ann",
                "Murphy",
                "222222222"
        );

        assertThrows(Exception.class, () -> reservationRepository.save(reservation1));
        assertThrows(Exception.class, () -> reservationRepository.save(reservation2));
    }

    @Test
    void testOfRemovingReservation () {
        Room room1 = new Room(-24, 7, 1, 1, true, 1, false, WorldDirection.East);
        roomRepository.save(room1);

        Reservation reservation1 = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 20),
                LocalDate.of(2023, Month.NOVEMBER, 27),
                room1,
                "Name",
                "Surname",
                "987654321"
        );
        reservationRepository.save(reservation1);

        assertDoesNotThrow(() -> reservationRepository.delete(reservation1));

        Example<Reservation> example = Example.of(reservation1);
        assertFalse(reservationRepository.findOne(example).isPresent());
    }

    @Test
    void testOfEditingReservation () {
        Room room1 = new Room(-25, 7, 1, 1, true, 1, false, WorldDirection.East);
        roomRepository.save(room1);

        Reservation reservation1 = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 20),
                LocalDate.of(2023, Month.NOVEMBER, 27),
                room1,
                "Anderw",
                "Tate",
                "750654343"
        );
        reservationRepository.save(reservation1);

        ReservationDTO reservationDTO1 = new ReservationDTO(reservation1);
        reservationDTO1.setCustomerFirstName("Andrew");
        assertDoesNotThrow(() -> reservationRepository.save(reservationDTO1.toReservation()));

        Optional<Reservation> reservation1_ = reservationRepository.findById(reservation1.getId());
        assertFalse(reservation1_.isEmpty());
        assertEquals("Andrew", reservation1_.get().getCustomer().getFirstName());
    }

    @Test
    void testOfCheckingOutPaidReservation () {
        Room room1 = new Room(-26, 4, 4, 0, false, 3, true, WorldDirection.West);
        roomRepository.save(room1);

        Reservation reservation1 = new Reservation(
                LocalDate.of(2024, Month.JANUARY, 10),
                LocalDate.of(2024, Month.JANUARY, 17),
                room1,
                "Name",
                "Surname",
                "987654321"
        );
        reservationRepository.save(reservation1);
        ReservationDTO reservationDTO1 = new ReservationDTO(reservation1);

        reservationDTO1.setPaid(true);
        reservationDTO1.setState(ReservationState.Checked_out);

        assertDoesNotThrow(() -> reservationRepository.save(reservationDTO1.toReservation()));
    }

    @Test
    void testOfCheckingInAndOutUnpaidReservation () {
        Room room1 = new Room(-27, 7, 1, 1, false, 1, false, WorldDirection.North);
        roomRepository.save(room1);

        Reservation reservation1 = new Reservation(
                LocalDate.of(2024, Month.JANUARY, 8),
                LocalDate.of(2024, Month.JANUARY, 14),
                room1,
                "Name",
                "Surname",
                "987654321"
        );
        reservationRepository.save(reservation1);
        ReservationDTO reservationDTO1 = new ReservationDTO(reservation1);

        reservationDTO1.setState(ReservationState.Checked_in);
        assertDoesNotThrow(() -> reservationRepository.save(reservationDTO1.toReservation()));

        reservationDTO1.setState(ReservationState.Checked_out);
        assertThrows(Exception.class, () -> reservationRepository.save(reservationDTO1.toReservation()));
    }

    @Test
    void testOfDaysDurationInReservationDTO() {
        Room room = new Room(-28, 7, 1, 1, true, 1, false, WorldDirection.East);

        Reservation reservation = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 7),
                LocalDate.of(2023, Month.DECEMBER, 7),
                room,
                "Test",
                "tesT",
                "1111"
        );

        ReservationDTO reservationDTO = new ReservationDTO(reservation);

        assertEquals(30, reservationDTO.getDuration());
    }

    @Test
    void testOfTotalPriceInReservationDTO() {
        Room room = new Room(-29, 7, 1, 1, true, 1, false, WorldDirection.East);

        Reservation reservation = new Reservation(
                LocalDate.of(2023, Month.NOVEMBER, 7),
                LocalDate.of(2023, Month.DECEMBER, 7),
                room,
                "Test",
                "tesT",
                "1111"
        );

        ReservationDTO reservationDTO = new ReservationDTO(reservation);

        assertEquals(7 * 30, reservationDTO.getTotalPrice());
    }

}
