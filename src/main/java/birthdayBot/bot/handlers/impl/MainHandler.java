package birthdayBot.bot.handlers.impl;

import birthdayBot.bot.State;
import birthdayBot.bot.handlers.interfaces.Handler;
import birthdayBot.entities.User;
import birthdayBot.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static birthdayBot.bot.State.USE;
import static birthdayBot.utils.Const.CallBackQueries.*;
import static birthdayBot.utils.TelegramUtil.createMessageTemplate;

@Service
public class MainHandler implements Handler {

    private final UserService userService;

    @Autowired
    public MainHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if (message.equalsIgnoreCase(ADD_BDAY)){
            return moveToAddBdayHandler(user);
        }else if (message.equalsIgnoreCase(DELETE_BDAY)){
            return null;
        } else if (message.equalsIgnoreCase(SCHEDULE_EDIT)){
            return null;
        } else return null;

    }

    private List<PartialBotApiMethod<? extends Serializable>> moveToAddBdayHandler(User user){
        user.setState(State.ADD_BDAY);
        userService.save(user);
        SendMessage sm = createMessageTemplate(user);
        sm.setText("Для добавления нового именинника введите дату его рождения и имя в формате:\n"
        + "дд.мм.гггг Имя Фамилия");
        return List.of(sm);
    }

    @Override
    public State operatedBotState() {
        return USE;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(ADD_BDAY, DELETE_BDAY, SCHEDULE_EDIT);
    }
}
