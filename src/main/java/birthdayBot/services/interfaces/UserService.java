package birthdayBot.services.interfaces;

import birthdayBot.entities.User;

public interface UserService {
    void save(User user);
    User findByIdOrException(Long id);
}
