package birthdayBot.services.impl;

import birthdayBot.entities.User;
import birthdayBot.services.interfaces.ButtonService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static birthdayBot.utils.Const.CallBackQueries.*;
import static birthdayBot.utils.Const.Common.*;
import static birthdayBot.utils.TelegramUtil.createInlineKeyboardButton;

@Service
public class ButtonServiceImpl implements ButtonService {
    @Override
    public InlineKeyboardMarkup getMainButtons() {
        InlineKeyboardMarkup ikb = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = List.of(
                createInlineKeyboardButton(NEW_BDAY, ADD_BDAY),
                createInlineKeyboardButton(REMOVE_BDAY, DELETE_BDAY)
        );
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = List.of(
                createInlineKeyboardButton(SCHEDULE, SCHEDULE_EDIT));
        ikb.setKeyboard(List.of(inlineKeyboardButtonsFirstRow,
                inlineKeyboardButtonsSecondRow));
        return ikb;
    }
    @Override
    public InlineKeyboardMarkup getNameAcceptButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = List.of(
                createInlineKeyboardButton(AGREE, NAME_ACCEPT));
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsFirstRow));
        return inlineKeyboardMarkup;
    }
    @Override
    public InlineKeyboardMarkup getBackToMainMenuButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = List.of(
                createInlineKeyboardButton(BACK_TO_MAIN_MENU, BACK_TO_MENU));
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsFirstRow));
        return inlineKeyboardMarkup;
    }
}
