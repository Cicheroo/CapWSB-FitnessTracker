package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Training rest controller used to communicate via HTTP to read/write on training. It produces/consumes in JSON format as default.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    /**
     * Finds all trainings.
     *
     * @return list of trainings
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Finds all trainings belonging to specified user.
     *
     * @param userId The owner of trainings
     * @return A {@link List} with {@link TrainingDto} belonging to {@link User}
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getAllTrainingsForUserById(@PathVariable long userId) {
        return trainingService.findAllTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Finds all finished trainings.
     *
     * @param afterTime The threshold time used to filter trainings
     * @return A {@link List} with {@link TrainingDto}
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainings(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        return trainingService.findAllFinishedTrainings(afterTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Finds all training matching given activity type.
     *
     * @param activityType The activity type trainings should match
     * @return A {@link List} with {@link TrainingDto}
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.findAllByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Creates new training. If successful it returns created user with 201 status code.
     *
     * @param trainingDto The body {@link com.capgemini.wsb.fitnesstracker.training.api.TrainingDto} used to create new {@link Training}
     * @return newly created training
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training createTraining(@RequestBody com.capgemini.wsb.fitnesstracker.training.api.TrainingDto trainingDto) {
        return trainingService.createTraining(trainingDto);
    }

    /**
     * Updates already existing training.
     *
     * @param trainingDto The body {@link com.capgemini.wsb.fitnesstracker.training.api.TrainingDto} used to update existing training
     * @param trainingId The od if training to update
     * @return updated training {@link Training}
     */
    @PutMapping("/{trainingId}")
    public Training updateTraining(@RequestBody com.capgemini.wsb.fitnesstracker.training.api.TrainingDto trainingDto, @PathVariable long trainingId) {
        return trainingService.updateTraining(trainingDto, trainingId);
    }

}
