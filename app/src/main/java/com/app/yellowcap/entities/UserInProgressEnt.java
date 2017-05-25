package com.app.yellowcap.entities;

/**
 * Created on 5/24/2017.
 */

public class UserInProgressEnt {
    private String name;
    private String title;
    private String quoteamount;
    private String number;
    private Boolean callNumber;

    public UserInProgressEnt(String name, String title, String quoteamount, String number, Boolean callNumber) {
        this.name = name;
        this.title = title;
        this.quoteamount = quoteamount;
        this.number = number;
        this.callNumber = callNumber;
    }

    public Boolean getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(Boolean callNumber) {
        this.callNumber = callNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuoteamount() {
        return quoteamount;
    }

    public void setQuoteamount(String quoteamount) {
        this.quoteamount = quoteamount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
