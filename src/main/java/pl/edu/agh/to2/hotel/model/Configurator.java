package pl.edu.agh.to2.hotel.model;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.to2.hotel.model.enums.WorldDirection;
import pl.edu.agh.to2.hotel.model.reservation.Reservation;
import pl.edu.agh.to2.hotel.model.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.model.room.Room;
import pl.edu.agh.to2.hotel.model.room.RoomRepository;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
@Configuration
public class Configurator {
    @Bean
    public Validator validatorFactory() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    CommandLineRunner commandLineRunner(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        return args -> {
            if (reservationRepository.count() == 0 && roomRepository.count() == 0) {


                Room room1 = new Room(1, 57, 1, 1, true, 1, false, WorldDirection.East);
                roomRepository.save(room1);
                Reservation reservation1 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 11),
                        LocalDate.of(2023, Month.DECEMBER, 7),
                        room1,
                        "Jan",
                        "Kowalski",
                        "736482973"
                );
                reservationRepository.save(reservation1);


                Room room2 = new Room(2, 65, 2, 0, true, 2, true, WorldDirection.West);
                roomRepository.save(room2);
                Reservation reservation2 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 20),
                        LocalDate.of(2023, Month.DECEMBER, 15),
                        room2,
                        "Maria",
                        "Nowak",
                        "789123456"
                );
                reservationRepository.save(reservation2);


                Room room3 = new Room(3, 80, 1, 2, false, 3, false, WorldDirection.North);
                roomRepository.save(room3);
                Reservation reservation3 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 5),
                        LocalDate.of(2023, Month.DECEMBER, 3),
                        room3,
                        "Adam",
                        "Lis",
                        "654987321"
                );
                reservationRepository.save(reservation3);


                Room room4 = new Room(4, 90, 1, 1, true, 2, true, WorldDirection.South);
                roomRepository.save(room4);
                Reservation reservation4 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 15),
                        LocalDate.of(2023, Month.DECEMBER, 10),
                        room4,
                        "Ewa",
                        "Jabłońska",
                        "123456789"
                );
                reservationRepository.save(reservation4);


                Room room5 = new Room(5, 75, 2, 1, false, 1, true, WorldDirection.East);
                roomRepository.save(room5);
                Reservation reservation5 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 8),
                        LocalDate.of(2023, Month.DECEMBER, 5),
                        room5,
                        "Piotr",
                        "Szymański",
                        "987654321"
                );
                reservationRepository.save(reservation5);


                Room room6 = new Room(6, 100, 1, 2, true, 3, false, WorldDirection.West);
                roomRepository.save(room6);
                Reservation reservation6 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 25),
                        LocalDate.of(2023, Month.DECEMBER, 20),
                        room6,
                        "Monika",
                        "Wójcik",
                        "456123789"
                );
                reservationRepository.save(reservation6);


                Room room7 = new Room(7, 85, 2, 1, true, 2, true, WorldDirection.North);
                roomRepository.save(room7);
                Reservation reservation7 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 10),
                        LocalDate.of(2023, Month.DECEMBER, 6),
                        room7,
                        "Tomasz",
                        "Górecki",
                        "789654123"
                );
                reservationRepository.save(reservation7);


                Room room8 = new Room(8, 120, 1, 1, false, 4, true, WorldDirection.South);
                roomRepository.save(room8);
                Reservation reservation8 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 18),
                        LocalDate.of(2023, Month.DECEMBER, 15),
                        room8,
                        "Karolina",
                        "Dąbrowska",
                        "321456987"
                );
                reservationRepository.save(reservation8);


                Room room9 = new Room(9, 95, 2, 1, false, 3, false, WorldDirection.East);
                roomRepository.save(room9);
                Reservation reservation9 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 13),
                        LocalDate.of(2023, Month.DECEMBER, 9),
                        room9,
                        "Rafał",
                        "Michalski",
                        "654789321"
                );
                reservationRepository.save(reservation9);


                Room room10 = new Room(10, 110, 1, 2, true, 1, true, WorldDirection.West);
                roomRepository.save(room10);
                Reservation reservation10 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 22),
                        LocalDate.of(2023, Month.DECEMBER, 18),
                        room10,
                        "Magdalena",
                        "Piotrowska",
                        "987321654"
                );
                reservationRepository.save(reservation10);


                Room room11 = new Room(11, 80, 1, 2, true, 2, true, WorldDirection.South);
                roomRepository.save(room11);
                Reservation reservation11 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 14),
                        LocalDate.of(2023, Month.DECEMBER, 10),
                        room11,
                        "Marcin",
                        "Krawczyk",
                        "123987456"
                );
                reservationRepository.save(reservation11);


                Room room12 = new Room(12, 95, 2, 1, false, 3, false, WorldDirection.East);
                roomRepository.save(room12);
                Reservation reservation12 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 18),
                        LocalDate.of(2023, Month.DECEMBER, 15),
                        room12,
                        "Katarzyna",
                        "Zielińska",
                        "789456123"
                );
                reservationRepository.save(reservation12);


                Room room13 = new Room(13, 110, 1, 2, true, 1, true, WorldDirection.West);
                roomRepository.save(room13);
                Reservation reservation13 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 25),
                        LocalDate.of(2023, Month.DECEMBER, 22),
                        room13,
                        "Michał",
                        "Sawicki",
                        "654321987"
                );
                reservationRepository.save(reservation13);


                Room room14 = new Room(14, 75, 2, 1, true, 2, false, WorldDirection.North);
                roomRepository.save(room14);
                Reservation reservation14 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 8),
                        LocalDate.of(2023, Month.DECEMBER, 5),
                        room14,
                        "Dorota",
                        "Rutkowska",
                        "987654321"
                );
                reservationRepository.save(reservation14);


                Room room15 = new Room(15, 120, 1, 1, false, 4, true, WorldDirection.South);
                roomRepository.save(room15);
                Reservation reservation15 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 18),
                        LocalDate.of(2023, Month.DECEMBER, 15),
                        room15,
                        "Wojciech",
                        "Kaczmarek",
                        "321456987"
                );
                reservationRepository.save(reservation15);


                Room room16 = new Room(16, 90, 1, 1, false, 2, true, WorldDirection.East);
                roomRepository.save(room16);
                Reservation reservation16 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 15),
                        LocalDate.of(2023, Month.DECEMBER, 12),
                        room16,
                        "Aleksandra",
                        "Nowicka",
                        "456789123"
                );
                reservationRepository.save(reservation16);


                Room room17 = new Room(17, 65, 2, 0, true, 3, true, WorldDirection.West);
                roomRepository.save(room17);
                Reservation reservation17 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 20),
                        LocalDate.of(2023, Month.DECEMBER, 18),
                        room17,
                        "Łukasz",
                        "Górski",
                        "789123456"
                );
                reservationRepository.save(reservation17);


                Room room18 = new Room(18, 100, 1, 2, true, 1, false, WorldDirection.North);
                roomRepository.save(room18);
                Reservation reservation18 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 10),
                        LocalDate.of(2023, Month.DECEMBER, 7),
                        room18,
                        "Patrycja",
                        "Witkowska",
                        "123456789"
                );
                reservationRepository.save(reservation18);


                Room room19 = new Room(19, 57, 1, 1, true, 1, false, WorldDirection.East);
                roomRepository.save(room19);
                Reservation reservation19 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 5),
                        LocalDate.of(2023, Month.DECEMBER, 3),
                        room19,
                        "Arkadiusz",
                        "Czajkowski",
                        "987321654"
                );
                reservationRepository.save(reservation19);


                Room room20 = new Room(20, 85, 2, 1, true, 2, true, WorldDirection.South);
                roomRepository.save(room20);
                Reservation reservation20 = new Reservation(
                        LocalDate.of(2023, Month.NOVEMBER, 22),
                        LocalDate.of(2023, Month.DECEMBER, 20),
                        room20,
                        "Agnieszka",
                        "Lipińska",
                        "654987321"
                );
                reservationRepository.save(reservation20);

                Room room21 = new Room(21, 75, 2, 1, false, 1, true, WorldDirection.East);
                roomRepository.save(room21);

                Room room22 = new Room(22, 100, 1, 2, true, 3, false, WorldDirection.West);
                roomRepository.save(room22);

                Room room23 = new Room(23, 85, 2, 1, true, 2, true, WorldDirection.North);
                roomRepository.save(room23);

                Room room24 = new Room(24, 110, 1, 2, true, 1, true, WorldDirection.West);
                roomRepository.save(room24);

                Room room25 = new Room(25, 80, 1, 2, true, 2, true, WorldDirection.South);
                roomRepository.save(room25);

                Reservation reservation21 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 8),
                        LocalDate.of(2023, Month.DECEMBER, 15),
                        room21,
                        "Anna",
                        "Kowalczyk",
                        "654321123"
                );
                reservationRepository.save(reservation21);

                Reservation reservation22 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 5),
                        LocalDate.of(2023, Month.DECEMBER, 12),
                        room22,
                        "Bartosz",
                        "Lewandowski",
                        "789456456"
                );
                reservationRepository.save(reservation22);

                Reservation reservation23 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 10),
                        LocalDate.of(2023, Month.DECEMBER, 18),
                        room23,
                        "Celina",
                        "Sadowska",
                        "987654789"
                );
                reservationRepository.save(reservation23);

                Reservation reservation24 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 14),
                        LocalDate.of(2023, Month.DECEMBER, 22),
                        room24,
                        "Daniel",
                        "Malinowski",
                        "321987654"
                );
                reservationRepository.save(reservation24);

                Reservation reservation25 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 1),
                        LocalDate.of(2023, Month.DECEMBER, 8),
                        room25,
                        "Eliza",
                        "Pawlak",
                        "456123789"
                );
                reservationRepository.save(reservation25);

                Reservation reservation26 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 16),
                        LocalDate.of(2023, Month.DECEMBER, 24),
                        room1,
                        "Filip",
                        "Krajewski",
                        "789123987"
                );
                reservationRepository.save(reservation26);

                Reservation reservation27 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 5),
                        LocalDate.of(2023, Month.DECEMBER, 15),
                        room2,
                        "Gabriela",
                        "Nowacka",
                        "321456654"
                );
                reservationRepository.save(reservation27);

                Reservation reservation28 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 10),
                        LocalDate.of(2023, Month.DECEMBER, 20),
                        room3,
                        "Henryk",
                        "Klimek",
                        "987789123"
                );
                reservationRepository.save(reservation28);

                Reservation reservation29 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 15),
                        LocalDate.of(2023, Month.DECEMBER, 23),
                        room4,
                        "Izabela",
                        "Jastrzębska",
                        "123654987"
                );
                reservationRepository.save(reservation29);

                Reservation reservation30 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 8),
                        LocalDate.of(2023, Month.DECEMBER, 16),
                        room5,
                        "Janusz",
                        "Zalewski",
                        "654321789"
                );
                reservationRepository.save(reservation30);

                Reservation reservation31 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 20),
                        LocalDate.of(2023, Month.DECEMBER, 28),
                        room6,
                        "Karolina",
                        "Sikorska",
                        "789321654"
                );
                reservationRepository.save(reservation31);

                Reservation reservation32 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 12),
                        LocalDate.of(2023, Month.DECEMBER, 20),
                        room7,
                        "Łukasz",
                        "Zawadzki",
                        "456987321"
                );
                reservationRepository.save(reservation32);

                Reservation reservation33 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 18),
                        LocalDate.of(2023, Month.DECEMBER, 26),
                        room8,
                        "Monika",
                        "Kowal",
                        "123789456"
                );
                reservationRepository.save(reservation33);

                Reservation reservation34 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 24),
                        LocalDate.of(2023, Month.DECEMBER, 31),
                        room9,
                        "Norbert",
                        "Duda",
                        "987321456"
                );
                reservationRepository.save(reservation34);

                Reservation reservation35 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 16),
                        LocalDate.of(2023, Month.DECEMBER, 24),
                        room10,
                        "Oliwia",
                        "Wrona",
                        "654123789"
                );
                reservationRepository.save(reservation35);

                Reservation reservation36 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 20),
                        LocalDate.of(2023, Month.DECEMBER, 28),
                        room11,
                        "Paweł",
                        "Kaczor",
                        "321987456"
                );
                reservationRepository.save(reservation36);

                Reservation reservation37 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 26),
                        LocalDate.of(2024, Month.JANUARY, 2),
                        room12,
                        "Qwerty",
                        "Uiop",
                        "987456321"
                );
                reservationRepository.save(reservation37);

                Reservation reservation38 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 30),
                        LocalDate.of(2024, Month.JANUARY, 7),
                        room13,
                        "Radosław",
                        "Kielbasa",
                        "123654789"
                );
                reservationRepository.save(reservation38);

                Reservation reservation39 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 23),
                        LocalDate.of(2023, Month.DECEMBER, 31),
                        room14,
                        "Sylwia",
                        "Zielińska",
                        "789321456"
                );
                reservationRepository.save(reservation39);

                Reservation reservation40 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 28),
                        LocalDate.of(2024, Month.JANUARY, 5),
                        room15,
                        "Tomasz",
                        "Lis",
                        "456987123"
                );
                reservationRepository.save(reservation40);

                Reservation reservation41 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 22),
                        LocalDate.of(2023, Month.DECEMBER, 30),
                        room16,
                        "Urszula",
                        "Pawlak",
                        "123456789"
                );
                reservationRepository.save(reservation41);

                Reservation reservation42 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 26),
                        LocalDate.of(2024, Month.JANUARY, 3),
                        room17,
                        "Waldemar",
                        "Dudek",
                        "789123456"
                );
                reservationRepository.save(reservation42);

                Reservation reservation43 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 30),
                        LocalDate.of(2024, Month.JANUARY, 7),
                        room18,
                        "Xawery",
                        "Nowakowski",
                        "321654987"
                );
                reservationRepository.save(reservation43);

                Reservation reservation44 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 24),
                        LocalDate.of(2023, Month.DECEMBER, 31),
                        room19,
                        "Yvonne",
                        "Jaworska",
                        "654987321"
                );
                reservationRepository.save(reservation44);

                Reservation reservation45 = new Reservation(
                        LocalDate.of(2023, Month.DECEMBER, 28),
                        LocalDate.of(2024, Month.JANUARY, 5),
                        room20,
                        "Zbigniew",
                        "Szczepański",
                        "321654987"
                );
                reservationRepository.save(reservation45);

            }
        };
    }
}
