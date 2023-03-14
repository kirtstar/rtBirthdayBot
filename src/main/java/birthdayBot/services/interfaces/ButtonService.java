package birthdayBot.services.interfaces;

import birthdayBot.entities.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.Serializable;
import java.util.List;

public interface ButtonService {
    InlineKeyboardMarkup getMainButtons();
    InlineKeyboardMarkup getNameAcceptButtons();
    InlineKeyboardMarkup getBackToMainMenuButton();
}
