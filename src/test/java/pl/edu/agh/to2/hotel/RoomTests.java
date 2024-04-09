package pl.edu.agh.to2.hotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomDTO;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;

import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoomTests {

    @Autowired
    private RoomRepository roomRepository = null;

    @Test
    void testOfFindInRoomRepository() {
        Room room = new Room(-1, 7, 1, 1, true, 1, false, WorldDirection.East);
        roomRepository.save(room);

        Example<Room> example = Example.of(room);

        assertTrue(roomRepository.findOne(example).isPresent());
    }

    @Test
    void testOfUniqueRoomNumber() {
        Room room1 = new Room(-2, 7, 1, 1, true, 1, false, WorldDirection.East);
        roomRepository.save(room1);

        Room room2 = new Room(-2, 7, 1, 1, true, 1, false, WorldDirection.East);
        assertThrows(DataIntegrityViolationException.class, () -> { roomRepository.save(room2); });
    }

    @Test
    void testOfNegativeAndNonePricePerDay () {
        Room room1 = new Room(-3, 0, 1, 1, true, 1, false, WorldDirection.West);
        assertThrows(ConstraintViolationException.class, () -> roomRepository.save(room1));

        Room room2 = new Room(-4, -7, 1, 1, true, 1, false, WorldDirection.West);
        assertThrows(ConstraintViolationException.class, () -> roomRepository.save(room2));
    }

    @Test
    void testOfNegativeNumberOfSingleBeds () {
        Room room1 = new Room(-5, 7, -4, 1, true, 1, false, WorldDirection.West);
        assertThrows(ConstraintViolationException.class, () -> roomRepository.save(room1));
    }

    @Test
    void testOfNegativeNumberOfDoubleBeds () {
        Room room1 = new Room(-6, 7, 1, -2, true, 1, false, WorldDirection.West);
        assertThrows(ConstraintViolationException.class, () -> roomRepository.save(room1));
    }

    @Test
    void testOfGroundFloorAndNegativeFloorNumber () {
        Room room1 = new Room(-7, 7, 1, 1, true, 0, false, WorldDirection.West);
        assertDoesNotThrow(() -> roomRepository.save(room1));

        Room room2 = new Room(-8, 7, 1, 1, true, -3, false, WorldDirection.West);
        assertThrows(ConstraintViolationException.class, () -> roomRepository.save(room2));
    }

    @Test
    void testOfRemovingRoom () {
        Room room1 = new Room(-9, 7, 2, 3, true, 4, false, WorldDirection.South);
        roomRepository.save(room1);

        assertDoesNotThrow(() -> roomRepository.delete(room1));

        Example<Room> example = Example.of(room1);
        assertFalse(roomRepository.findOne(example).isPresent());
    }

    @Test
    void testOfEditingRoom () {
        Room room1 = new Room(-10, 7, 0, 2, true, 4, true, WorldDirection.North);
        roomRepository.save(room1);

        RoomDTO roomDTO1 = new RoomDTO(room1);
        roomDTO1.setTVPresent(false);
        assertDoesNotThrow(() -> roomRepository.save(roomDTO1.toRoom()));

        Optional<Room> room1_ = roomRepository.findById(room1.getId());
        assertFalse(room1_.isEmpty());
        assertFalse(room1_.get().isTVpresent());
    }

}
