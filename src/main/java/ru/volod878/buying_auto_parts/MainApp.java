package ru.volod878.buying_auto_parts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.volod878.buying_auto_parts.entity.*;
import ru.volod878.buying_auto_parts.model.AutoPartResult;
import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.view.AutoPartEditDialogController;
import ru.volod878.buying_auto_parts.view.RootLayoutController;
import ru.volod878.buying_auto_parts.view.ShoppingCartController;

import java.io.IOException;

public class MainApp extends Application {
    private static Stage primaryStage;
    private BorderPane rootLayout;

    private static SessionFactory factory;

    public static SessionFactory getFactory() {
        return factory;
    }

    public static void main(String[] args) {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(AutoPart.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Order.class)
                    .addAnnotatedClass(Shop.class)
                    .addAnnotatedClass(ShoppingCart.class)
                    .buildSessionFactory();

            launch(args);

        } finally {
            factory.close();
        }

    }

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("Автозапчасти");

        MainApp.primaryStage.getIcons()
                .add(new Image("file:src/main/resources/ru/volod878/buying_auto_parts/images/icon.png"));

        initRootLayout();

        showShop();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/root-layout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


            // Даём контроллеру доступ к главному приложению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об автозапчастях в магазине
     */
    public void showShop() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/auto-parts-of-shop.fxml"));
            AnchorPane autoPartsReview = loader.load();

            rootLayout.setCenter(autoPartsReview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об автозапчастях на складе
     */
    public void showWareHouse() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/auto-parts-of-warehouse.fxml"));
            AnchorPane autoPartsReview = loader.load();

            rootLayout.setCenter(autoPartsReview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения о клиентах
     */
    public void showCustomer() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/customer.fxml"));
            AnchorPane autoPartsReview = loader.load();

            rootLayout.setCenter(autoPartsReview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Открывает диалоговое окно для изменения деталей указанной автозапчасти.
     * Если пользователь кликнул Ок, то изменения сохраняются в предоставленном
     * объекте автозапчасти и возвращается значение true.
     *
     * @param autoPartResult - объект автозапчасти, который надо изменить
     * @return true, если пользователь кликнул Ок, в противном случае false.
     */
    public static boolean showAutoPartEditDialog(AutoPartResult autoPartResult) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/auto-part-edit-dialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            dialogStage.getIcons()
                    .add(new Image("file:src/main/resources/ru/volod878/buying_auto_parts/images/icon.png"));
            dialogStage.setTitle("Редактор");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём автозапчасть в контроллер.
            AutoPartEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAutoPart(autoPartResult);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Открывает диалоговое окно для оплаты автозапчастей.
     * Если пользователь кликнул Оплатить, то изменения сохраняются в предоставленном
     * объекте заказа и возвращается значение true.
     *
     * @return true, если пользователь кликнул Оплатить, в противном случае false.
     */
    public static boolean showShoppingCardDialog(OrderResult orderResult) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/shopping-cart.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();

            dialogStage.getIcons()
                    .add(new Image("file:src/main/resources/ru/volod878/buying_auto_parts/images/icon.png"));
            dialogStage.setTitle("Оплата заказа");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём OrderResult в контроллер.
            ShoppingCartController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOrder(orderResult);
            controller.setCustomerLabel();
            controller.initTable();

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}