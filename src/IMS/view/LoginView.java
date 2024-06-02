package IMS.view;

import IMS.controller.LoginController;
import IMS.model.InventoryModel;
import IMS.controller.DashboardController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginView {
    // Define UI components and controller
    private VBox loginView;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button cancelButton;
    private Label statusLabel;
    private LoginController controller;
    private Stage primaryStage;

    // Constructor for LoginView
    public LoginView(Stage primaryStage, LoginController controller) {
        this.controller = controller;
        this.primaryStage = primaryStage;

        // Create and configure the pane and controls
        createAndConfigurePane();
        createAndLayoutControls();
    }

    // Method to return the login view as a Parent node
    public Parent asParent() {
        return loginView;
    }

    // Method to create and layout controls on the login view
    private void createAndLayoutControls() {
        // Initialize controls
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        cancelButton = new Button("Cancel");
        statusLabel = new Label();

        // Create rows for username, password and login buttons
        HBox usernameRow = new HBox(20, new Label("Username"), usernameField);
        usernameRow.setAlignment(Pos.CENTER);
        HBox passwordRow = new HBox(20, new Label("Password"), passwordField);
        passwordRow.setAlignment(Pos.CENTER);
        HBox loginRow = new HBox(20, loginButton, cancelButton);
        loginRow.setAlignment(Pos.CENTER);

        // Set action for login button
        loginButton.setOnAction(e -> {
            // Check if username or password is empty
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                updateStatus("Please enter your credentials.");
                return; // Return early if credentials are not entered
            }
            // Check if authentication is successful
            if (controller.authenticate(usernameField.getText(), passwordField.getText())) {
                updateStatus("Login succeeded! Loading...");

                // Create dashboard view on successful login
                InventoryModel invModel = new InventoryModel();
                DashboardController dashboardController = new DashboardController(invModel, this.primaryStage);
                Scene dashboardScene = new Scene(new DashboardView(dashboardController, invModel).asParent(),
                        800, 600);

                this.primaryStage.setScene(dashboardScene);
                this.primaryStage.setTitle("NZXT's IMS Dashboard");

            } else {
                updateStatus("Wrong username or password!");
            }
        });

        // Set action for cancel button
        cancelButton.setOnAction(e -> {
            cancelConfirmation(this.primaryStage);
        });

        // Add all rows to the login view
        loginView.getChildren().addAll(usernameRow, passwordRow, statusLabel, loginRow);
    }

    // Method to create and configure the login view pane
    private void createAndConfigurePane() {
        loginView = new VBox(10);
        loginView.setAlignment(Pos.CENTER);
    }

    // Method to update the status label
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    // Method to show a confirmation dialog when cancel button is clicked
    private void cancelConfirmation(Stage motherStage) {
        // Create a new stage for the confirmation dialog
        Stage stage = new Stage();
        stage.initOwner(motherStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmation");

        // Create the confirmation dialog
        Label question = new Label("Do you want to quit the appication?");
        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(e -> {
            motherStage.close();
        });
        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            stage.close();
        });
        HBox buttonRow = new HBox(20, yesBtn, noBtn);
        buttonRow.setAlignment(Pos.CENTER);
        VBox root = new VBox(10, question, buttonRow);
        root.setAlignment(Pos.CENTER);

        // Show the confirmation dialog
        Scene confirmationScene = new Scene(root, 300, 100);
        stage.setScene(confirmationScene);
        stage.show();
    }
}