package com.pmajay.scheduler;

import com.pmajay.model.Agency;
import com.pmajay.model.Project;
import com.pmajay.model.User;
import com.pmajay.repository.AgencyRepository;
import com.pmajay.repository.ProjectRepository;
import com.pmajay.repository.UserRepository;
import com.pmajay.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DeadlineScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DeadlineScheduler.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Runs daily at midnight to check for projects past their deadline.
     * Flags them as DELAYED and creates notifications for relevant users.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkDelayedProjects() {
        logger.info("Running deadline check scheduler...");

        List<Project> pastDeadline = projectRepository.findProjectsPastDeadline(LocalDate.now());

        for (Project project : pastDeadline) {
            // Mark project as DELAYED
            project.setStatus(Project.Status.DELAYED);
            projectRepository.save(project);

            logger.warn("Project '{}' (ID: {}) has been marked as DELAYED", project.getTitle(), project.getId());

            // Create notification for the agency's nodal officer
            if (project.getAgencyId() != null) {
                Agency agency = agencyRepository.findById(project.getAgencyId()).orElse(null);
                if (agency != null) {
                    String message = String.format(
                        "⚠️ Project '%s' has exceeded its deadline (%s). Immediate attention required.",
                        project.getTitle(),
                        project.getDeadline()
                    );

                    // Notify all users linked to this state
                    List<User> stateUsers = userRepository.findAll().stream()
                            .filter(u -> project.getStateId() != null && project.getStateId().equals(u.getStateId()))
                            .toList();

                    for (User user : stateUsers) {
                        notificationService.createNotification(user.getId(), message);
                    }

                    // Also notify CENTRE users
                    List<User> centreUsers = userRepository.findAll().stream()
                            .filter(u -> u.getRole() == User.Role.CENTRE)
                            .toList();

                    for (User user : centreUsers) {
                        notificationService.createNotification(user.getId(), message);
                    }
                }
            }
        }

        logger.info("Deadline check completed. {} projects marked as delayed.", pastDeadline.size());
    }
}
