package ru.volod878.buying_auto_parts.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HelpController {
    @FXML
    public Label textLabel;

    /**
     * Инициализация класса-контроллера для отображения справки по приложению
     */
    @FXML
    private void initialize() {
        try {
            // Читаем справку из файла и отображаем на экране
            Path path = Path.of("src/main/resources/help.txt");
            String text = Files.readString(path);
            textLabel.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
