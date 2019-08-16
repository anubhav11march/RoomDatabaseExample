package com.example.roomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    Context context;
    ArrayList<Contact> contacts;
    MainActivity mainActivity;

    @NonNull
    @Override
    public ContactsAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactsViewHolder(itemView);
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView email;
        public ContactsViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);

        }
    }


    public ContactsAdapter(Context context, ArrayList<Contact> contacts, MainActivity mainActivity){
        this.context = context;
        this.contacts = contacts;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ContactsViewHolder holder, final int position) {
        final Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.updateContact(contact, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
