package sample;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class tableViewModel {
    private static List<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    GridPane container = new GridPane();

    public void createTableView (Integer rows, Integer columns) {
        if (table.isEmpty()) {
            for(int i=0; i<rows; i++)
            {
                HBox row = new HBox();
                GridPane.setConstraints(row, 0, (i+1));
                for(int j=0; j<columns; j++)
                {
                    TextField cell = new TextField();
                    cell.setPromptText("row "+i+" col "+j);
                    row.getChildren().addAll(cell);
                }
                container.getChildren().addAll(row);
            }
        }
    }

    public GridPane getTableView() {
        return container;
    }
}
