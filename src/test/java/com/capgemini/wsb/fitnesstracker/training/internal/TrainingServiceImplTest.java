package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingMapper trainingMapper;
    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private TrainingServiceImpl trainingService;


    @Test
    void shouldGetTraining_whenGettingTraining() throws ParseException {
        //given
        Training expected = buildTraining();
        given(trainingRepository.findById(anyLong())).willReturn(Optional.of(expected));

        //when
        Optional<Training> actual = trainingService.getTraining(1L);

        //then
        then(trainingRepository).should().findById(anyLong());
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    void shouldFindAllTrainings_whenFindingAllTrainings() throws ParseException {
        //given
        Training expected = buildTraining();
        given(trainingRepository.findAll()).willReturn(List.of(expected));

        //when
        List<Training> actual = trainingService.findAllTrainings();

        //then
        then(trainingRepository).should().findAll();
        assertThat(actual).hasSize(1);
        assertThat(actual).contains(expected);
    }

    @Test
    void shouldFindAllTrainingsByUserId_whenFindingAllTrainingsByUserId() throws ParseException {
        //given
        Training expected = buildTraining();
        given(trainingRepository.findAllByUserId(anyLong())).willReturn(List.of(expected));

        //when
        List<Training> actual = trainingService.findAllTrainingsByUserId(1L);

        //then
        then(trainingRepository).should().findAllByUserId(anyLong());
        assertThat(actual).hasSize(1);
        assertThat(actual).contains(expected);
    }

    @Test
    void shouldFindAllFinishedTrainings_whenFindingAllFinishedTrainings() throws ParseException {
        //given
        Training expected = buildTraining();
        given(trainingRepository.findAllFinishedTrainings(any())).willReturn(List.of(expected));

        //when
        List<Training> actual = trainingService.findAllFinishedTrainings(new Date());

        //then
        then(trainingRepository).should().findAllFinishedTrainings(any());
        assertThat(actual).hasSize(1);
        assertThat(actual).contains(expected);
    }

    @Test
    void shouldFindAllTrainingsByActivityType_whenFindingAllTrainingsByActivityType() throws ParseException {
        //given
        Training expected = buildTraining();
        given(trainingRepository.findAllByActivityType(any())).willReturn(List.of(expected));

        //when
        List<Training> actual = trainingService.findAllByActivityType(ActivityType.CYCLING);

        //then
        then(trainingRepository).should().findAllByActivityType(any());
        assertThat(actual).hasSize(1);
        assertThat(actual).contains(expected);
    }

    @Test
    void shouldCreateTraining_whenCreatingTraining() throws ParseException {
        //given
        TrainingDto trainingDto = buildTrainingDto(null);
        given(userProvider.getUser(anyLong())).willReturn(Optional.of(buildUser()));
        given(trainingRepository.save(any())).willReturn(buildTraining());
        given(trainingMapper.toEntity(any(), any())).willReturn(buildTraining());

        //when
        trainingService.createTraining(trainingDto);

        //when
        then(userProvider).should().getUser(anyLong());
        then(trainingRepository).should().save(any());
        then(trainingMapper).should().toEntity(any(), any());
    }

    @Test
    void shouldThrowException_whenCreatingTrainingIfTrainingDtoHasId() throws ParseException {
        //given
        TrainingDto trainingDto = buildTrainingDto(1L);

        //when
        //then
        assertThatThrownBy(() -> trainingService.createTraining(trainingDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Training has already DB ID, update is not permitted!");
    }

    @Test
    void shouldThrowException_whenCreatingTrainingIfUserIsNotFound() throws ParseException {
        //given
        TrainingDto trainingDto = buildTrainingDto(null);
        given(userProvider.getUser(anyLong())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> trainingService.createTraining(trainingDto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with ID=1 was not found");
        then(userProvider).should().getUser(anyLong());
    }

    @Test
    void shouldUpdateTraining_whenUpdatingTraining() throws ParseException {
        //given
        TrainingDto trainingDto = buildTrainingDto(1L);
        given(trainingRepository.findById(1L)).willReturn(Optional.of(buildTraining()));
        given(trainingRepository.save(any())).willReturn(buildTraining());
        given(trainingMapper.updateEntity(any(), any())).willReturn(buildTraining());

        //when
        trainingService.updateTraining(trainingDto, 1L);

        //when
        then(trainingRepository).should().findById(anyLong());
        then(trainingRepository).should().save(any());
        then(trainingMapper).should().updateEntity(any(), any());
    }

    @Test
    void shouldThrowException_whenUpdatingTrainingIfTrainingIsNotFound() throws ParseException {
        //given
        TrainingDto trainingDto = buildTrainingDto(null);
        given(trainingRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> trainingService.updateTraining(trainingDto, 1L))
                .isInstanceOf(TrainingNotFoundException.class)
                .hasMessage("Training with ID=1 was not found");
        then(trainingRepository).should().findById(anyLong());
    }

    private TrainingDto buildTrainingDto(Long id) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new TrainingDto(id,
                1L,
                sdf.parse("2024-01-19 08:00:00"),
                sdf.parse("2024-01-19 09:30:00"),
                ActivityType.CYCLING,
                15.02,
                12.4);
    }

    private Training buildTraining() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Training(buildUser(),
                sdf.parse("2024-01-19 08:00:00"),
                sdf.parse("2024-01-19 09:30:00"),
                ActivityType.CYCLING,
                15.02,
                12.4);
    }

    private User buildUser() {
        return new User("firstName", "lastName", LocalDate.of(2000, 10, 25), "email@test.pl");
    }
}