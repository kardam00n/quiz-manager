package quizmanager.model;

import java.io.File;

public class QuizListElement {
    private String name;

    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name + file.toString();
    }
}
