package com.example.roomapp;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactsDatabase extends RoomDatabase {

    public abstract ContactDAO getContactDAO();



}
