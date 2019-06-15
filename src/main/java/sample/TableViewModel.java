package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableViewModel {
    private static List<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

    GridPane container = new GridPane();
    ScrollPane scrollPane = new ScrollPane(container);

    public void createTableView (Integer rows, Integer columns) {
        if (container.getChildren().size() == 0) {
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

    public ScrollPane getScrollPane() {
        return scrollPane;
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
                alert(e.getMessage());
            }
        });

        Optional<String> amountRes = amountDialog.showAndWait();

        amountRes.ifPresent(amountVal->{
            int amount = Integer.parseInt(amountVal);

            //drugi input dialog
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Input row index");
            dialog.setHeaderText("Enter row index");
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
                    alert(e.getMessage());
                }
            });

            Optional<String> res = dialog.showAndWait();

            res.ifPresent(val -> {

                int index = Integer.parseInt(val);

                //przesunięcie poprzednich o jedno niżej
                for (Node node : container.getChildren()) {
                    int i = container.getRowIndex(node);
                    if (i > index) {
                        container.setRowIndex(node, i + amount);
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

    public void deleteRow() {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Input row index");
        dialog.setHeaderText("Enter row index");
        dialog.setContentText("Start with row:");

        //walidacja danych wejściowych
        Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.addEventFilter(ActionEvent.ACTION, ae -> {
            try{
                int val = Integer.parseInt(dialog.getEditor().getText());
                if(val < 1 || val > UserInputWindowController.getRows())
                {
                    throw new Exception("Bad data");
                }
            }
            catch (Exception e)
            {
                ae.consume();//zatrzymanie nacisniecia
                alert(e.getMessage());
            }
        });

        Optional<String> res = dialog.showAndWait();

        res.ifPresent(indexVal -> {
            int index = Integer.parseInt(indexVal);

            TextInputDialog amountDialog = new TextInputDialog("1");
            amountDialog.setTitle("Number of rows");
            amountDialog.setHeaderText("Enter amount of rows to be deleted");
            amountDialog.setContentText("Rows");

            Button amountOk = (Button) amountDialog.getDialogPane().lookupButton(ButtonType.OK);
            amountOk.addEventFilter(ActionEvent.ACTION,ae->{
                try{
                    int val = Integer.parseInt(amountDialog.getEditor().getText());
                    if(val < 1 || (index + val) > (UserInputWindowController.getRows() + 1))
                        throw new Exception("Wrong amount");
                }
                catch (Exception e)
                {
                    ae.consume();
                    alert(e.getMessage());
                }
            });

            Optional<String> amountRes = amountDialog.showAndWait();

            amountRes.ifPresent(amountVal->{
                int amount = Integer.parseInt(amountVal);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Deleting " + amount + " rows. Starting from "+index);
                alert.setContentText("Are you sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    //usunięcie
                    for(int i=index+amount-1; i>=index; i--)
                    {
                        HBox row = (HBox)container.getChildren().get(i-1);
                        row.getChildren().clear();
                        container.getChildren().remove(row);
                        UserInputWindowController.decrementRows();
                    }
                    //przesunięcie elementów na siatce
                    for(int i=index; i<=UserInputWindowController.getRows(); i++)
                    {
                        HBox row = (HBox) container.getChildren().get(i-1);
                        GridPane.setConstraints(row,0,i);
                        int j=1;
                        for(Node node: row.getChildren())
                        {
                            TextField tf = (TextField) node;
                            tf.setPromptText("row " + i + " col "+j);
                            j++;
                        }
                    }
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
                alert(e.getMessage());
            }
        });

        Optional<String> amountRes = amountDialog.showAndWait();

        amountRes.ifPresent(amountVal->{
            int amount = Integer.parseInt(amountVal);

            //drugi input dialog
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Input column index");
            dialog.setHeaderText("Enter column index");
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
                    alert(e.getMessage());
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

    public void deleteColumn(){
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Input column index");
        dialog.setHeaderText("Enter column index");
        dialog.setContentText("Start with column:");

        //walidacja danych wejściowych
        Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.addEventFilter(ActionEvent.ACTION, ae -> {
            try{
                int val = Integer.parseInt(dialog.getEditor().getText());
                if(val < 1 || val > UserInputWindowController.getColumns())
                {
                    throw new Exception("Bad data");
                }
            }
            catch (Exception e)
            {
                ae.consume();//zatrzymanie nacisniecia
                alert(e.getMessage());
            }
        });

        Optional<String> res = dialog.showAndWait();

        res.ifPresent(indexVal -> {
            int index = Integer.parseInt(indexVal);

            TextInputDialog amountDialog = new TextInputDialog("1");
            amountDialog.setTitle("Number of columns");
            amountDialog.setHeaderText("Enter amount of columns to be deleted");
            amountDialog.setContentText("Columns");

            Button amountOk = (Button) amountDialog.getDialogPane().lookupButton(ButtonType.OK);
            amountOk.addEventFilter(ActionEvent.ACTION,ae->{
                try{
                    int val = Integer.parseInt(amountDialog.getEditor().getText());
                    if(val < 1 || (index + val) > (UserInputWindowController.getColumns() + 1))
                        throw new Exception("Wrong amount");
                }
                catch (Exception e)
                {
                    ae.consume();
                    alert(e.getMessage());
                }
            });

            Optional<String> amountRes = amountDialog.showAndWait();

            amountRes.ifPresent(amountVal->{
                int amount = Integer.parseInt(amountVal);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Deleting " + amount + " columns. Starting from "+index);
                alert.setContentText("Are you sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    //usunięcie
                    int k=1;
                    for(Node node: container.getChildren())
                    {
                        HBox row = (HBox) node;
                        for(int i=index+amount-1; i>=index; i--)
                        {
                            row.getChildren().remove(row.getChildren().get(i-1));
                        }
                        for(int i=index; i<=(UserInputWindowController.getColumns()-amount); i++)
                        {
                            TextField tf = (TextField) row.getChildren().get(i-1);
                            tf.setPromptText("row "+k+" col "+i);
                        }
                        k++;
                    }
                    //zmniejszenie rozmiaru
                    for(int i=0; i<amount; i++)
                        UserInputWindowController.decrementColumns();
                }
                saveDataToArray();
            });

        });
    }

    public void alert(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void creatingLaTexFile(boolean skipTexSymbols) {
        saveDataToArray();
        TextInputDialog fileNameWindow = new TextInputDialog("");
        fileNameWindow.setTitle("Input file name");
        fileNameWindow.setHeaderText("Enter the file name");
        fileNameWindow.setContentText("File name");

        Button nameOk = (Button) fileNameWindow.getDialogPane().lookupButton(ButtonType.OK);
        nameOk.addEventFilter(ActionEvent.ACTION,ae->{
            try{
                String fileName = fileNameWindow.getEditor().getText();
                if(fileName.equals(""))
                    throw new Exception("Bad data");
            }
            catch (Exception e)
            {
                ae.consume();
            }
        });

        Optional<String> fileNameInput = fileNameWindow.showAndWait();
        fileNameInput.ifPresent(name-> {
            String fileName = name + ".tex";
            FileWriter out = null;
            //File LaTexFile = new File(fileName);
            try {
                String[] unavailableCharacters = {"\\", "%", "$", "_", "#", "&", "^", "<", ">"};
                int n = (int)container.getWidth() / 140;
                out = new FileWriter(new File(fileName));
                out.write("\\documentclass[12pt]{article}\n");
                out.write("\\begin{document}\n");
                out.write("\\begin{table}[]\n");
                out.write("\\begin {tabular}{" + new String(new char[n]).replace("\0", "|l") + '|' + "}\n");
                out.write("\\hline\n");
                for (List l: table) {
                    for (int i = 0; i < n; i++) {
                        String ourText = l.get(i).toString();
                        if (skipTexSymbols) {
                            for (String items : unavailableCharacters) {
                                if (ourText.contains(items)) {
                                    switch (items) {
                                        case "%":
                                            ourText = ourText.replace("%", "\\%");
                                            break;
                                        case "$":
                                            ourText = ourText.replace("$", "\\$");
                                            break;
                                        case "_":
                                            ourText = ourText.replace("_", "\\_");
                                            break;
                                        case "#":
                                            ourText = ourText.replace("#", "\\#");
                                            break;
                                        case "&":
                                            ourText = ourText.replace("&", "\\&");
                                            break;
                                        case "^":
                                            ourText = ourText.replace("^", "\\textasciicircum{}");
                                            break;
                                        case "<":
                                            ourText = ourText.replace("<", "\\textless{}");
                                            break;
                                        case ">":
                                            ourText = ourText.replace(">", "\\textgreater{}");
                                            break;
                                        case "\\":
                                            ourText = ourText.replace("\\", "\\textbackslash{}");
                                            break;
                                    }
                                }
                            }

                        }
                        out.write(ourText + " ");
                        if (i < n - 1) {
                            out.write("& ");
                        }
                    }
                    out.write("\\\\ \\hline\n");
                }
                out.write("\\end{tabular}\n");
                out.write("\\end{table}\n");
                out.write("\\end{document}\n");

                out.close();
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.toString());
                alert.showAndWait();
            }
        });
    }
}
