package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * Training dto which is used to do write operations on {@link Training}.
 */
@Getter
@RequiredArgsConstructor
public class TrainingDto {
    @Nullable
    private final Long id;
    private final Long userId;
    private final Date startTime;
    private final Date endTime;
    private final ActivityType activityType;
    private final double distance;
    private final double averageSpeed;
}
