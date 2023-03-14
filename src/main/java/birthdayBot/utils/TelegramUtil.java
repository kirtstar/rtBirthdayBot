package birthdayBot.utils;

import birthdayBot.entities.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public class TelegramUtil {

    public static SendMessage createMessageTemplate(String chatId) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        return message;
    }
    public static SendMessage createMessageTemplate(User user){
        return createMessageTemplate(user.getChatId().toString());
    }

    public static SendMessage createMessageTemplateWithText(User user, String text){
        SendMessage sm = createMessageTemplate(user);
        sm.setText(text);
        return sm;
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command){
        InlineKeyboardButton ikb =  new InlineKeyboardButton();
        ikb.setText(text);
        ikb.setCallbackData(command);
        return ikb;
    }
}
