package ru.volod878.buying_auto_parts.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.volod878.buying_auto_parts.model.CustomerResult;

public class CustomerAddDialogController {
    @FXML
    public TextField nameField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private CustomerResult customerResult;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCustomer(CustomerResult customerResult) {
        this.customerResult = customerResult;

        nameField.setText(customerResult.getName());
    }


    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    public void handleAddCustomer() {
        if (isInputValid()) {
            customerResult.setName(nameField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Не корректно набрано название автозапчасти!\n";
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
