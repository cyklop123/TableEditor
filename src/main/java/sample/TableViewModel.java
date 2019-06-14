package sample;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class TableViewModel {
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
                    cell.setPromptText("row "+(i+1)+" col "+(j+1));
                    cell.setMaxWidth(140);
                    cell.setMinWidth(40);
                    row.getChildren().addAll(cell);
                }
                container.getChildren().addAll(row);
            }
        }
    }

    public GridPane getTableView() {
        return container;
    }

    public void saveDataToArray()
    {
        if(container.getChildren().size() > 0)
        {
            table = new ArrayList<ArrayList<String>>();
            for(Node node: container.getChildren())
            {
                HBox hbox = (HBox) node;
                ArrayList<String> row = new ArrayList<>();
                for(Node node1: hbox.getChildren())
                {
                    TextField tf = (TextField) node1;
                    row.add(tf.getText());
                }
                table.add(row);
            }
            System.out.println(table.toString());
        }
    }

    public void addRow()
    {
        HBox row = new HBox();
        GridPane.setConstraints(row,0, UserInputWindowController.getRows()+1);
        for(int i = 0; i< UserInputWindowController.getColumns(); i++)
        {
            TextField cell = new TextField();
            cell.setPromptText("row "+(UserInputWindowController.getRows()+1)+" col "+(i+1));
            cell.setMaxWidth(140);
            cell.setMinWidth(40);
            row.getChildren().addAll(cell);
        }
        container.getChildren().addAll(row);
        UserInputWindowController.incrementRows();
    }

    public void addColumn()
    {
        for(int i = 0; i< UserInputWindowController.getRows(); i++)
        {
            HBox row = (HBox) container.getChildren().get(i);
            TextField cell = new TextField();
            cell.setPromptText("row "+(i+1)+" col "+(UserInputWindowController.getColumns()+1));
            cell.setMaxWidth(140);
            cell.setMinWidth(40);
            row.getChildren().addAll(cell);
        }
        UserInputWindowController.incrementColumns();
    }
}