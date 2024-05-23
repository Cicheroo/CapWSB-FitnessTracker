package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * This service is main communication point between API and internal functionality. It performs operations on training.
 * This service is managed by Spring Framework therefore it's life cycle is managed by the framework.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

    /**
     * Retrieves a training based on its ID.
     * If the training with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located training, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    /**
     * Retrieves all trainings.
     *
     * @return An {@link List} containing the all trainings
     */
    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Retrieves all trainings belonging to specified user by his id.
     *
     * @return An {@link List} containing the all trainings belonging to user
     */
    @Override
    public List<Training> findAllTrainingsByUserId(long userId) {
        return trainingRepository.findAllByUserId(userId);
    }

    /**
     * Retrieves all finished trainings after time.
     *
     * @return A {@link List} containing all finished trainings
     */
    @Override
    public List<Training> findAllFinishedTrainings(Date afterTime) {
        return trainingRepository.findAllFinishedTrainings(afterTime);
    }

    /**
     * Retrieves all trainings matching given activity type.
     *
     * @return A {@link List} containing all trainings with given activity type
     */
    @Override
    public List<Training> findAllByActivityType(ActivityType activityType) {
        return trainingRepository.findAllByActivityType(activityType);
    }

    /**
     * Creates training from given request {@link Training}.
     * Additionally, checks if training already has id. If it does, an {@link IllegalArgumentException} exception is thrown.
     * If user was not found it throws an {@link UserNotFoundException}.
     *
     * @param trainingDto The training to be created.
     * @return created training
     */
    @Override
    public Training createTraining(TrainingDto trainingDto) {
        log.info("Creating Training {}", trainingDto);
        if (trainingDto.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, update is not permitted!");
        }
        User user = userProvider.getUser(trainingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(trainingDto.getUserId()));
        return trainingRepository.save(trainingMapper.toEntity(trainingDto, user));
    }

    /**
     * Updates training {@link Training} specified by id with given {@link TrainingDto} request.
     * Additionally, training if training with given id exists, if not an {@link TrainingNotFoundException} exception is thrown.
     *
     * @param trainingDto The body of training to be updated
     * @param trainingId  The id of training to update
     * @return updated training
     */
    @Override
    public Training updateTraining(TrainingDto trainingDto, long trainingId) {
        log.info("Updating Training {}", trainingId);
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        Training updatedUser = trainingMapper.updateEntity(training, trainingDto);

        return trainingRepository.save(updatedUser);
    }
}
