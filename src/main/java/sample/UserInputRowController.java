package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UserInputRowController {

    private static int numberOfRows;

    @FXML
    private TextField rowTextField;

    @FXML
    private Button rowButton;

    @FXML
    public void getNumberOfRows() {
        try {
            numberOfRows = Integer.parseInt(rowTextField.getText());
            Stage stage = (Stage) rowButton.getScene().getWindow();
            if ( numberOfRows <= 0) {
                throw new Exception("Too low value");
            }
            stage.close();
        }
        catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wrong data");
            alert.setContentText(error.toString());
            alert.showAndWait();
            numberOfRows = 0;
        }
    }

    public static Integer getRows() {
        return numberOfRows;
    }
}
