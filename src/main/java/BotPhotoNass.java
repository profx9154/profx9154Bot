import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BotPhotoNass extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "Profx9154_Bot";
    }

    @Override
    public String getBotToken() {
        return "6305919707:AAG1_LEhKM03oSAe0ybl-ktHf9jvHbh90Wc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup= new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow>keyboardRows=new ArrayList<>();
        KeyboardRow row= new KeyboardRow();
        KeyboardButton keyboardButton= new KeyboardButton("Get Photo!");
        row.add(keyboardButton);
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText( NasaUrlPhoto());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static String downLoadWebPage(String url) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        URLConnection urlConnection = new URL(url).openConnection();
        try (InputStream is = urlConnection.getInputStream(); BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            return result.toString();

        }
    }

    String NasaUrlPhoto(){
        String page = null;
        try {
            page = downLoadWebPage("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int startIndex = page.lastIndexOf("url");
        int endIndex = page.lastIndexOf("}");
        String url = page.substring(startIndex + 6, endIndex - 1);
        int ExplanationBeginIndex1= page.lastIndexOf("Explanation");
        int ExplanationEndIndex1= page.lastIndexOf("hdurl");
        String description=page.substring(ExplanationBeginIndex1+37,ExplanationEndIndex1-3);
        String responseToUser="The best photo of the day"+"\n"+url+"\n"+description;

        return responseToUser;

    }

    String MarsUrlPhoto(){
        String page = null;
        try {
            page = downLoadWebPage("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=2&api_key=DEMO_KEY");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int startIndex = page.lastIndexOf("img_src");
        int endIndex = page.lastIndexOf("earth_date");
        String url = page.substring(startIndex+10,endIndex-3);
        return url;

    }

}
