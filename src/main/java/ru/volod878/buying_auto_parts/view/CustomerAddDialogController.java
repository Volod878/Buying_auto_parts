package ru.volod878.buying_auto_parts.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.volod878.buying_auto_parts.model.CustomerResult;

/**
 * Окно для добавления нового клиента.
 */
public class CustomerAddDialogController {
    @FXML
    public TextField nameField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private CustomerResult customerResult;

    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Устанавливаем клиента, которого будем добавлять
     */
    public void setCustomer(CustomerResult customerResult) {
        this.customerResult = customerResult;
        nameField.setText(customerResult.getName());
    }

    /**
     * @return true, если пользователь кликнул Ок, в другом случае false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

     /**
     * Вызывается, когда пользователь кликнул по кнопке Добавить.
     */
    @FXML
    public void handleAddCustomer() {
        if (isInputValid()) {
            customerResult.setName(nameField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Отмена.
     * Окно закрывается
     */
    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовом поле.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Не корректно набрано имя клиента!\n";
        }

        if (errorMessage.length() == 0) {
            return true;

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, исправьте недопустимые поля");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
