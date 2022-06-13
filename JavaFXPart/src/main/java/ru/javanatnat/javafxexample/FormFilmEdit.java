package ru.javanatnat.javafxexample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

import static ru.javanatnat.javafxexample.TextFormat.getBudgetTextFormatter;
import static ru.javanatnat.javafxexample.TextFormat.getYearTextFormatter;

public class FormFilmEdit {
    private static final String EDIT_FILM_TITLE = "Edit film";
    private static final String NAME = "Name";
    private static final String YEAR = "Year";
    private static final String COUNTRY = "Country";
    private static final String GENRE = "Genre";
    private static final String BUDGET = "Budget";
    private static final String DELETE = "Delete";
    private static final String SAVE = "Save";

    private final DbFilmService dbFilmService;
    private final FilmParam filmParam;
    private final Label labelName;
    private final Label labelYear;
    private final Label labelCountry;
    private final Label labelGenre;
    private final Label labelBudget;

    private final TextField textName;
    private final TextField textYear;
    private final TextField textCountry;
    private final TextField textGenre;
    private final TextField textBudget;

    private final Button buttonDelete;
    private final Button buttonSave;

    public FormFilmEdit(DbFilmService dbFilmService) {
        this.dbFilmService = dbFilmService;

        filmParam = FilmParam.getInstance();
        Film editFilm = filmParam.getFilm();

        labelName = new Label(NAME);
        textName = createTextName(editFilm);

        labelYear = new Label(YEAR);
        textYear = createTextYear(editFilm);

        labelCountry = new Label(COUNTRY);
        textCountry = createTextCountry(editFilm);

        labelGenre = new Label(GENRE);
        textGenre = createTextGenre(editFilm);

        labelBudget = new Label(BUDGET);
        textBudget = createTextBudget(editFilm);

        buttonDelete = createButtonDelete(editFilm);
        buttonSave = createButtonSave(editFilm);
    }

    public Label getLabelName() {
        return labelName;
    }

    public Label getLabelYear() {
        return labelYear;
    }

    public Label getLabelCountry() {
        return labelCountry;
    }

    public Label getLabelGenre() {
        return labelGenre;
    }

    public Label getLabelBudget() {
        return labelBudget;
    }

    public TextField getTextName() {
        return textName;
    }

    public TextField getTextYear() {
        return textYear;
    }

    public TextField getTextCountry() {
        return textCountry;
    }

    public TextField getTextGenre() {
        return textGenre;
    }

    public TextField getTextBudget() {
        return textBudget;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }

    public Button getButtonSave() {
        return buttonSave;
    }

    private TextField createTextName(Film editFilm) {
        TextField textName = new TextField();
        if (!(editFilm == null)) {
            textName.setText(editFilm.getName());
            textName.setEditable(false);
        }
        return textName;
    }

    private TextField createTextYear(Film editFilm) {
        TextField textYear = new TextField();
        textYear.setTextFormatter(getYearTextFormatter());
        if (!(editFilm == null)) {
            textYear.setText(Integer.toString(editFilm.getYear()));
            textYear.setEditable(false);
        }
        return textYear;
    }

    private TextField createTextCountry(Film editFilm) {
        TextField textCountry = new TextField();
        if (!(editFilm == null)) {
            textCountry.setText(editFilm.getCountry());
            textCountry.setEditable(false);
        }
        return textCountry;
    }

    private TextField createTextGenre(Film editFilm) {
        TextField textGenre = new TextField();
        if (!(editFilm == null)) {
            textGenre.setText(editFilm.getGenre());
        }
        return textGenre;
    }

    private TextField createTextBudget(Film editFilm) {
        TextField textBudget = new TextField();
        textBudget.setTextFormatter(getBudgetTextFormatter());
        if (!(editFilm == null)) {
            textBudget.setText(Long.toString(editFilm.getBudget()));
        }
        return textBudget;
    }

    private Button createButtonDelete(Film editFilm) {
        Button buttonDelete = new Button(DELETE);
        buttonDelete.setOnAction((ActionEvent event) -> {
            filmParam.setFilm(null);
            dbFilmService.delete(editFilm);
            closeWindow(event);
        });
        buttonDelete.setPrefWidth(55);
        return buttonDelete;
    }

    private Button createButtonSave(Film editFilm) {
        Button buttonSave = new Button("Save");
        buttonSave.setDefaultButton(true);
        buttonSave.setOnAction((ActionEvent event) -> {
            if (textName.getText().isEmpty()
                    || textCountry.getText().isEmpty()
                    || textGenre.getText().isEmpty()) {
                showAlert("Не заполнены обязательные поля!");
            }
            Film film = new Film(
                    textName.getText(),
                    textCountry.getText(),
                    textGenre.getText(),
                    Long.parseLong(textBudget.getText()),
                    Integer.parseInt(textYear.getText())
                    );

            if (Objects.equals(editFilm,film) || (editFilm == null && !dbFilmService.exists(film))) {
                filmParam.setFilm(film);
                dbFilmService.save(film);
                closeWindow(event);
            } else {
                showAlert("Фильм с такими значениями уже есть в таблице!");
            }
        });
        buttonSave.setPrefWidth(55);

        return buttonSave;
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(EDIT_FILM_TITLE);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
