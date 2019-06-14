package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class userInputWindowController {

    static private int rows, columns;

    @FXML
    private TextField rowsInput, colsInput;

    @FXML
    private Button b1;


    @FXML
    public void creatingNewWindow(ActionEvent event) {
        try {
            rows = Integer.parseInt(rowsInput.getText());
            columns = Integer.parseInt(colsInput.getText());
            Stage stage = (Stage) b1.getScene().getWindow();
            stage.close();
        }
        catch (NumberFormatException error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wrong data");
            alert.setContentText(error.toString());
            alert.showAndWait();
        }
    }

    static public Integer getColumns() {
        return columns;
    }

    static public Integer getRows() {
        return rows;
    }
}
