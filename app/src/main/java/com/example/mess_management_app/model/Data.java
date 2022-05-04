package com.example.mess_management_app.model;

public class Data {
    String _id;
    String userName;
    String rollNo;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public Data(String _id, String userName, String rollNo) {
        this._id = _id;
        this.userName = userName;
        this.rollNo = rollNo;
    }
}
