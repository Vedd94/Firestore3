package com.example.firestore3;

public class Contacts {
    private String name;
    private String Ph_no;

    public Contacts(){

    }

    public Contacts(String name, String ph_no) {
        this.name = name;
        Ph_no = ph_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPh_no() {
        return Ph_no;
    }

    public void setPh_no(String ph_no) {
        Ph_no = ph_no;
    }
}
