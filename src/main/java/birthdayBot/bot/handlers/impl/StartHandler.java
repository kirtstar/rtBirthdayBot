package birthdayBot.bot.handlers.impl;

import birthdayBot.bot.State;
import birthdayBot.bot.handlers.interfaces.Handler;
import birthdayBot.entities.User;
import birthdayBot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static birthdayBot.utils.Const.Common.START_MESSAGE;
import static birthdayBot.utils.TelegramUtil.createMessageTemplate;

@Service
public class StartHandler implements Handler {

    @Value("${bot.name}")
    private String botUserName;

    private UserRepository userRepository;

    @Autowired
    public StartHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        user.setState(State.REGISTRATION);
        userRepository.save(user);

        SendMessage startMessage = createMessageTemplate(user);
        startMessage.setText(START_MESSAGE);

        return List.of(startMessage);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
