package com.controller;

import com.game.Hangman;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.media.AudioClip;

import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    private static final String soundCorrect = "src/main/resources/com/sound/correctAnswer.mp3";
    private static final String soundDrawing = "src/main/resources/com/sound/drawing.mp3";
    private static final String soundHang = "src/main/resources/com/sound/man_scream.mp3";
    private static final String soundWin = "src/main/resources/com/sound/win.mp3";
    public Button resetButton;
    public FontAwesomeIconView resetIcon;

    @FXML
    GridPane gridPaneConsonants;
    File file;

    public String answer;
    List<Label> listLabel = new ArrayList<>();
    @FXML
    public HBox Hbox;

    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public ImageView image3;
    @FXML
    public ImageView image4;
    @FXML
    public ImageView image5;
    @FXML
    public ImageView image6;
    @FXML
    public ImageView image7;
    @FXML
    public ImageView image8;
    @FXML
    public ImageView image9;
    @FXML
    public ImageView image10;

    List<ImageView> listImageview;

    List<Image> listImageHang;
    @FXML
    public ImageView imageHang;

    public static final int frameHang = 14;
    @FXML private Button buttonA;
    @FXML private Button buttonB;
    @FXML private Button buttonC;
    @FXML private Button buttonD;
    @FXML private Button buttonE;
    @FXML private Button buttonF;
    @FXML private Button buttonG;
    @FXML private Button buttonH;
    @FXML private Button buttonI;
    @FXML private Button buttonJ;
    @FXML private Button buttonK;
    @FXML private Button buttonL;
    @FXML private Button buttonM;
    @FXML private Button buttonN;
    @FXML private Button buttonO;
    @FXML private Button buttonP;
    @FXML private Button buttonQ;
    @FXML private Button buttonR;
    @FXML private Button buttonS;
    @FXML private Button buttonT;
    @FXML private Button buttonU;
    @FXML private Button buttonV;
    @FXML private Button buttonW;
    @FXML private Button buttonX;
    @FXML private Button buttonY;
    @FXML private Button buttonZ;

    private Hangman game;

    public void updateListLabel(String s, int index) {
        listLabel.get(index).setText(s);
    }
    public void updateHbox() {
        Hbox.getChildren().clear();
        for (Label l: listLabel) {
            Hbox.getChildren().add(l);
        }
        Hbox.setLayoutY(520);
        System.out.println(Hbox.getLayoutX() + " " + Hbox.getLayoutY());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new Hangman();
        Thread loadThread = new Thread(() -> {
            Platform.runLater(() -> {
                game.initialize();
                startConfig();
            });
        });
        loadThread.start();

        initListImage();
        setOpacity();
        resetButton.setVisible(false);
        resetIcon.setVisible(false);
        resetButton.setOnAction(event -> reset());
    }


    /**
     * Handles the event when a button is clicked.
     *
     * @param event The event triggered when the button is clicked.
     */
    public void addClickEvent(ActionEvent event) {
        if (!game.isGameOver() && !game.isWin()) {
            // Lấy đối tượng gửi sự kiện (button được nhấn)
            Button clickedButton = (Button) event.getSource();
            if (!game.isGameOver()) {
                handleClickEvent(clickedButton);
                clickedButton.setVisible(false);
            }
        }
        if (game.isWin()) {
            resetButton.setVisible(true);
            resetIcon.setVisible(true);
            System.out.println("Win");
        }
    }

    /**
     * Handles logic when a button is clicked in the context of a word guessing game.
     *
     * @param button The clicked button.
     */
    public void handleClickEvent(Button button) {
        String buttonID = button.getId();
        System.out.println(buttonID + " được nhấn!");
        buttonID = buttonID.toLowerCase().substring(6,7);
        char tmp = buttonID.charAt(0);
        if (!game.takeGuess(tmp)) {
            // Draw hangman
            updateImage();
        } else {
            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == tmp) {
                    listLabel.get(i).setText(buttonID.toUpperCase());
                }
            }
            if(game.isWin()){
                playSoundWin();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("YOU WIN");
                alert.setContentText("Do you want to continue playing?");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.isEmpty() || buttonType.get() == ButtonType.CANCEL) {
                    return;
                }
                reset();
            }
            else
            playSoundCorrectAnswer();
        }
    }

    public void initListImage() {
        listImageview = new ArrayList<>();
        listImageview.add(image1);
        listImageview.add(image2);
        listImageview.add(image3);
        listImageview.add(image4);
        listImageview.add(image5);
        listImageview.add(image6);
        listImageview.add(image7);
        listImageview.add(image8);
        listImageview.add(image9);
        listImageview.add(image10);

        listImageHang = new ArrayList<>();
        for(int i = 0; i < frameHang; i++) {
            Image tmp = new Image(getClass().getResource("/com/hangman/t" + (frameHang - i - 1) + ".png").toString());
            listImageHang.add(tmp);
        }
    }


    /**
     * Sets the opacity of the image views in the list to 0.0.
     * This method is used to hide a series of image views.
     */
    public void setOpacity() {
        for(int i = 0; i < 10; i++) {
            listImageview.get(i).setOpacity(0.0);
        }
    }

    public void updateImage() {
        if(game.isGameOver()){
            animationHang();
            for (int i = 0; i < listLabel.size(); i++) {
                if(listLabel.get(i).getText().equals("__")) {
                    listLabel.get(i).setText(Character.toString(Character.toUpperCase(answer.charAt(i))));
                    listLabel.get(i).setStyle("-fx-text-fill: red;");
                }
            }
/*            updateHbox();*/
            resetButton.setVisible(true);
            resetIcon.setVisible(true);
            System.out.println("Lose");
        } else {
            playSoundDrawing();
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), listImageview.get(game.getWrongGuess() - 1));
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }

    }
    // play the animation of Hang man.
    public void animationHang() {
        playSoundHang();
        for(int i = 3; i < 10; i++) {
            listImageview.get(i).setOpacity(0.0);
        }
        imageHang.setOpacity(1);
        imageHang.setImage(listImageHang.get(0));
        Timeline timeline = new Timeline();
        for (int i = 0; i < listImageHang.size(); i++) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 80),
                    e -> imageHang.setImage(listImageHang.get(finalI))));
        }
        timeline.setCycleCount(1);
        timeline.playFromStart();
        timeline.setOnFinished(e ->  Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("YOU LOSE");
            alert.setContentText("Do you want to continue playing?");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isEmpty() || buttonType.get() == ButtonType.CANCEL) {
                return;
            }
            reset();
        }));
    }

    public void playSoundCorrectAnswer() {
        File file = new File(soundCorrect);
        AudioClip audioClip = new AudioClip(file.toURI().toString());
        audioClip.play();
    }
    public void playSoundDrawing() {
        File file = new File(soundDrawing);
        AudioClip audioClip = new AudioClip(file.toURI().toString());
        audioClip.play();
    }

    public void playSoundHang() {
        File file = new File(soundHang);
        AudioClip audioClip = new AudioClip(file.toURI().toString());
        audioClip.play();
    }
    public void playSoundWin() {
        File file = new File(soundWin);
        AudioClip audioClip = new AudioClip(file.toURI().toString());
        audioClip.play();
    }

    public void reset() {
        System.out.println("reset");
        imageHang.setOpacity(0.0);
        for (Button button : Arrays.asList(buttonA, buttonB, buttonC, buttonD, buttonE,
                                            buttonF, buttonG, buttonH, buttonI, buttonJ,
                                            buttonK, buttonL, buttonM, buttonN, buttonO,
                                            buttonP, buttonQ, buttonR, buttonS, buttonT,
                                            buttonU, buttonV, buttonW, buttonX, buttonY,
                                            buttonZ)) {
            button.setVisible(true);
        }
        System.out.println("reset");
        startConfig();
    }

    /**
     * Initializes the game configuration, including reading the answer from a file,
     * setting up the list of labels, updating the display, initializing images,
     * and configuring opacity.
     */
    private void startConfig() {
        answer = game.randomWord();
        listLabel.clear();
        for (int i = 0; i < answer.length(); i++) {
            Label tmp = new Label("__");
            listLabel.add(tmp);
        }
        updateHbox();
        setOpacity();
        initListImage();
    }

    private boolean wining() {
        if (!game.isGameOver()) {
            return false;
        }
        for (Label label : listLabel) {
            if (label.getText().equals("__")) {
                return false;
            }
        }
        return true;
    }

}