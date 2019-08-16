package com.example.roomapp;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Contact> contacts;
    ContactsAdapter contactsAdapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Room App");
        dbHelper = new DatabaseHelper(this);
        contacts = new ArrayList<>();
        contacts.addAll(dbHelper.getAllCOntacts());


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
                createContact(editName.getText().toString(), editEmail.getText().toString());
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
        dbHelper.updateContact(contact);
        contacts.set(position, contact);
        contactsAdapter.notifyDataSetChanged();

    }

    public void deleteContact(int position){
        Contact contact = contacts.get(position);
        dbHelper.deleteCOntact(contact);
        contacts.remove(position);
        contactsAdapter.notifyDataSetChanged();
    }

    public void createContact(String name, String email){
        long id = dbHelper.insertContact(name, email);
        Contact contact = dbHelper.getContact(id);
        if(contact!=null){
            contacts.add(0, contact);
            contactsAdapter.notifyDataSetChanged();
        }
    }
}
