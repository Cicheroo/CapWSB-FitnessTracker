package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * Training dto which is used to do read operations on {@link Training}.
 */
@Getter
@RequiredArgsConstructor
class TrainingDto {
    @Nullable
    private final Long id;
    private final UserDto user;
    private final Date startTime;
    private final Date endTime;
    private final ActivityType activityType;
    private final double distance;
    private final double averageSpeed;
}
