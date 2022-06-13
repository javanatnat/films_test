package ru.javanatnat.javafxexample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application{
    private static final String EDIT_FILM_TITLE = "Edit film";
    private static final String NAME = "Name";
    private static final String YEAR = "Year";
    private static final String COUNTRY = "Country";
    private static final String GENRE = "Genre";
    private static final String BUDGET = "Budget";

    private static final int COLUMN_1 = 0;
    private static final int COLUMN_2 = 1;
    private static final int ROW_1 = 0;
    private static final int ROW_2 = 1;
    private static final int ROW_3 = 2;
    private static final int ROW_4 = 3;
    private static final int ROW_5 = 4;
    private static final int ROW_6 = 5;

    private DbFilmService dbFilmService;
    private FilmParam filmParam;
    private List<Film> films;

    public static void main(String[] args) {
        App.launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        dbFilmService = new DbFilmServiceImpl();
        filmParam = FilmParam.getInstance();
        films = dbFilmService.getAll();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Films");

        TableView<Film> filmTable = getFilmTableView();

        Scene sceneFilmTable = new Scene(
                new VBox(filmTable),
                800,
                400
        );

        filmTable.setOnMouseClicked(
                getFilmsOn2Click(primaryStage, filmTable)
        );

        primaryStage.setScene(sceneFilmTable);
        primaryStage.show();
    }

    private TableView<Film> getFilmTableView() {
        TableView<Film> tableView = new TableView<>();
        ObservableList<TableColumn<Film, ?>> columns = tableView.getColumns();

        TableColumn<Film, String> columnName = createColumn(NAME, String.class);
        columns.add(columnName);

        TableColumn<Film, Integer> columnYear = createColumn(YEAR, Integer.class);
        columnYear.setSortType(TableColumn.SortType.DESCENDING);
        columns.add(columnYear);

        columns.add(createColumn(COUNTRY, String.class));
        columns.add(createColumn(GENRE, String.class));
        columns.add(createColumn(BUDGET, Long.class));

        tableView.getItems().addAll(films);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(false);
        tableView.setMaxWidth(800);

        ObservableList<TableColumn<Film, ?>> orderColumns = tableView.getSortOrder();
        orderColumns.add(columnYear);
        orderColumns.add(columnName);

        return tableView;
    }

    private static <T> TableColumn<Film, T> createColumn(String field, Class<T> tClass) {
        TableColumn<Film, T> column = new TableColumn<>(field);
        column.setCellValueFactory(new PropertyValueFactory<>(field));
        return column;
    }

    private EventHandler<? super MouseEvent> getFilmsOn2Click(
            Stage primaryStage,
            TableView<Film> filmTable
    ) {
        return (MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
                filmParam.setFilm(selectedFilm);

                VBox vboxFilmEditor = getFormEditor();
                Scene sceneFilmEditor = new Scene(vboxFilmEditor);
                Stage stageFilmEditor = new Stage();

                stageFilmEditor.setScene(sceneFilmEditor);
                stageFilmEditor.setTitle(EDIT_FILM_TITLE);
                stageFilmEditor.initOwner(primaryStage);

                stageFilmEditor.setX(400);
                stageFilmEditor.setY(400);
                stageFilmEditor.setWidth(300);
                stageFilmEditor.setHeight(300);

                stageFilmEditor.showAndWait();

                ObservableList<Film> tableList = filmTable.getItems();
                if (filmParam.isEmpty()) {
                    tableList.remove(selectedFilm);
                } else {
                    Film returnFilm = filmParam.getFilm();
                    if (selectedFilm == null) {
                        tableList.add(returnFilm);
                    } else {
                        int index = tableList.indexOf(selectedFilm);
                        tableList.set(index, returnFilm);
                    }
                }
            }
        };
    }

    private VBox getFormEditor() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(20, 10, 20, 10));

        FormFilmEdit form = new FormFilmEdit(dbFilmService);

        gridPane.add(form.getLabelName(),       COLUMN_1, ROW_1);
        gridPane.add(form.getTextName(),        COLUMN_2, ROW_1);
        gridPane.add(form.getLabelYear(),       COLUMN_1, ROW_2);
        gridPane.add(form.getTextYear(),        COLUMN_2, ROW_2);
        gridPane.add(form.getLabelCountry(),    COLUMN_1, ROW_3);
        gridPane.add(form.getTextCountry(),     COLUMN_2, ROW_3);
        gridPane.add(form.getLabelGenre(),      COLUMN_1, ROW_4);
        gridPane.add(form.getTextGenre(),       COLUMN_2, ROW_4);
        gridPane.add(form.getLabelBudget(),     COLUMN_1, ROW_5);
        gridPane.add(form.getTextBudget(),      COLUMN_2, ROW_5);
        gridPane.add(form.getButtonSave(),      COLUMN_1, ROW_6);
        gridPane.add(form.getButtonDelete(),    COLUMN_2, ROW_6);

        return new VBox(gridPane);
    }
}
