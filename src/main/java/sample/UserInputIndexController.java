package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserInputIndexController {
    private static int index;

    @FXML
    private TextField indexTextField;

    @FXML
    private Button indexButton;

    @FXML
    public void getIndex() {
        try {
            index = Integer.parseInt(indexTextField.getText());
            Stage stage = (Stage) indexButton.getScene().getWindow();
            if ( index <= 0) {
                throw new Exception("Too low value");
            }
            stage.close();
        }
        catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wrong data");
            alert.setContentText(error.toString());
            alert.showAndWait();
            index = 0;
        }
    }

    public static Integer getClassIndex() {
        return index;
    }
}
