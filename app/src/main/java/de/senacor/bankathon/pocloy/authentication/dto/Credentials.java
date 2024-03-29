package de.senacor.bankathon.pocloy.authentication.dto;

public class Credentials {
    private String username;
    private String password;
    
    public Credentials(String username, String password) {
        this.username = username.toLowerCase();
        this.password = password;
    }

    public void setUsername(String email) {
        this.username = email.toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
