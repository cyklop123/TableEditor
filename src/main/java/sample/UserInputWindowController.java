package sample;

import alerts.AlertError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UserInputWindowController {

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
            if (rows <= 0 || columns <= 0) {
                throw new Exception("Too low value");
            }
            stage.close();
        }
        catch (NumberFormatException error) {
            new AlertError("Wrong data").show();
            rows = 0;
            columns = 0;
        }
        catch (Exception e) {
            new AlertError("Wrong data").show();
            rows = 0;
            columns = 0;
        }
    }

    static public Integer getColumns() {
        return columns;
    }

    static public Integer getRows() {
        return rows;
    }
}
