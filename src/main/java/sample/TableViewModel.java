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
        TextInputDialog amountDialog = new TextInputDialog("1");
        amountDialog.setTitle("Number of rows");
        amountDialog.setHeaderText("Enter amount of rows to be added");
        amountDialog.setContentText("Rows");

        Button amountOk = (Button) amountDialog.getDialogPane().lookupButton(ButtonType.OK);
        amountOk.addEventFilter(ActionEvent.ACTION,ae->{
            try{
                int val = Integer.parseInt(amountDialog.getEditor().getText());
                if(val < 1)
                    throw new Exception("Bad data");
            }
            catch (Exception e)
            {
                ae.consume();
            }
        });

        Optional<String> amountRes = amountDialog.showAndWait();

        amountRes.ifPresent(amountVal->{
            int amount = Integer.parseInt(amountVal);

            //drugi input dialog
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Input row number");
            dialog.setHeaderText("Enter row number");
            dialog.setContentText("After row(0=before first):");

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
                catch (Exception e)
                {
                    ae.consume();//zatrzymanie nacisniecia
                }
            });

            Optional<String> res = dialog.showAndWait();

            res.ifPresent(val -> {

                int index = Integer.parseInt(val);

                    //przesunięcie poprzednich o jedno niżej
                    for (Node node : container.getChildren()) {
                        int i = container.getRowIndex(node);
                        if (i > index) {
                            container.setRowIndex(node, i + 1 + amount);
                            int j = 1;
                            for (Node cell : ((HBox) (node)).getChildren()) {
                                ((TextField) (cell)).setPromptText("row " + (i + amount) + " col " + j);
                                j++;
                            }
                        }
                    }

                    for (int j = 0; j < amount; j++) {
                        HBox row = new HBox();
                        //ustawienie położenia
                        GridPane.setConstraints(row, 0, index + 1 + j);

                        //tworzenie wiersza
                        for (int i = 0; i < UserInputWindowController.getColumns(); i++) {
                            TextField cell = new TextField();
                            cell.setPromptText("row " + (index + 1 + j) + " col " + (i + 1));
                            cell.setMaxWidth(140);
                            cell.setMinWidth(40);
                            row.getChildren().addAll(cell);
                        }
                        container.getChildren().add(index + j, row);
                        UserInputWindowController.incrementRows();
                    }

                saveDataToArray();
            });

        });
    }

    public void addColumn()
    {
        TextInputDialog amountDialog = new TextInputDialog("1");
        amountDialog.setTitle("Number of columns");
        amountDialog.setHeaderText("Enter amount of columns to be added");
        amountDialog.setContentText("Columns");

        Button amountOk = (Button) amountDialog.getDialogPane().lookupButton(ButtonType.OK);
        amountOk.addEventFilter(ActionEvent.ACTION,ae->{
            try{
                int val = Integer.parseInt(amountDialog.getEditor().getText());
                if(val < 1)
                    throw new Exception("Bad data");
            }
            catch (Exception e)
            {
                ae.consume();
            }
        });

        Optional<String> amountRes = amountDialog.showAndWait();

        amountRes.ifPresent(amountVal->{
            int amount = Integer.parseInt(amountVal);

            //drugi input dialog
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Input column number");
            dialog.setHeaderText("Enter column number");
            dialog.setContentText("After column(0=before first):");

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
                catch (Exception e)
                {
                    ae.consume();//zatrzymanie nacisniecia
                }
            });

            Optional<String> res = dialog.showAndWait();

            res.ifPresent(val -> {

                int index = Integer.parseInt(val);

                for(int i=0; i<amount; i++) {
                    for (Node node : container.getChildren()) {
                        HBox hBox = (HBox) node;

                        TextField cell = new TextField();
                        cell.setPromptText("row " + container.getRowIndex(node) + " col " + (index+i));
                        cell.setMaxWidth(140);
                        cell.setMinWidth(40);

                        hBox.getChildren().add(index+i, cell);
                        int j=1;
                        for(Node row: hBox.getChildren())
                        {
                            if(j > (index+i)) {
                                TextField tf = (TextField) row;
                                tf.setPromptText("row " + container.getRowIndex(node) + " col " + j);
                            }
                            j++;
                        }
                    }
                    UserInputWindowController.incrementColumns();
                }
                saveDataToArray();
            });

        });
    }
}
