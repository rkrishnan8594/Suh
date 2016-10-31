package com.suh.app.suh;

/**
 * Created by katyamalison on 10/11/16.
 */

public class User {
    private Long id;
    private String email;
    private String first;
    private String last;
    private Boolean available;
    private Boolean showLocation;

    public User(Integer i, String e, String f, String l, Boolean a, Boolean s) {
        this.email = e;
        this.first = f;
        this.last = l;
        this.available = a;
        this.showLocation = s;
        this.id = Long.valueOf(i);
    }

    public String getName() {
        return first + " " + last;
    }

    public Boolean getAvailability() {
        return available;
    }

    public Long getId() {
        return id;
    }
}
