package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private static TableView<String> tableView = null;
    private static List<TableColumn> tableColumnList = new ArrayList<>();

    private static Scene scene = null;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));

        scene = new Scene(root, 800, 775);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void createTableView (Integer rows, Integer columns) {
        if (tableView == null) {
            System.out.println(rows + columns);
            tableView = new TableView<>();
            for (int i = 1; i <= columns; i++) {
                tableColumnList.add(new TableColumn("col-" + i));
                tableView.getColumns().addAll(tableColumnList.get(i - 1));
            }
            BorderPane borderPane = (BorderPane) scene.lookup("#background");
            borderPane.setCenter(tableView);
        }
    }
}
