package com.example.stackvekuyrukgui;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class controller implements Initializable {
    @FXML
    AnchorPane pane;
    @FXML
    TextField nameArea;
    @FXML
    Text sizeTxt;
    Stack<Label> stack;
    Label recs[];
    Label disardakiler[];
    String name;
    int i = 0;
    int y = 15;
    int t = 0;
    boolean calisiyorMu = false;
    public void clear() {
        if (recs != null) {
            for (int j = 0; j < recs.length; j++) {
                pane.getChildren().remove(recs[j]);
            }
            sizeTxt.setText("Stack Boyutu : ");
            recs = null;
            i = 0;
            y = 15;
            t = 0;
        }
    }

    public void creat() {
        stack = new Stack<>();
        int xOfChars = 290;
        int yOfChars = 540;
        name = nameArea.getText();
        if (recs == null) {
            if (name.length()<=6) {
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
                        xOfChars -= 55;
                    }
                }
            }
            nameArea.setText("");
        }
    }
    public void dontShow(){
        if (!stack.empty()) {
            stack.peek().setBackground(new Background(new BackgroundFill(Color.rgb(135, 35, 65), new CornerRadii(0), new Insets(0))));
        }
    }
    public void show(){
        if (!stack.empty()) {
            stack.peek().setBackground(new Background(new BackgroundFill(Color.rgb(242, 140, 40), new CornerRadii(0), new Insets(0))));
        }
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchScene1(ActionEvent action) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene2.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)action.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void pop() {
        if (!stack.empty() && !calisiyorMu) {
            for (int j = 0; j < disardakiler.length; j++) {
                if (disardakiler[j]!=null) {
                    Path sagKaydirma = new Path();
                    sagKaydirma.getElements().add(new MoveTo(disardakiler[j].getTranslateX() + 25, disardakiler[j].getTranslateY() + 25));
                    sagKaydirma.getElements().add(new HLineTo(disardakiler[j].getTranslateX() + 80));
                    PathTransition pathdeneme = new PathTransition();
                    pathdeneme.setNode(disardakiler[j]);
                    pathdeneme.setPath(sagKaydirma);
                    pathdeneme.setDuration(Duration.millis(1000));
                    pathdeneme.setCycleCount(1);
                    pathdeneme.playFromStart();
                }
            }
            Path path = new Path();
            path.getElements().add(new MoveTo(stack.peek().getTranslateX() + 25, stack.peek().getTranslateY() + 25));
            path.getElements().add(new LineTo(stack.peek().getTranslateX() + 25, -360));
            path.getElements().add(new HLineTo(stack.peek().getTranslateX() + 110));
            path.getElements().add(new VLineTo(25));


            PathTransition pathTransition = new PathTransition();
            pathTransition.setNode(stack.peek());
            pathTransition.setPath(path);
            pathTransition.setDuration(Duration.millis(1000));
            pathTransition.setCycleCount(1);
            pathTransition.play();
            calisiyorMu = true;
            pathTransition.setOnFinished(e -> {
                disardakiler[t] = stack.pop();
                calisiyorMu = false;
                y+=60;
                t++;
                sizeTxt.setText("Stack Boyutu : " + stack.size());
            });
        }
    }

    public void push() {
        if (!calisiyorMu) {
            if (recs != null) {
                if (i < recs.length) {
                    Path path1 = new Path();
                    path1.getElements().add(new MoveTo(recs[i].getTranslateX() + 25, recs[i].getTranslateY() + 25));
                    path1.getElements().add(new LineTo(recs[i].getTranslateX() + 25, -360));
                    path1.getElements().add(new HLineTo(recs[i].getTranslateX() + 110));
                    path1.getElements().add(new VLineTo(y));

                    PathTransition pathTransition = new PathTransition();
                    pathTransition.setNode(recs[i]);
                    pathTransition.setPath(path1);
                    pathTransition.setDuration(Duration.millis(1000));
                    pathTransition.setCycleCount(1);
                    pathTransition.playFromStart();
                    calisiyorMu = true;
                    pathTransition.setOnFinished(e -> {
                        stack.push(recs[i]);
                        y -= 60;
                        i++;
                        for (int j = 0 + i; j < recs.length; j++) {
                            Path sagKaydirma = new Path();
                            sagKaydirma.getElements().add(new MoveTo(recs[j].getTranslateX() + 25, recs[j].getTranslateY() + 25));
                            sagKaydirma.getElements().add(new HLineTo(recs[j].getTranslateX() + 80));
                            PathTransition pathdeneme = new PathTransition();
                            pathdeneme.setNode(recs[j]);
                            pathdeneme.setPath(sagKaydirma);
                            pathdeneme.setDuration(Duration.millis(400));
                            pathdeneme.setCycleCount(1);
                            pathdeneme.playFromStart();
                        }
                        sizeTxt.setText("Stack Boyutu : " + stack.size());
                        calisiyorMu = false;
                    });
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Line line = new Line(360, 220, 440, 220);
        line.setStroke(Color.rgb(23, 23, 23));
        line.setStrokeWidth(8);
        pane.getChildren().add(line);
    }
}