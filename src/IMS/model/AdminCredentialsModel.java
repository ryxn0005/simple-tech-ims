package IMS.model;

import java.util.Arrays;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Admin {
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;

    Admin(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getUsername() {
        return this.username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return this.username;
    }

    String getPassword() {
        return this.password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return this.password;
    }
}

public class AdminCredentialsModel {
    private final ObservableList<Admin> accounts = FXCollections.observableArrayList(
            Arrays.asList(new Admin("admin", "1"), new Admin("a", "1")));

    public boolean authenticate(String username, String password) {
        boolean found = false;
        for (Admin admin : accounts) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                found = true;
            }
        }
        return found;
    }
}
