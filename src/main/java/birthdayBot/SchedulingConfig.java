package birthdayBot;

import birthdayBot.scheduler.ScheduledTaskManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    private final ScheduledTaskManager scheduledTaskManager;

    public SchedulingConfig(ScheduledTaskManager scheduledTaskManager) {
        this.scheduledTaskManager = scheduledTaskManager;
    }

    @Scheduled(cron = "0 45 19 * * *")
    public void reportAboutBirthdays(){
        scheduledTaskManager.reportAboutBirthdays();
    }
}
