package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Training repository which extends JpaRepository. This repository is managed by Spring Framework and is used for operations
 * on {@link Training} training entity.
 */
interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Query searching trainings by user id.
     *
     * @param userId id of user to search
     * @return {@link List} containing found users or empty {@link List} if user has no trainings.
     */
    default List<Training> findAllByUserId(long userId) {
        return findAll().stream()
                .filter(training -> training.getUser().getId() == userId)
                .toList();
    }

    /**
     * Query searching trainings after given time.
     *
     * @param afterTime time after trainings should be found
     * @return {@link List} containing found users or empty {@link List} if no trainings satisfy given criteria.
     */
    default List<Training> findAllFinishedTrainings(Date afterTime) {
        return findAll().stream()
                .filter(training -> training.getEndTime().compareTo(afterTime) > 0)
                .toList();
    }
    /**
     * Query searching trainings after given time.
     *
     * @param activityType type of training to search
     * @return {@link List} containing found users or empty {@link List} if no trainings satisfy given criteria.
     */
    default List<Training> findAllByActivityType(ActivityType activityType) {
        return findAll().stream()
                .filter(training -> training.getActivityType() == activityType)
                .toList();
    }
}
