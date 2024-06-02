package IMS;

import IMS.controller.LoginController;
import IMS.model.AdminCredentialsModel;
import IMS.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("NZXT's Inventory Management System");
        AdminCredentialsModel adminModel = new AdminCredentialsModel();
        LoginController loginController = new LoginController(adminModel);
        LoginView loginView = new LoginView(primaryStage, loginController);

        Scene loginScene = new Scene(loginView.asParent(), 400, 300);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
