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

import javax.naming.PartialResultException;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    private TableViewModel tvm;
    private Integer columns, rows;

    @FXML
    private BorderPane background;

    @FXML
    public void creatingNewTable(ActionEvent event) {
        try {
            Parent windowRoot = FXMLLoader.load(getClass().getClassLoader().getResource("userInputRowAndColWindow.fxml"));

            Stage window = new Stage();

            window.setScene(new Scene(windowRoot));
            window.showAndWait();

            rows = UserInputRowAndColController.getRows();
            columns = UserInputRowAndColController.getColumns();

            if (rows != 0 && columns != 0) {
                if(tvm == null)
                    tvm = new TableViewModel();
                tvm.createTableView(rows, columns);
                background.setCenter(tvm.getTableView());
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    public void addRow(int row, int index)
    {
        if(tvm == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Create table first");
            alert.showAndWait();
        }

        else {
            List<ArrayList<TextField>> tmpData= new ArrayList<>();

            for(int i = 0; i < rows - index; i++) {
                ArrayList<TextField> tmpRow = new ArrayList<>();
                for(int j = 0; j < columns; j++) {
                    TextField textField = (TextField)tvm.getTableView().getChildren().get((index + i) * columns + j);
                    tmpRow.add(textField);
                }
                tmpData.add(tmpRow);
            }

            for(int i = 0; i < rows - index; i++) {
                for(int j = 0; j < columns; j++) {

                    TextField textField = (TextField)tvm.getTableView().getChildren().get(index*columns);
                    tvm.getTableView().getChildren().removeAll(textField);
                }
            }

            for(int i = 0; i < row; i++) {
                for (int j = 0; j < columns; j++) {
                    TextField cell = new TextField();
                    cell.setPromptText("row " + (index + i + 1) + " col " + (j + 1));
                    cell.setMaxWidth(140);
                    cell.setMinWidth(40);
                    tvm.getTableView().add(cell, j, index + i);
                }
            }

            for(int i = 0; i < rows - index; i++) {
                for(int j = 0; j < columns; j++) {
                    TextField cell = new TextField();
                    cell.setPromptText("row " + (index + row + i + 1) + " col " + (j + 1));
                    cell.setMaxWidth(140);
                    cell.setMinWidth(40);
                    System.out.print(i);
                    System.out.println(j);
                    cell.setText(tmpData.get(i).get(j).getText());
                    tvm.getTableView().add(cell, j, index + row + i);
                }
            }

            rows += row;
        }
        tvm.saveDataToArray();
    }

    @FXML
    public void addRowOnTheEnd(ActionEvent event) {
        try {
            Parent windowRoot = FXMLLoader.load(getClass().getClassLoader().getResource("userInputRowWindow.fxml"));

            Stage window = new Stage();

            window.setScene(new Scene(windowRoot));
            window.showAndWait();

            int numberOfRows = UserInputRowController.getRows();
            if (numberOfRows != 0) {
                addRow(numberOfRows, rows);
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    @FXML
    public void chooseRow() {
        try {
            Parent windowRoot = FXMLLoader.load(getClass().getClassLoader().getResource("userInputRowWindow.fxml"));

            Stage window = new Stage();

            window.setScene(new Scene(windowRoot));
            window.showAndWait();

            int numberOfRows = UserInputRowController.getRows();
            if (numberOfRows != 0) {
                Parent windowRoot2 = FXMLLoader.load(getClass().getClassLoader().getResource("userInputIndexWindow.fxml"));

                Stage window2 = new Stage();

                window2.setScene(new Scene(windowRoot2));
                window2.showAndWait();
                //System.out.println("BLAD");
                int index = UserInputIndexController.getClassIndex();
                if (index != 0) {
                    addRow(numberOfRows, index - 1);
                }
            }
        }
        catch (Exception e) {
            System.out.println("BLAD");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }
}
