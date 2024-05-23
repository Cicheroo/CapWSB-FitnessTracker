package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for querying {@link Training} entities through the API.
 */
public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

    /**
     * Retrieves all trainings.
     *
     * @return A {@link List} containing all trainings
     */
    List<Training> findAllTrainings();

    /**
     * Retrieves all trainings belonging to specified user.
     *
     * @return A {@link List} containing all trainings belonging to specified user
     */
    List<Training> findAllTrainingsByUserId(long userId);

    /**
     * Retrieves all finished trainings after time.
     *
     * @return A {@link List} containing all finished trainings
     */
    List<Training> findAllFinishedTrainings(Date afterTime);


    /**
     * Retrieves all trainings matching given activity type.
     *
     * @return A {@link List} containing all trainings with given activity type
     */
    List<Training> findAllByActivityType(ActivityType activityType);
}
