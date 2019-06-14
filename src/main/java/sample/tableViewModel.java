package sample;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class tableViewModel {

    private Controller controller = new Controller();

    private  TableView<String> tableView = null;
    private  List<TableColumn> tableColumnList = new ArrayList<>();

    private Scene scene = null;

    public void createTableView (Integer rows, Integer columns) {
        if (tableView == null) {
            System.out.println(rows + columns);
            tableView = new TableView<>();
            System.out.println(rows + columns);
            for (int i = 1; i <= columns; i++) {
                tableColumnList.add(new TableColumn("col-" + i));
                tableView.getColumns().addAll(tableColumnList.get(i - 1));
                for( int j = 0; j < rows; j++) {
                    tableColumnList.get(i - 1).setCellValueFactory(new PropertyValueFactory<String, String>("cosik"));
                }
            }

        }
    }

    public TableView<String> getTableView() {
        return tableView;
    }
}
