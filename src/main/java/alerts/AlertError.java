package alerts;

import javafx.scene.control.Alert;

public class AlertError extends Prompt {
    public AlertError(String msg) {
        super(msg);
    }

    @Override
    public void show() {
        dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Error");
        dialog.setContentText(message);
        dialog.showAndWait();
    }
}
