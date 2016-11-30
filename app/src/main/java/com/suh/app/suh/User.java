package com.suh.app.suh;

/**
 * Created by katyamalison on 10/11/16.
 */

public class User {
    private String email;
    private String name;
    private Boolean isAvailable;
    private Boolean showLocation;

    private User() {}

    public User(String e, String n, Boolean a, Boolean s) {
        this.email = e;
        this.name = n;
        this.isAvailable = a;
        this.showLocation = s;
    }

    public String getEmail() { return email; }

    public Boolean getShowLocation() { return showLocation; }

    public String getName() {
        return name;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }
}
