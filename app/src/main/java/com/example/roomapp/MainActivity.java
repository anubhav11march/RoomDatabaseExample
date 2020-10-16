package com.example.roomapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner selector;

    RecyclerView recyclerView;
    ArrayList<Contact> contacts;
    ContactsAdapter contactsAdapter;
    //    DatabaseHelper dbHelper;
    ContactsDatabase contactsDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Room App");
//        dbHelper = new DatabaseHelper(this);
        contactsDatabase = Room.databaseBuilder(this, ContactsDatabase.class, "ContactsDB").addCallback(callback).build();

        selector = findViewById(R.id.spinnerSort);
        String[] select = {"Date","Name"};
        contacts = new ArrayList<>();
        //new getAllContactsAsync().execute();
        new getAllContactsByDateAsync().execute();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, select);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selector.setAdapter(adapter);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        contactsAdapter = new ContactsAdapter(this, contacts, MainActivity.this);
        recyclerView.setAdapter(contactsAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(null, -1);
            }
        });

        createContact("Anubhav", "anubhav11march@gmail.com",0L);
        createContact("Dev", "devjadeja549@gmail.com",1L);
        createContact("Avinash", "avinashkhetri@gmail.com",2L);
        createContact("Rishabh", "sleepercell@gmail.com",3L);
        createContact("KyloDroid", "cafreakanu@gmail.com",4L);

        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    new getAllContactsByDateAsync().execute();
                }
                if(position == 1){
                    new getAllContactsByNameAsync().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addContact(final Contact contact, int position){
        LayoutInflater layoutInflater =LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_contact, null);

        final AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(view);

        TextView contactTitle = view.findViewById(R.id.title);

        final EditText editName = view.findViewById(R.id.name);
        final EditText editEmail = view.findViewById(R.id.email);


        contactTitle.setText("Add New Contact");
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }  );
        final AlertDialog alertDialog1 =alertDialog.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editEmail.getText()) || TextUtils.isEmpty(editName.getText())){
                    Toast.makeText(MainActivity.this, "Please enter both name & email", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    alertDialog1.dismiss();
                }
                createContact(editName.getText().toString(), editEmail.getText().toString(), System.currentTimeMillis());
            }
        });

    }


    public void updateContact(final Contact contact, final int position){
        LayoutInflater layoutInflater =LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_contact, null);

        final AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(view);

        TextView contactTitle = view.findViewById(R.id.title);

        final EditText editName = view.findViewById(R.id.name);
        final EditText editEmail = view.findViewById(R.id.email);


        contactTitle.setText("Edit Contact");

        if(contact!=null){
            editEmail.setText(contact.getEmail());
            editName.setText(contact.getName());
        }
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteContact(position);
                    }
                }  );
        final AlertDialog alertDialog1 =alertDialog.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editEmail.getText()) || TextUtils.isEmpty(editName.getText())){
                    Toast.makeText(MainActivity.this, "Please enter both name & email", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    alertDialog1.dismiss();
                }
                updateTheContact(editName.getText().toString(), editEmail.getText().toString(), position);
            }
        });

    }

    public void updateTheContact(String name, String email, int position){
        Contact contact = contacts.get(position);
        contact.setName(name);
        contact.setEmail(email);
        new updateContactAsynctask().execute(contact);

        contacts.set(position, contact);


    }

    public void deleteContact(int position){
        Contact contact = contacts.get(position);
        new deleteContactAsyncTask().execute(contact);
        contacts.remove(position);
    }

    public void createContact(String name, String email, Long timestamp){
        new createContactAsyncTask().execute(new Contact(name, email, 0,timestamp));
    }

    private class getAllContactsAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            contacts.addAll(contactsDatabase.getContactDAO().getAllContacts());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private class getAllContactsByDateAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            contacts.clear();
            contacts.addAll(contactsDatabase.getContactDAO().getAllByDate());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private class getAllContactsByNameAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            contacts.clear();
            contacts.addAll(contactsDatabase.getContactDAO().getAllByName());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private class createContactAsyncTask extends AsyncTask<Contact, Void, Void>{

        @Override
        protected Void doInBackground(Contact... contactss) {
            long id = contactsDatabase.getContactDAO().addContact(contactss[0]);
            Contact contact = contactsDatabase.getContactDAO().getContact(id);
            if(contact!=null){
                contacts.add(0, contact);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private class updateContactAsynctask extends AsyncTask<Contact, Void, Void>{

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDatabase.getContactDAO().updateContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private class deleteContactAsyncTask extends AsyncTask<Contact, Void, Void>{

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDatabase.getContactDAO().deleteContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            contactsAdapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }
    }

    RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            Log.v("AAA", "onCreate()");
//            createContact("Anubhav", "anubhav11march@gmail.com",0L);
//            createContact("Dev", "devjadeja549@gmail.com",1L);
//            createContact("Avinash", "avinashkhetri@gmail.com",2L);
//            createContact("Rishabh", "sleepercell@gmail.com",3L);
//            createContact("KyloDroid", "cafreakanu@gmail.com",4L);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.v("AAA", "onOpen()");
        }
    };
}
