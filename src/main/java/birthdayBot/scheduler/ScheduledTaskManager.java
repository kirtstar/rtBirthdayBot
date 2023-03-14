package birthdayBot.scheduler;

import birthdayBot.Bot;
import birthdayBot.entities.Birthday;
import birthdayBot.entities.User;
import birthdayBot.services.interfaces.BirthdayService;
import birthdayBot.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static birthdayBot.utils.TelegramUtil.createMessageTemplate;

@Service
public class ScheduledTaskManager {

    private final Bot bot;
    private final UserService userService;
    private final BirthdayService birthdayService;

    @Autowired
    public ScheduledTaskManager(Bot bot, UserService userService, BirthdayService birthdayService) {
        this.bot = bot;
        this.userService = userService;
        this.birthdayService = birthdayService;
    }

    public void reportAboutBirthdays(){
        User user = userService.findByIdOrException(8l);
        SendMessage sm = createMessageTemplate(user);
        List<Birthday> nearestBirthdays = birthdayService.getNearestBirthdaysByUser(user);
        if (nearestBirthdays.isEmpty()) return;
        sm.setText(nearestBirthdays.stream()
                        .sorted(Comparator.comparingLong(b -> ChronoUnit.DAYS.between(LocalDate.now(), b.getBirthdayDate())))
                        .map(birthday -> String.format("%s %s до дня рождения %d",
                                birthday.getName(), birthday.getBirthdayDate().toString(),
                                ChronoUnit.DAYS.between(LocalDate.now(), birthday.getBirthdayDate())))
                                .collect(Collectors.joining("\n")));
        bot.executeWithExceptionCheck(sm);
    }
}
