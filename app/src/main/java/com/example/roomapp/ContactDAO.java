package com.example.roomapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert
    public long addContact(Contact contact);

    @Update
    public void updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("SELECT * FROM contacts")
    public List<Contact> getAllContacts();

    @Query("SELECT * from contacts where contact_id  == :contactId")
    public Contact getContact(long contactId);

    @Query("SELECT * FROM contacts ORDER BY contact_name")
    public List<Contact> getAllByName();

    @Query("SELECT * FROM contacts ORDER BY timestamp DESC")
    public List<Contact> getAllByDate();
}
