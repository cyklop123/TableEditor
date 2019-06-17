package alerts;

import javafx.scene.control.Alert;

public class AlertInfo extends Prompt {
    public AlertInfo(String msg) {
        super(msg);
    }

    @Override
    public void show() {
        dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Information");
        dialog.setContentText(message);
        dialog.showAndWait();
    }
}
