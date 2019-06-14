package sample;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewModel {

    private TableView<String> tableView;

    TableView<String> tableView = new TableView<>();

            for(int i = 1; i <= userInputWindowController.getColumns(); i++) {
        tableColumnList.add(new TableColumn("col-" + i));
        tableView.getColumns().addAll(tableColumnList.get(i - 1));
    }
            background.setCenter(tableView);

}
