package sample;


import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private BorderPane borderPane;

    private TableView<String> tableView;
    private int rows, columns;
    private List<TableColumn> tableCol = new ArrayList<>();

    public void creatingNewTable() {

        if (tableView == null) {

            GridPane getUserInputGridPane = new GridPane();

            Label row = new Label("Rows");
            TextField numberOfRows = new TextField();
            numberOfRows.setPromptText("0");

            Label column = new Label("Columns");
            TextField numberOfColumns = new TextField();
            numberOfColumns.setPromptText("0");

            Button confirm = new Button("Confirm");

            GridPane.setConstraints(row, 1, 1);
            GridPane.setConstraints(numberOfRows, 2, 1);

            GridPane.setConstraints(column, 1, 2);
            GridPane.setConstraints(numberOfColumns, 2, 2);
            GridPane.setConstraints(confirm, 2, 3);

            GridPane.setHalignment(confirm, HPos.CENTER);

            getUserInputGridPane.getChildren().addAll(row, column, numberOfColumns, numberOfRows, confirm);
            getUserInputGridPane.setHgap(10);
            getUserInputGridPane.setVgap(10);
            Scene getUsetInputScene = new Scene(getUserInputGridPane, 320, 140);
            Stage getUserInput = new Stage();
            getUserInput.setScene(getUsetInputScene);
            getUserInput.setTitle("Enter the data");

            confirm.setOnAction(e -> {
                if(tryGetData(numberOfRows.getText(), numberOfColumns.getText()))
                {
                    tableView = new TableView<>();
                    getUserInput.close();
                    for (int i = 1; i <= columns; i++) {
                        tableCol.add(new TableColumn("col-" + i));
                        tableView.getColumns().addAll(tableCol.get(i - 1));
                    }
                }
            });

            getUserInput.show();
        }

    }

    private boolean tryGetData(String r, String c) {
        try {
            columns = Integer.parseInt(c);
            rows = Integer.parseInt(r);
            return true;
        }
        catch (NumberFormatException error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wrong data");
            alert.setContentText(error.toString());
            alert.showAndWait();
            return false;
        }
    }
}
