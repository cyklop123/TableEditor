package sample;


import alerts.AlertError;
import alerts.InputIntPrompt;
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

            System.out.println("przed"+background.getChildren().size());
            if(tvm == null)
                tvm = new TableViewModel();
            if(tvm.createTableView())
               background.setCenter(tvm.getScrollPane());

            System.out.println("Po"+background.getChildren().size());
        }
        catch (Exception e) {
            new AlertError(e.getMessage()).show();
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
            new AlertError("Create table first").show();
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
            new AlertError("Create table first").show();
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
            new AlertError("Create table first").show();
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
            new AlertError("Create table first").show();
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
            new AlertError("Create table first").show();
        }
    }
}
