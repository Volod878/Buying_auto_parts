package ru.volod878.buying_auto_parts.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import ru.volod878.buying_auto_parts.model.AutoPartResult;

/**
 * Окно для изменения информации об автозапчасти.
 */
public class AutoPartEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField inStockField;
    @FXML
    private TextField deliveryPeriodField;

    private Stage dialogStage;
    private AutoPartResult autoPartResult;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Устанавливаем автозапчасть, информацию о котором будем менять
     */
    public void setAutoPart(AutoPartResult autoPartResult) {
        this.autoPartResult = autoPartResult;

        nameField.setText(autoPartResult.getName());
        priceField.setText(Double.toString(autoPartResult.getPrice()));
        inStockField.setText(Integer.toString(autoPartResult.getInStock()));
        deliveryPeriodField.setText(Integer.toString(autoPartResult.getDeliveryPeriod()));
    }

    /**
     * @return true, если пользователь кликнул Ок, в другом случае false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Ок.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            autoPartResult.setName(nameField.getText());
            autoPartResult.setPrice(Double.parseDouble(priceField.getText()));
            autoPartResult.setInStock(Integer.parseInt(inStockField.getText()));
            autoPartResult.setDeliveryPeriod(Integer.parseInt(deliveryPeriodField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Отмена.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Не корректно набрано название автозапчасти!\n";
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "Не корректно набрана цена автозапчасти!\n";
        }
        if (inStockField.getText() == null || inStockField.getText().length() == 0) {
            errorMessage += "Не корректно набрано количество автозапчасти!\n";
        } else {
            try {
                Integer.parseInt(deliveryPeriodField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Не корректно набрано количество автозапчасти (должно быть целое число)!\n";
            }
        }

        if (deliveryPeriodField.getText() == null || deliveryPeriodField.getText().length() == 0) {
            errorMessage += "Не корректно набран срок доставки!\n";
        } else {
            try {
                Integer.parseInt(deliveryPeriodField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Не корректно набран срок доставки (должен быть целое число)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка заполнения полей");
            alert.setHeaderText("Пожалуйста, исправьте недопустимые поля");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
