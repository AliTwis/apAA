package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Ball;
import model.BallAnimation;
import model.SinglePlayerGame;
import model.TargetCircle;

public class GameMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(300);
        stage.setHeight(700);
        Pane gameLayout = new Pane();
//        TargetCircle targetCircle = new TargetCircle(stage.getWidth() / 2, stage.getHeight() / 2, 70);
//        Ball ball = new Ball(stage.getWidth() / 2, stage.getHeight() - 50, 10, 1, targetCircle);
//        gameLayout.getChildren().add(ball.getLine());
//        BallAnimation thisBallAnimation = new BallAnimation(ball, targetCircle);
//        ball.setBallAnimation(thisBallAnimation);
        Scene scene = new Scene(gameLayout);
        SinglePlayerGame game = new SinglePlayerGame(null, 10, stage, gameLayout);
        final boolean[] go = {true};
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.SPACE) && go[0]) {
                    game.getPlayer().getBalls().getFirst().getBallAnimation().play();
                    game.getPlayer().getBalls().removeFirst();
                    if (game.getPlayer().getBalls().size() > 0) {
                        game.getPlayer().getBalls().getFirst().getLine().setVisible(true);
                        gameLayout.getChildren().add(game.getPlayer().getBalls().getFirst());
                    }
                    go[0] = t;
                }
            }

        });
//        gameLayout.getChildren().addAll(targetCircle, ball);
        stage.setScene(scene);
        stage.show();
    }
}
