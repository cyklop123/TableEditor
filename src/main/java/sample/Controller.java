package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Controller {

    public List<TableColumn> tableColumnList = new ArrayList<>();
    tableViewModel tvm;

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

            Integer a = userInputWindowController.getRows();
            Integer b = userInputWindowController.getColumns();

            if (a != 0 && b != 0) {
                if(tvm == null)
                    tvm = new tableViewModel();
                tvm.createTableView(a, b);
                background.setCenter(tvm.getTableView());
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    public BorderPane getBackground() {
        System.out.println(this.background);
        return this.background;
    }

    @FXML
    public void addRow(ActionEvent event)
    {
        System.out.println("add Row");
        if(background.getChildren().size() > 1) {
            if (tvm == null)
                tvm = new tableViewModel();
            tvm.addRow();
        }
    }

}
