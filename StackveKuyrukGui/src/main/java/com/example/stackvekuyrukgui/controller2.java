package com.example.stackvekuyrukgui;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
public class controller2 {
    private Stage stage;
    private Scene scene;
    Queue<Label> queue = new LinkedList();
    @FXML
    AnchorPane pane;
    @FXML
    TextField nameArea;
    @FXML
    Text sizeTxt;
    Label recs[];
    Label disardakiler[];
    String name;
    boolean calisiyorMu = false;
    int i = 0;
    int y = 38;
    int tSayisi = 0;
    int sayac = 0;
    int sag = 400;
    int y1 = 360;
    public void clear() {
        if (recs != null) {
            for (int j = 0; j < recs.length; j++) {
                pane.getChildren().remove(recs[j]);
            }
            recs = null;
            i = 0;
            y = 38;
            tSayisi = 0;
            sayac = 0;
            y1 = 360;
            sag = 400;
        }
    }

    public void creat() {
        clear();
        int xOfChars = 150;
        int yOfChars = 540;
        name = nameArea.getText();
        if (recs == null) {
            if (name.length() <= 6) {
                recs = new Label[name.length()];
                disardakiler = new Label[name.length()];
                for (int j = 0; j < name.length(); j++) {
                    if (j < 6) {
                        Label eleman = new Label(String.valueOf(name.charAt(name.length() - j - 1)));
                        eleman.setBackground(new Background(new BackgroundFill(Color.rgb(135, 35, 65), new CornerRadii(0), new Insets(0))));
                        eleman.setMinWidth(50);
                        eleman.setMinHeight(50);
                        eleman.setAlignment(Pos.CENTER);
                        eleman.setTextFill(Color.WHITE);
                        eleman.setBorder(Border.stroke(Color.WHITE));
                        eleman.setFont(Font.font("sans serif", FontWeight.BOLD, 18));
                        recs[j] = eleman;
                        recs[j].setLayoutX(xOfChars);
                        recs[j].setLayoutY(yOfChars);
                        pane.getChildren().add(recs[j]);
                        sayac++;
                        yOfChars -= 55;
                    }
                }
            }
            y = y + (55 * (6 - sayac));
        }
    }

    public void dontShow() {
        if (!queue.isEmpty()) {
            queue.peek().setBackground(new Background(new BackgroundFill(Color.rgb(135, 35, 65), new CornerRadii(0), new Insets(0))));
        }
    }

    public void show() {
        if (!queue.isEmpty()) {
            queue.peek().setBackground(new Background(new BackgroundFill(Color.rgb(242, 140, 40), new CornerRadii(0), new Insets(0))));
        }
    }
    public void switchScene2(ActionEvent action) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage = (Stage) ((Node) action.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void pop() {
        if (!queue.isEmpty() && !calisiyorMu) {
            for (Label eleman : queue) {
                if (eleman != null) {
                    Path sagKaydirma = new Path();
                    sagKaydirma.getElements().add(new MoveTo(eleman.getTranslateX() + 25, eleman.getTranslateY() + 25));
                    sagKaydirma.getElements().add(new HLineTo(eleman.getTranslateX() + 80));
                    PathTransition pathdeneme = new PathTransition();
                    pathdeneme.setNode(eleman);
                    pathdeneme.setPath(sagKaydirma);
                    pathdeneme.setDuration(Duration.millis(1000));
                    pathdeneme.setCycleCount(1);
                    pathdeneme.playFromStart();
                }
            }

            Path path = new Path();
            path.getElements().add(new MoveTo(queue.peek().getTranslateX() + 25, queue.peek().getTranslateY() + 25));
            path.getElements().add(new HLineTo(queue.peek().getTranslateX() + 110));
            path.getElements().add(new VLineTo(queue.peek().getTranslateY() + y1));

            PathTransition pathTransition = new PathTransition();
            pathTransition.setNode(queue.peek());
            pathTransition.setPath(path);
            pathTransition.setDuration(Duration.millis(1000));
            pathTransition.setCycleCount(1);
            pathTransition.play();
            calisiyorMu = true;
            pathTransition.setOnFinished(ev -> {
                disardakiler[tSayisi] = queue.poll();
                calisiyorMu = false;
                y1 -= 55;
                sag += 55;
                sizeTxt.setText("Kuyruk Boyutu: "+queue.size());
            });
        }
    }
    public void push() {
        if (!calisiyorMu) {
            if (recs != null) {
                if (i < recs.length) {
                    Path path1 = new Path();
                    path1.getElements().add(new MoveTo(recs[recs.length - i - 1].getTranslateX() + 25, recs[recs.length - i - 1].getTranslateY() + 25));
                    path1.getElements().add(new VLineTo(recs[recs.length - i - 1].getTranslateY() - y));
                    path1.getElements().add(new HLineTo(recs[recs.length - i - 1].getTranslateX() + sag));

                    PathTransition pathTransition = new PathTransition();
                    pathTransition.setNode(recs[recs.length - i - 1]);
                    pathTransition.setPath(path1);
                    pathTransition.setDuration(Duration.millis(1000));
                    pathTransition.setCycleCount(1);
                    pathTransition.playFromStart();
                    calisiyorMu = true;
                    pathTransition.setOnFinished(e -> {
                        y += 55;
                        i++;
                        sag -= 55;
                        queue.add(recs[recs.length - i]);
                        calisiyorMu = false;
                        sizeTxt.setText("Kuyruk Boyutu: "+queue.size());
                    });
                }
            }
        }
    }
}