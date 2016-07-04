package client;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Created by Makhobey Oleh on 6/2/16.
 * email: tajcig@ya.ru
 */
public class Main extends Application{
    private static String URL = "http://localhost:4567";
    private TextArea mArea;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        primaryStage.setTitle("Client");
        Scene scene = new Scene(group,600,600);

        setFields(group.getChildren());

        setButtons(group.getChildren());

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void setButtons(ObservableList<Node> list) {
        Button button = new Button("Get");
        button.setOnAction(this::onButtonClicked);
        button.setLayoutY(400);
        button.setLayoutX(200);
        list.add(button);
    }

    private void setFields(ObservableList<Node> list) {
        mArea = new TextArea();
        mArea.setPrefSize(600,300);
        list.add(mArea);

    }

    private void onButtonClicked(ActionEvent event){
        Client.getInstance().get(URL+ "/hello" , mArea::setText);
    }

}
