package com.suh.app.suh;

/**
 * Created by katyamalison on 10/11/16.
 */

public class User {
    //private Long id;
    private String email;
    private String first;
    private String last;
    private Boolean isAvailable;
    private Boolean showLocation;

    private User() {}

    public User(Integer i, String e, String f, String l, Boolean a, Boolean s) {
        this.email = e;
        this.first = f;
        this.last = l;
        this.isAvailable = a;
        this.showLocation = s;
        //this.id = Long.valueOf(i);
    }

    public String getFirst() { return first; }

    public String getLast() { return last; }

    public String getEmail() { return email; }

    public Boolean getShowLocation() { return showLocation; }

    public String getName() {
        return first + " " + last;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /*public Long getId() {
        return id;
    }*/
}
