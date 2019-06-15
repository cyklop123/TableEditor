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
    private CheckMenuItem skipTexSymbolsCheck;

    @FXML
    private MenuItem newTable;

    @FXML
    private BorderPane background;

    @FXML
    public void creatingNewTable(ActionEvent event) {
        try {
            if(background.getChildren().size() > 1)
                throw new Exception("Table already created");

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
            tvm.alert(e.getMessage());
        }
    }

    @FXML
    public void addRow(ActionEvent event)
    {
        System.out.println("add Row");
        if (tvm == null)
            tvm = new TableViewModel();
        if(background.getChildren().size() > 1) {
            tvm.addRow();
        }
        else{
            tvm.alert("Create table first");
        }
    }

    @FXML
    public void addColumn(ActionEvent event)
    {
        System.out.println("add col");
        if (tvm == null)
            tvm = new TableViewModel();
        if(background.getChildren().size() > 1) {
            tvm.addColumn();
        }
        else{
            tvm.alert("Create table first");
        }
    }

    @FXML
    public void deleteColumn(ActionEvent event)
    {
        System.out.println("delete col");
        if (tvm == null)
            tvm = new TableViewModel();
        if(background.getChildren().size() > 1) {
            tvm.deleteColumn();
        }
        else{
            tvm.alert("Create table first");
        }
    }

    @FXML
    public void deleteRow(ActionEvent event)
    {
        System.out.println("delete row");
        if (tvm == null)
            tvm = new TableViewModel();
        if(background.getChildren().size() > 1) {
            tvm.deleteRow();
        }
        else{
            tvm.alert("Create table first");
        }
    }

    @FXML
    public void creatingLaTexFile() {
        System.out.println("create latex");
        if (tvm == null)
            tvm = new TableViewModel();
        if(background.getChildren().size() > 1) {
            tvm.creatingLaTexFile(skipTexSymbolsCheck.isSelected());
        }
        else{
            tvm.alert("Create table first");
        }
    }
}
