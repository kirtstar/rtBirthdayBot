package birthdayBot.services.impl;

import birthdayBot.entities.Birthday;
import birthdayBot.entities.User;
import birthdayBot.repositories.BirthdayRepository;
import birthdayBot.services.interfaces.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BirthdayServiceImpl implements BirthdayService {
    private final BirthdayRepository birthdayRepository;

    @Autowired
    public BirthdayServiceImpl(BirthdayRepository birthdayRepository) {
        this.birthdayRepository = birthdayRepository;
    }

    @Override
    public void save(Birthday birthday) {
        birthdayRepository.save(birthday);
    }

    @Override
    public boolean save(User user, String message) {
        String dateString = message.substring(0, 10);
        String name = message.substring(11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            Birthday newBirthday = new Birthday();
            newBirthday.setBirthdayDate(date);
            newBirthday.setUser(user);
            newBirthday.setName(name);
            this.save(newBirthday);
            return true;
        } catch (Exception E) {
            return false;
        }
    }

    @Override
    public List<Birthday> getNearestBirthdaysByUser(User user) {
        return birthdayRepository.findAllByUser(user)
                .stream()
                .peek(birthday -> {
                    birthday.setBirthdayDate(birthday.getBirthdayDate().withYear(LocalDate.now().getYear()));
                })
                .filter(birthday -> ChronoUnit.DAYS.between(LocalDate.now(), birthday.getBirthdayDate()) < 30L
                && ChronoUnit.DAYS.between(LocalDate.now(), birthday.getBirthdayDate()) > 0L)
                .collect(Collectors.toList());
    }
}
