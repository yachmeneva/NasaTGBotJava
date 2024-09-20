import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        FileInputStream file;
        Properties property = new Properties();
        String bot_name = "";
        String bot_token = "";
        String nasa_apikey = "";

        try {
            file = new FileInputStream("src/main/resources/config.properties");
            property.load(file);

            bot_name = property.getProperty("tg_bot.name");
            bot_token = property.getProperty("tg_bot.token");
            nasa_apikey = property.getProperty("nasa.api_key");

            System.out.println("bot_name: " + bot_name
                    + ", bot_token: " + bot_token
                    + ", nasa_apikey: " + nasa_apikey);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл config.properties отсуствует.");
        }

        //NasaTelegramBot bot = new NasaTelegramBot("oy_nasa_image_bot", "8104762105:AAFwNHZ5jJ80gQH6UBWhDjgZBr53JzbBiyk");
        NasaTelegramBot bot = new NasaTelegramBot(bot_name, bot_token, nasa_apikey);
    }
}
