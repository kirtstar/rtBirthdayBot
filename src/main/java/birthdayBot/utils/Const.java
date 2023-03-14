package birthdayBot.utils;

public class Const {
    private Const(){};

    public static final class Common {
        private Common(){};
        public static final String START_MESSAGE =
                String.format("Привет! Для начала давай познакомимся, я бот напоминался о днях рождениях! %n"
                        + "Чтобы начать введите своё имя:");

        public static final String ACTION_NOT_AVAILABLE_NOW = "Данное действие сейчас недоступно";
        public static final String AGREE = "Верно!";
        public static final String BACK_TO_MAIN_MENU = "Вернуться";
        public static final String CHECK_NAME = "Ваше имя: %s?%nЕсли нет введите имя снова.";
        public static final String NEW_BDAY = "Добавить имениника";
        public static final String REMOVE_BDAY = "Удалить имениника";
        public static final String SCHEDULE = "Настройка оповещений";
    }

    public static final class CallBackQueries{
        private CallBackQueries(){};

        public static final String NAME_ACCEPT = "/name_accept";
        public static final String ADD_BDAY = "/add_bday";
        public static final String DELETE_BDAY = "/delete_bday";
        public static final String SCHEDULE_EDIT = "/edit_schedule";
        public static final String ADD_BDAY_ACCEPT = "/add_bday_accept";
        public static final String BACK_TO_MENU = "/back_to_menu";
    }



}

