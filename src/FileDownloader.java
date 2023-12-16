import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDownloader {
    public static void main(String[] args) {
        // dla każdej linii / dopóki nie spotkasz Entera, generujemy plik mp3, nstępnie dopisujemy go do odpowiedniej karty.
        String text = "Hello how was your day?";
        try {
            String urlToMp3 = "https://translate.google.com/translate_tts?tl=en-US&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&client=tw-ob";
            URL url = new URL(urlToMp3);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                String cutText = text.substring(0, Math.min(text.length(), 20)).replace(" ", "_");

                Files.copy(inputStream, Paths.get(cutText + ".mp3")); // Zapisz pobrany plik jako audio.mp3
                System.out.println("Pobrano plik audio.mp3");
            } else {
                System.out.println("Błąd podczas pobierania pliku. Kod odpowiedzi: " + responseCode);
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
