package birthdayBot.bot.handlers.impl;

import birthdayBot.bot.State;
import birthdayBot.bot.handlers.interfaces.Handler;
import birthdayBot.entities.User;
import birthdayBot.services.interfaces.BirthdayService;
import birthdayBot.services.interfaces.ButtonService;
import birthdayBot.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static birthdayBot.utils.Const.CallBackQueries.ADD_BDAY_ACCEPT;
import static birthdayBot.utils.Const.CallBackQueries.BACK_TO_MENU;
import static birthdayBot.utils.TelegramUtil.createMessageTemplate;

@Service
public class AddBdayHandler implements Handler {

    private final BirthdayService birthdayService;
    private final UserService userService;
    private final ButtonService buttonService;

    @Autowired
    public AddBdayHandler(BirthdayService birthdayService, UserService userService, ButtonService buttonService) {
        this.birthdayService = birthdayService;
        this.userService = userService;
        this.buttonService = buttonService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if (message.equalsIgnoreCase(ADD_BDAY_ACCEPT)) {
            //acceptBday(user);
        } else if (message.equalsIgnoreCase(BACK_TO_MENU)){
            return backToMenu(user);
        }
        return checkBday(user, message);
    }

    private List<PartialBotApiMethod<? extends Serializable>> checkBday(User user, String message){
        SendMessage sm = createMessageTemplate(user);
        if (birthdayService.save(user, message)){
            sm.setText("Именинник успешно добавлен!");
            user.setState(State.USE);
            userService.save(user);
            sm.setReplyMarkup(buttonService.getMainButtons());
            return List.of(sm);
        }
        sm.setText("Некорректный формат!\nВы можете вернуться назад или данные согласно формату ниже: \n"
            + "дд.мм.гггг Имя Фамилия");
        sm.setReplyMarkup(buttonService.getBackToMainMenuButton());
        return List.of(sm);
    }

    private List<PartialBotApiMethod<? extends Serializable>> backToMenu(User user){
        user.setState(State.USE);
        userService.save(user);
        SendMessage sm = createMessageTemplate(user);
        sm.setText("Для продолжения выберите нужное действие:");
        sm.setReplyMarkup(buttonService.getMainButtons());
        return List.of(sm);
    }

    @Override
    public State operatedBotState() {
        return State.ADD_BDAY;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(ADD_BDAY_ACCEPT, BACK_TO_MENU);
    }
}
