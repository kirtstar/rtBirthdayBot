package birthdayBot.services.impl;

import birthdayBot.entities.User;
import birthdayBot.repositories.UserRepository;
import birthdayBot.services.interfaces.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByIdOrException(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
