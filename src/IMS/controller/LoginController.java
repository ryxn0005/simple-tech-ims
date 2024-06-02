package IMS.controller;

import IMS.model.AdminCredentialsModel;

// The LoginController class is responsible for authenticating admin login attempts.
public class LoginController {
    // Declare the model for admin credentials
    private AdminCredentialsModel adminModel;

    // Constructor for the LoginController class
    public LoginController(AdminCredentialsModel model) {
        // Initialize the admin credentials model
        this.adminModel = model;
    }

    // Method to authenticate a login attempt
    public boolean authenticate(String username, String password) {
        // Return the result of the authentication attempt
        return adminModel.authenticate(username, password);
    }
}