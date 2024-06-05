package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportGenerator {

    private final EmailSender mailSender;
    private final TrainingProvider trainingProvider;

    @Scheduled(cron = "0 0 0 * * SAT")
    public void generateReport() {
        log.info("Generating training report for users...");
        List<Training> allTrainings = trainingProvider.findAllTrainings();

        Instant weekAgo = new Date().toInstant().minus(7, ChronoUnit.DAYS);
        Map<User, List<Training>> userWithTrainingsMap = allTrainings.stream()
                .filter(training -> training.getEndTime().toInstant().isAfter(weekAgo))
                .collect(Collectors.groupingBy(Training::getUser));

        userWithTrainingsMap.keySet()
                .forEach(user -> {
                    List<Training> userTrainings = userWithTrainingsMap.get(user);
                    mailSender.send(new EmailDto(
                            user.getEmail(),
                            "Raport z ostatniego tygodnia",
                            "Ukonczone treningi: " + userTrainings.size()
                    ));
                });
    }
}
