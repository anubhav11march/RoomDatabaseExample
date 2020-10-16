package com.example.roomapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @ColumnInfo(name = "contact_name")
    private String name;
    @ColumnInfo(name = "contact_email")
    private String email;
    @ColumnInfo(name = "timestamp")
    private Long timeStamp;
    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    private int id;


    @Ignore
    public Contact(){

    }

    public Contact(String name, String email, int id, Long timeStamp){
        this.email = email;
        this.name = name;
        this.id = id;
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public Long getTimeStamp(){ return timeStamp; }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTimeStamp(Long timeStamp){ this.timeStamp = timeStamp; }

}

