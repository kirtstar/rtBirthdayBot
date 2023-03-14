package birthdayBot.services.interfaces;

import birthdayBot.entities.Birthday;
import birthdayBot.entities.User;

import java.util.List;

public interface BirthdayService {
    void save(Birthday birthday);

    boolean save(User user, String message);
    List<Birthday> getNearestBirthdaysByUser(User user);

}
