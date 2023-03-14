package birthdayBot;

import birthdayBot.bot.State;
import birthdayBot.bot.UpdateReciever;
import birthdayBot.entities.User;
import birthdayBot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

@Component
@ComponentScan({"birthdayBot"})
public class Bot extends TelegramLongPollingBot {

    private final UpdateReciever updateReciever;
    private final UserRepository userRepository;

    public Bot(UpdateReciever updateReciever, UserRepository userRepository) {
        this.updateReciever = updateReciever;
        this.userRepository = userRepository;
    }

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReciever.handle(update);
        if (messagesToSend != null && !messagesToSend.isEmpty()){
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage){
                    executeWithExceptionCheck((SendMessage) response);
                } else if (response instanceof SendPhoto){
                    executeWithExceptionCheck((SendPhoto) response);
                } else if (response instanceof SendDocument){
                    executeWithExceptionCheck((SendDocument) response);
                }
            });
        }
    }

    public void executeWithExceptionCheck(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            System.out.println(sendMessage.getChatId() + " не удалось отправить пользователю");
            User user = userRepository
                    .findByChatId(Long.parseLong(sendMessage.getChatId()))
                    .orElse(null);
            if (user != null){
                user.setState(State.REGISTRATION);
            }
            e.printStackTrace();
        }
    }

    public void executeWithExceptionCheck(SendPhoto sendPhoto){
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void executeWithExceptionCheck(SendDocument sendDocument){
        try {
            execute(sendDocument);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

//    @PostConstruct
//    public void start() {
//    }

}
