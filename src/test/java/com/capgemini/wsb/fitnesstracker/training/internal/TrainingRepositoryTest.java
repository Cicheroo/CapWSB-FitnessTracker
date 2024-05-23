package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        trainingRepository.save(new Training(new User("first", "last", LocalDate.now(), "email@email.pl"),
                sdf.parse("2024-01-19 08:00:00"), sdf.parse("2024-01-19 09:30:00"), ActivityType.CYCLING, 12.45, 512.2));

        trainingRepository.save(new Training(new User("first2", "last2", LocalDate.now(), "email1@email.pl"),
                sdf.parse("2025-01-19 08:00:00"),  sdf.parse("2025-01-19 08:00:00"), ActivityType.RUNNING, 12.455, 5125.2));
    }

    @AfterEach
    public void tearDown() {
        trainingRepository.deleteAll();
    }

    @Test
    void shouldFindAllTrainingsByUserId_whenFindingAllTrainingsByUserId() {
        Long userId = trainingRepository.findAll().get(0).getUser().getId();

        List<Training> trainings = trainingRepository.findAllByUserId(userId);

        assertThat(trainings).isNotEmpty();
        assertThat(trainings).hasSize(1);
        assertThat(trainings).allMatch(training -> training.getUser().getId().equals(userId));
    }

    @Test
    void shouldFindAllFinishedTrainings_whenFindingAllFinishedTrainings() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date threshold = sdf.parse("2024-05-18");
        List<Training> trainings = trainingRepository.findAllFinishedTrainings(threshold);

        assertThat(trainings).isNotEmpty();
        assertThat(trainings).hasSize(1);
        assertThat(trainings).allMatch(training -> training.getEndTime().compareTo(threshold) > 0);
    }

    @Test
    void shouldFindAllTrainingsByActivityType_whenFindingAllTrainingsByActivityType() {
        List<Training> trainings = trainingRepository.findAllByActivityType(ActivityType.CYCLING);

        assertThat(trainings).isNotEmpty();
        assertThat(trainings).hasSize(1);
        assertThat(trainings).allMatch(training -> training.getActivityType() == ActivityType.CYCLING);
    }
}