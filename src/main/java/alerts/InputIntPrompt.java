package alerts;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class InputIntPrompt extends Prompt {
    String header;
    Integer value=-1,min,max=-1;
    public InputIntPrompt(String msg, String _header, Integer _min, Integer _max) {
        super(msg);
        header = _header;
        min = _min;
        max = _max;
    }
    public InputIntPrompt(String msg, String _header, Integer _min) {
        super(msg);
        header = _header;
        min = _min;
    }

    @Override
    public void show() {
        dialog = new TextInputDialog("");
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        Button amountOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        amountOk.addEventFilter(ActionEvent.ACTION, ae->{
            try{
                int val = Integer.parseInt(((TextInputDialog)dialog).getEditor().getText());
                System.out.println(val);
                if(val < min)
                    throw new Exception("Too low value");
                if(max>=0 && val>max)
                    throw new Exception("Too high value");
                value = val;
            }
            catch (Exception e)
            {
                ae.consume();
                new AlertError(e.getMessage()).show();
            }
        });

        dialog.showAndWait();
    }

    public int getInt(){
        return value;
    }
}
