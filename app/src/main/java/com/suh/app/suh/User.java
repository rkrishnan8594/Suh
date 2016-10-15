package com.suh.app.suh;

/**
 * Created by katyamalison on 10/11/16.
 */

public class User {
    private Long id;
    private String name;
    private Boolean available;

    public User(String n, Boolean a, Integer i) {
        this.name = n;
        this.available = a;
        this.id = Long.valueOf(i);
    }
    public String getName() {
        return name;
    }
    public Boolean getAvailability() {
        return available;
    }
    public Long getId() {
        return id;
    }
}
