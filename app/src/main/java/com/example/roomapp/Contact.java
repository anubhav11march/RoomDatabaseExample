package com.example.roomapp;

public class Contact {
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID = "contact_id";
    public static final String COLUMN_EMAIL = "contact_email";
    public static final String COLUMN_NAME = "contact_name";

    private String name, email;
    private int id;

    public Contact(){

    }

    public Contact(String name, String email, int id){
        this.email = email;
        this.name = name;
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_EMAIL + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
}
//
//package com.example.roomapp;
//
//        import androidx.room.ColumnInfo;
//        import androidx.room.Entity;
//        import androidx.room.Ignore;
//        import androidx.room.PrimaryKey;
//
//@Deprecated
//public class Persons {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//    @ColumnInfo(name = "name")
//    private String name;
//    @ColumnInfo(name = "city")
//    private String city;
//
//    public Persons(int id, String name, String city){
//        this.id = id;
//        this.city = city;
//        this.name = name;
//    }
//
//    @Ignore
//    public Persons(String name, String city){
//        this.name = name;
//        this.city = city;
//    }
//
//}
