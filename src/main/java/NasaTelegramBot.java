import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class NasaTelegramBot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private String NASA_URL = "https://api.nasa.gov/planetary/apod?api_key=";


    public NasaTelegramBot(String BOT_NAME, String BOT_TOKEN, String API_KEY) {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;
        this.NASA_URL += API_KEY;

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Create a SendMessage object with mandatory fields
            long chatID = update.getMessage().getChatId();
            String[] request_args = update.getMessage().getText().split(" ");
            String action = request_args[0];
            String answer;
            System.out.println(action);

            switch (action) {
                case "/hi":
                    answer = "Hello, " + update.getMessage().getFrom().getFirstName() + "!";
                    sendMessage(answer, chatID);
                    break;
                case "/img":
                    answer = Utils.getImageOfTheDayURL(NASA_URL);
                    sendMessage(answer, chatID);
                    break;
                case "/help":
                    sendMessage("Введите /img для получения сегодняшней картинки дня.\n" +
                            "Введите /hi чтобы поздороваться с ботом.\n" +
                            "Введите /date YYYY-MM-DD для получения картинки за произвольный день.",
                            chatID);
                    break;
                case "/date":
                    answer = Utils.getImageOfTheDayURL(NASA_URL + "&date=" + request_args[1]);
                    sendMessage(answer, chatID);
                    break;
                default:
                    sendMessage("Введите команду", chatID);
            }
        }
    }
    public void sendMessage(String text, long chatID){
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(text);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
