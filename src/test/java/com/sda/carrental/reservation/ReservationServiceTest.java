package com.sda.carrental.reservation;

import com.sda.carrental.car_rental_facility.BranchesRepository;
import com.sda.carrental.car_rental_facility.BranchesModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationServiceTest {

    @Mock
    private CarRepository carRepositoryMock;

    @Mock
    private BranchesRepository branchesRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldSaveReservation() {

        //given
        ReservationDTO reservationDTO = new ReservationDTO(
                "Ted",
                1L,
                LocalDate.of(2023, 11, 20),
                LocalDate.of(2023, 11, 22),
                1L,
                2L
        );

        BranchesModel startBranch = new BranchesModel(1L, "Warszawa");
        Mockito.when(branchesRepositoryMock.findById(1L)).thenReturn(Optional.of(startBranch));

        BranchesModel endBranch = new BranchesModel(2L, "Gdynia");
        Mockito.when(branchesRepositoryMock.findById(2L)).thenReturn(Optional.of(endBranch));

        CarModel car = new CarModel(
                1L,
                "KIA",
                "Ceed",
                "sedan",
                CarStatus.AVAILABLE,
                BigDecimal.valueOf(100)
        );

        Mockito.when(carRepositoryMock.findById(1L)).thenReturn(Optional.of(car));

        //when
        reservationService.saveReservation(reservationDTO);

        //then
        Mockito.verify(carRepositoryMock).findById(1L);

        ArgumentCaptor<ReservationModel> captor = ArgumentCaptor.forClass(ReservationModel.class);
        Mockito.verify(reservationRepositoryMock).save(captor.capture());
        ReservationModel result = captor.getValue();

        assertThat(result.getEndDate()).isEqualTo("2023-11-22");
        assertThat(result.getStartDate()).isEqualTo("2023-11-20");
        assertThat(result.getCar()).isEqualTo(car);
        assertThat(result.getPrice().intValue()).isEqualTo(200);
    }
}