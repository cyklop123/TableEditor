package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Controller {

    public List<TableColumn> tableColumnList = new ArrayList<>();

    @FXML
    private MenuItem newTable;


    @FXML
    private BorderPane background;

    @FXML
    public void creatingNewTable(ActionEvent event) {
        try {
            Parent windowRoot = FXMLLoader.load(getClass().getClassLoader().getResource("userInputWindow.fxml"));

            Stage window = new Stage();

            window.setScene(new Scene(windowRoot));
            window.showAndWait();


        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot open widnow");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }
}
