package com.app.yellowcap.entities;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class NewJobItem {

    private String logo;
    private String notification;
    private String arrow;

    public NewJobItem(String logo, String notification, String arrow){
        setLogo(logo);
        setNotification(notification);
        setArrow(arrow);
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getArrow() {
        return arrow;
    }

    public void setArrow(String arrow) {
        this.arrow = arrow;
    }
}
