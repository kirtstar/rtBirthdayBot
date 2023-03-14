package birthdayBot.bot.handlers.impl;

import birthdayBot.bot.State;
import birthdayBot.bot.handlers.interfaces.Handler;
import birthdayBot.entities.User;
import birthdayBot.services.interfaces.ButtonService;
import birthdayBot.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


import java.io.Serializable;
import java.util.List;

import static birthdayBot.utils.Const.CallBackQueries.*;
import static birthdayBot.utils.Const.Common.*;
import static birthdayBot.utils.TelegramUtil.createMessageTemplate;

@Service
public class RegistrationHandler implements Handler {

    private final UserService userService;
    private final ButtonService buttonService;

    @Autowired
    public RegistrationHandler(UserService userService, ButtonService buttonService) {
        this.userService = userService;
        this.buttonService = buttonService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if (message.equalsIgnoreCase(NAME_ACCEPT)){
            return accept(user);
        }
        return checkName(user, message);
    }



    private List<PartialBotApiMethod<? extends Serializable>> checkName(User user, String message){
        if (message.startsWith("/")){
            SendMessage sm = createMessageTemplate(user);
            sm.setText(ACTION_NOT_AVAILABLE_NOW);
            return List.of(sm);
        }
        user.setTempName(message);
        userService.save(user);

        //Button to save changes
        SendMessage sm = createMessageTemplate(user);
        sm.setText(String.format(CHECK_NAME, message));
        sm.setReplyMarkup(buttonService.getNameAcceptButtons());
        return List.of(sm);
    }

    private List<PartialBotApiMethod<? extends Serializable>> accept(User user){
        user.setName(user.getTempName());
        user.setState(State.USE);
        userService.save(user);
        return getBirthdayInstruction(user);
    }

    private List<PartialBotApiMethod<? extends Serializable>> getBirthdayInstruction(User user){
        SendMessage sm = createMessageTemplate(user);
        sm.setReplyMarkup(buttonService.getMainButtons());
        sm.setText("Отлично! Для работы выберите необходимую функцию");
        return List.of(sm);
    }

    @Override
    public State operatedBotState() {
        return State.REGISTRATION;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(NAME_ACCEPT);
    }
}
