package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Input Rows");
        dialog.setHeaderText("Enter number of rows");
        dialog.setContentText("Rows:");

        //walidacja danych wejściowych
        Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.addEventFilter(ActionEvent.ACTION, ae -> {
            try{
                int val = Integer.parseInt(dialog.getEditor().getText());
                if(val < 0 || val > UserInputWindowController.getRows())
                {
                    throw new Exception("Bad data");
                }
            }
            catch (NumberFormatException e)
            {
                ae.consume();
            }
            catch (Exception e)
            {
                ae.consume();
            }
        });

        Optional<String> res = dialog.showAndWait();

        res.ifPresent(val -> {

            int index = Integer.parseInt(val);

            HBox row = new HBox();
            //ustawienie położenia
            GridPane.setConstraints(row,0, index+1);
            //przesunięcie poprzednich o jedno niżej
            for(Node node: container.getChildren())
            {
                int i=container.getRowIndex(node);
                if(i>index)
                {
                    container.setRowIndex(node,i+1);
                    int j = 1;
                    for (Node cell : ((HBox) (node)).getChildren()) {
                        ((TextField) (cell)).setPromptText("row " + (i + 1) + " col" + j);
                        j++;
                    }
                }
            }
            //tworzenie wiersza
            for(int i = 0; i< UserInputWindowController.getColumns(); i++)
            {
                TextField cell = new TextField();
                cell.setPromptText("row "+(index+1)+" col "+(i+1));
                cell.setMaxWidth(140);
                cell.setMinWidth(40);
                row.getChildren().addAll(cell);
            }
            container.getChildren().add(index,row);
            UserInputWindowController.incrementRows();
            saveDataToArray();
            System.out.println(table.toString());
        });
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
