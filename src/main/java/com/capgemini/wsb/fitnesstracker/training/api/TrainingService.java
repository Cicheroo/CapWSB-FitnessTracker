package com.capgemini.wsb.fitnesstracker.training.api;

/**
 * Interface (API) for modifying operations on {@link Training} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface TrainingService {

    /**
     * Creates training from given request {@link TrainingDto}.
     * Implementing classes should handle cases when training already exists.
     *
     * @param training The training to be created.
     * @return created training {@link Training}
     */
    Training createTraining(TrainingDto training);

    /**
     * Updates training {@link Training} specified by id with given {@link TrainingDto} request.
     * Implementing classes should handle cases when training does not exist.
     *
     * @param trainingId The id of training to update
     * @param trainingDto The body of training to be updated
     * @return updated training {@link Training}
     */
    Training updateTraining(TrainingDto trainingDto, long trainingId);
}
