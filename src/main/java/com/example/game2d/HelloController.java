package com.example.game2d;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView bg1, bg2, player, enemy;

    @FXML
    private Label labelPause, labelLose;


    private final int BG_WIDTH = 712;
    private ParallelTransition parallelTransition;
    private TranslateTransition enemyTr;

    public static boolean right = false;
    public static boolean left = false;
    public static boolean jump = false;
    public static boolean isPause = false;

    private int playerSpeed = 3, jumpDSpeed = 3 ;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if(jump && player.getLayoutY() > 90f){
                player.setLayoutY(player.getLayoutY() - playerSpeed);
            }
            else if(player.getLayoutY() <= 235f){
                jump = false;
                player.setLayoutY(player.getLayoutY() + jumpDSpeed);
            }

            if(right && player.getLayoutX() < 100f){
                player.setLayoutX(player.getLayoutX() + playerSpeed);
            }
            if(left && player.getLayoutX() > 28f){
                player.setLayoutX(player.getLayoutX() - playerSpeed);
            }

            if(isPause && !labelPause.isVisible()){
                playerSpeed = 0;
                jumpDSpeed = 0;
                parallelTransition.pause();
                enemyTr.pause();
                labelPause.setVisible(true);
            }
            else if(!isPause && labelPause.isVisible()){
                playerSpeed = 3;
                jumpDSpeed = 3;
                parallelTransition.play();
                enemyTr.play();
                labelPause.setVisible(false);
            }


        }
    };

    @FXML
    void initialize() {
        TranslateTransition bgOneTr = new TranslateTransition(Duration.millis(5000), bg1);
        bgOneTr.setFromX(0);
        bgOneTr.setToX(BG_WIDTH * -1);
        bgOneTr.setInterpolator(Interpolator.LINEAR);

        TranslateTransition bgTwoTr = new TranslateTransition(Duration.millis(5000), bg2);
        bgTwoTr.setFromX(0);
        bgTwoTr.setToX(BG_WIDTH * -1);
        bgTwoTr.setInterpolator(Interpolator.LINEAR);

        enemyTr = new TranslateTransition(Duration.millis(3500), enemy);
        enemyTr.setFromX(0);
        enemyTr.setToX(BG_WIDTH * -1 - 100);
        enemyTr.setInterpolator(Interpolator.LINEAR);
        enemyTr.setCycleCount(Animation.INDEFINITE);
        enemyTr.play();

        parallelTransition = new ParallelTransition(bgOneTr, bgTwoTr);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        timer.start();
    }

}
