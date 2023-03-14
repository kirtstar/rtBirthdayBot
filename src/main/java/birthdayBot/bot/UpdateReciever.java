package birthdayBot.bot;

import birthdayBot.bot.handlers.interfaces.Handler;
import birthdayBot.entities.User;
import birthdayBot.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class UpdateReciever {
    private final List<Handler> handlers;
    private final UserRepository userRepository;

    public UpdateReciever(List<Handler> handlers, UserRepository userRepository) {
        this.handlers = handlers;
        this.userRepository = userRepository;
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update){
        try{
            if (isMessageWithText(update)) {
                Message message = update.getMessage();
                Long chatId = message.getFrom().getId();
                User user = userRepository.findByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));
                return getHandlerByState(user.getState()).handle(user, message.getText());
            } else if (update.hasCallbackQuery()){
                CallbackQuery callbackQuery = update.getCallbackQuery();
                Long chatId = callbackQuery.getFrom().getId();
                User user = userRepository.findByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));
                return getHandlerByCallBackQuery(callbackQuery.getData(), user.getState())
                        .handle(user, callbackQuery.getData());
            } else {
                //Here may be photo handler
                return Collections.emptyList();
            }
        } catch (Exception e){
            System.out.println("Problem here");
            System.out.println(Arrays.toString(e.getStackTrace()));
            return Collections.emptyList();
        }

    }

    private Handler getHandlerByState(State state){
        return handlers.stream()
                .filter(Objects::nonNull)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query, State state){
        return handlers.stream()
                .filter(Objects::nonNull)
                .filter(h -> h.operatedBotState().equals(state))
                .filter(h-> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update){
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}
