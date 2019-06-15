package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class Controller {
    private  TableViewModel tvm;

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

            Integer a = UserInputWindowController.getRows();
            Integer b = UserInputWindowController.getColumns();

            if (a != 0 && b != 0) {
                if(tvm == null)
                    tvm = new TableViewModel();
                tvm.createTableView(a, b);
                background.setCenter(tvm.getScrollPane());
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    @FXML
    public void addRow(ActionEvent event)
    {
        System.out.println("add Row");
        if(background.getChildren().size() > 1) {
            if (tvm == null)
                tvm = new TableViewModel();
            tvm.addRow();
        }
    }

    @FXML
    public void addColumn(ActionEvent event)
    {
        System.out.println("add col");
        if(background.getChildren().size() > 1) {
            if (tvm == null)
                tvm = new TableViewModel();
            tvm.addColumn();
        }
    }

    @FXML
    public void deleteColumn(ActionEvent event)
    {

    }

    @FXML
    public void deleteRow(ActionEvent event)
    {
        System.out.println("delete row");
        if (tvm == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Create table first");
            alert.showAndWait();
        }
        else {
            tvm.deleteRow();
        }
    }

    @FXML
    public void creatingLaTexFile() {

    }
}
