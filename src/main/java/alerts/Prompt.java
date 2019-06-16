package alerts;

import javafx.scene.control.Dialog;

public abstract class Prompt {
    protected String message = "";
    protected Dialog dialog;
    public Prompt(String msg)
    {
        message = msg;
    }
    public abstract void show();
}
