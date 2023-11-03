package com.dictionary;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Speech {
    private static Media getMedia(String target) throws IOException {
        String urlStr =
                "https://translate.google.com/translate_tts?ie=UTF-8&q="
                + target
                + "&tl=en&client=tw-ob";
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        return new Media(urlStr);
    }

    public static void play(String text) throws IOException {
        Media media = getMedia(text);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}