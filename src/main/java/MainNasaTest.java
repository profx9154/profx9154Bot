import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainNasaTest {
    public static void main(String[] args) throws IOException {
        String page=downLoadWebPage("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=2&api_key=DEMO_KEY");
        int startIndex= page.lastIndexOf("img_src");
        int endIndex= page.lastIndexOf("earth_date");
        String url=page.substring(startIndex+10,endIndex-3);
        try(InputStream in =new URL(url).openStream()){
            Files.copy(in, Paths.get("newMars.JPG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Picture Saved");

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
}
