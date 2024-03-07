package quizmanager.presenter;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import quizmanager.model.QuizListElement;

import java.io.File;

public class FormUploadPresenter {

    public static final String FILE_CHOOSER_TITLE = "Open Resource File";
    public static final String FILE_CHOOSER_DESCRIPTION = "Excel Files";
    public static final String FILE_CHOOSER_EXTENSION = "*.xlsx";
    @FXML
    private Button okButton;

    @FXML
    private Label filePath;

    @FXML
    private TextField quizName;

    private Stage dialogStage;

    private QuizListElement quizListElement;

    private boolean approved;

    private File selectedFile;

    @FXML
    private void initialize() {
        okButton.disableProperty().bind(
                Bindings.isEmpty(quizName.textProperty())
                        .or(Bindings.isEmpty(filePath.textProperty()))
        );
    }

    @FXML
    private void handleOkAction() {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }



    @FXML
    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILE_CHOOSER_TITLE);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(FILE_CHOOSER_DESCRIPTION, FILE_CHOOSER_EXTENSION));
        File selectedFile = fileChooser.showOpenDialog(dialogStage);
        if (selectedFile != null) {
            this.selectedFile = selectedFile;
            updateControls();
        }
    }

    private void updateControls() {
        filePath.setText(selectedFile.toString());
        quizName.setText(selectedFile.getName());
    }

    private void updateModel() {
        quizListElement.setName(quizName.getText());
        quizListElement.setFile(selectedFile);

    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setData(QuizListElement quizListElement) {
        this.quizListElement = quizListElement;
    }

}
