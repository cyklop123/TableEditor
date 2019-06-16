package sample;

import alerts.InputIntPrompt;
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

    private int rows=0,cols=0;

    GridPane container = new GridPane();
    ScrollPane scrollPane = new ScrollPane(container);

    public boolean createTableView () {
        if (container.getChildren().size() == 0) {
            rows = UserInputWindowController.getRows();
            cols = UserInputWindowController.getColumns();
            for(int i=0; i<rows; i++)
            {
                HBox row = new HBox();
                GridPane.setConstraints(row, 0, (i+1));
                for(int j=0; j<cols; j++)
                {
                    TextField cell = new TextField();
                    cell.setPromptText("row "+(i+1)+" col "+(j+1));
                    cell.setMaxWidth(140);
                    cell.setMinWidth(40);
                    row.getChildren().addAll(cell);
                }
                container.getChildren().addAll(row);
            }
            return true;
        }
        return false;
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
        InputIntPrompt amountPrompt = new InputIntPrompt("Rows" ,"Enter amount of rows to be added", 1);
        amountPrompt.show();
        if(amountPrompt.getInt() != -1)
        {
            InputIntPrompt indexPrompt = new InputIntPrompt("After row(0=before first):","Enter row index",0, rows);
            indexPrompt.show();
            if(indexPrompt.getInt() != -1)
            {
                int amount = amountPrompt.getInt();
                int index = indexPrompt.getInt();

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
                    for (int i = 0; i < cols; i++) {
                        TextField cell = new TextField();
                        cell.setPromptText("row " + (index + 1 + j) + " col " + (i + 1));
                        cell.setMaxWidth(140);
                        cell.setMinWidth(40);
                        row.getChildren().addAll(cell);
                    }
                    container.getChildren().add(index + j, row);
                    rows++;
                }

                saveDataToArray();
            }
        }
    }

    public void deleteRow() {
        InputIntPrompt indexPrompt = new InputIntPrompt("Start with row:","Enter row index",1, rows);
        indexPrompt.show();
        if(indexPrompt.getInt() != -1) {
            int index = indexPrompt.getInt();
            InputIntPrompt amountPrompt = new InputIntPrompt("Rows", "Enter amount of rows to be deleted", 1, rows - index + 1);
            amountPrompt.show();
            if (amountPrompt.getInt() != -1) {
                int amount = amountPrompt.getInt();

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
                        rows--;
                    }
                    //przesunięcie elementów na siatce
                    for(int i=index; i<=rows; i++)
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
            }
        }
    }

    public void addColumn()
    {
        InputIntPrompt amountPrompt = new InputIntPrompt("Columns" ,"Enter amount of columns to be added", 1);
        amountPrompt.show();
        if(amountPrompt.getInt() != -1)
        {
            InputIntPrompt indexPrompt = new InputIntPrompt("After column(0=before first):","Enter column index",0, cols);
            indexPrompt.show();
            if(indexPrompt.getInt() != -1)
            {
                int amount = amountPrompt.getInt();
                int index = indexPrompt.getInt();

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
                    cols++;
                }
                saveDataToArray();
            }
        }
    }

    public void deleteColumn(){
        InputIntPrompt indexPrompt = new InputIntPrompt("Start with column:","Enter column index",1, cols);
        indexPrompt.show();
        if(indexPrompt.getInt() != -1) {
            int index = indexPrompt.getInt();
            InputIntPrompt amountPrompt = new InputIntPrompt("Columns", "Enter amount of columns to be deleted", 1, cols - index + 1);
            amountPrompt.show();
            if (amountPrompt.getInt() != -1) {
                int amount = amountPrompt.getInt();

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
                        for(int i=index; i<=(cols-amount); i++)
                        {
                            TextField tf = (TextField) row.getChildren().get(i-1);
                            tf.setPromptText("row "+k+" col "+i);
                        }
                        k++;
                    }
                    //zmniejszenie rozmiaru
                    for(int i=0; i<amount; i++)
                        cols--;
                }
                saveDataToArray();
            }
        }
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
