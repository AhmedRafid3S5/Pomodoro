package com.example.pomodoro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Pomodoro implements Initializable {

    @FXML
    private TabPane baseTab;

    @FXML
    private Label L_MIn;

    @FXML
    private Button L_Pause;

    @FXML
    private Label L_Sec;

    @FXML
    private Button L_Skip;

    @FXML
    private Button PomStart;

    @FXML
    private Label Pom_Hour;

    @FXML
    private Label Pom_Min;

    @FXML
    private Button Pom_Skip;

    @FXML
    private Label S_MIn;

    @FXML
    private Button S_Pause;

    @FXML
    private Label S_Sec;

    @FXML
    private Button S_Skip;


    private int min = 24;
    private int sec = 60;
    private int short_min = 4;
    private int long_min = 14;

    private Timeline timeline;
    private Timeline shortBreak_timeline;
    private Timeline longBreak_timeline;
    private boolean Paused;

    private int pomodoro_count = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Paused = false;
        pomodoro_count = 1;
        PomStart.setText("Pause");
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),e->{
            sec--;
            if(min > 0 && sec == 0)
            {
                min--; //decrement minutes
                sec = 59;//Reset sec countdown
            }

            if(min == 0 && sec==0 && pomodoro_count<=2)
            {
                pomodoro_count++;
                timeline.pause();
                min = 24;
                sec = 59;
                baseTab.getSelectionModel().select(1);
                playShortBreak();
            }

            else if(min ==0 && sec== 0 && pomodoro_count>2)
            {
                pomodoro_count = 1;
                timeline.pause();
                min = 24;
                sec = 59;
                baseTab.getSelectionModel().select(2);
                playLongBreak();

            }
            Pom_Hour.setText(String.valueOf(min));
            Pom_Min.setText(String.valueOf(sec));
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void playShortBreak()
    {

        S_Pause.setText("Pause");
        shortBreak_timeline = new Timeline(new KeyFrame(Duration.seconds(1),e->{
            sec--;
            if(short_min>0 && sec == 0)
            {
                short_min--; //decrement minutes
                sec = 59;//Reset sec countdown
            }
            if(short_min == 0 && sec == 0)
            {

                shortBreak_timeline.pause();
                short_min = 4;
                sec = 59;
                baseTab.getSelectionModel().select(0);
                timeline.play();
            }


            S_MIn.setText(String.valueOf(short_min));
            S_Sec.setText(String.valueOf(sec));
        }));

        shortBreak_timeline.setCycleCount(Animation.INDEFINITE);
        shortBreak_timeline.play();
    }

    public void playLongBreak()
    {
        L_Pause.setText("Pause");
        longBreak_timeline = new Timeline(new KeyFrame(Duration.seconds(1),e->{
            sec--;
            if(long_min>0 && sec == 0)
            {
                long_min--; //decrement minutes
                sec = 59;//Reset sec countdown
            }
            if(long_min == 0 && sec == 0)
            {

                longBreak_timeline.pause();
                long_min = 14;
                sec = 59;
                baseTab.getSelectionModel().select(0);
                timeline.play();
            }


            L_MIn.setText(String.valueOf(long_min));
            L_Sec.setText(String.valueOf(sec));
        }));

        longBreak_timeline.setCycleCount(Animation.INDEFINITE);
        longBreak_timeline.play();
    }


    @FXML
    public void action(ActionEvent event) throws IOException
    {
        if(!Paused) {
            PomStart.setText("Resume");
            timeline.pause();
            Paused = true;
        }

        else if(Paused)
        {
            PomStart.setText("Pause");
            timeline.play();
            Paused = false;

          // baseTab.getSelectionModel().select(1);
        }
    }
    @FXML
    public void short_Pause(ActionEvent event) throws IOException
    {
        if(!Paused) {
            S_Pause.setText("Resume");
            shortBreak_timeline.pause();
            Paused = true;
        }

        else if(Paused)
        {
            S_Pause.setText("Pause");
            shortBreak_timeline.play();
            Paused = false;

            // baseTab.getSelectionModel().select(1);
        }
    }
    @FXML
    public void long_Pause(ActionEvent event) throws IOException
    {
        if(!Paused) {
            L_Pause.setText("Resume");
            longBreak_timeline.pause();
            Paused = true;
        }

        else if(Paused)
        {
            L_Pause.setText("Pause");
            longBreak_timeline.play();
            Paused = false;

            // baseTab.getSelectionModel().select(1);
        }
    }
    @FXML

    public void skip_pomodoro(ActionEvent event) throws IOException{
        pomodoro_count++;
        timeline.pause();
        min = 24;
        sec = 59;

        if(pomodoro_count<=2) {
            baseTab.getSelectionModel().select(1);
            playShortBreak();
        }
        else if (pomodoro_count>2)
        {
            pomodoro_count = 1;
            baseTab.getSelectionModel().select(2);
            playLongBreak();
        }
    }
@FXML
    public void skip_shortBreak(ActionEvent event) throws IOException{

        short_min = 4;
        sec = 59;

        shortBreak_timeline.pause();
        baseTab.getSelectionModel().select(0);
        timeline.play();

    }
@FXML
    public void skip_LongBreak(ActionEvent event) throws IOException{
        longBreak_timeline.pause();
        long_min = 14;
        sec = 59;

        baseTab.getSelectionModel().select(0);
       timeline.play();
    }


}
