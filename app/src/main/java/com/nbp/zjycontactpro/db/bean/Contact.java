package com.nbp.zjycontactpro.db.bean;

import java.io.Serializable;

/**
 * Created by zjygzc on 16/8/13.
 */
public class Contact implements Serializable{

    public int id = -1;
    public String name;
    public String phoneNumber;
    public String emailID;
    public String qqNumber;

    public Contact(String name, String phoneNumber, String emailID, String qqNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.qqNumber = qqNumber;
    }

    public Contact(int id, String name, String phoneNumber, String emailID, String qqNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.qqNumber = qqNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailID='" + emailID + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                '}';
    }
}
