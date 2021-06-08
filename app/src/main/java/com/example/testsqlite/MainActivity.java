package com.example.testsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    EditText name, contact, dob;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //just for commit
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        DB = new DBHelper(this);

        insert.setOnClickListener(v -> insertDialog());

        update.setOnClickListener(v -> updateDialog());

        delete.setOnClickListener(v -> deleteDialog());

        view.setOnClickListener(v -> {
            Cursor res = DB.getdata();

            if (res.getCount() == 0){
                Toast.makeText(MainActivity.this, "Data does not exists", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()){
                buffer.append("Name : " + res.getString(0) + "\n");
                buffer.append("Contact : " + res.getString(1) + "\n");
                buffer.append("DOB : " + res.getString(2) + "\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
            builder.setCancelable(true);
            builder.setTitle("Details");
            builder.setMessage(buffer.toString());
            builder.show();
        });
    }

    void insertDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_insert_data);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button insertBtn = dialog.findViewById(R.id.insertBtn);
        EditText Name = dialog.findViewById(R.id.name);
        EditText Contact = dialog.findViewById(R.id.contact);
        EditText Dob = dialog.findViewById(R.id.dob);
        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> dialog.dismiss());

        insertBtn.setOnClickListener(v -> {
            String NAME, CONTACT, DOB;
            NAME = Name.getText().toString();
            CONTACT = Contact.getText().toString();
            DOB = Dob.getText().toString();

            if (TextUtils.isEmpty(NAME)){
                Toasty.warning(getApplicationContext(), "Please fill the name!", Toast.LENGTH_SHORT, true).show();
            } else {
                boolean checkinsertdata = DB.insertuserdata(NAME, CONTACT, DOB);
                if (checkinsertdata){
                    Toasty.success(getApplicationContext(), "Inserted Successfully!", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();
                } else {
                    Toasty.error(getApplicationContext(), "Data not inserted!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        dialog.show();
    }

    void updateDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_update_data);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button updateBtn = dialog.findViewById(R.id.updateBtn);
        EditText Name = dialog.findViewById(R.id.name);
        EditText Contact = dialog.findViewById(R.id.contact);
        EditText Dob = dialog.findViewById(R.id.dob);
        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> dialog.dismiss());

        updateBtn.setOnClickListener(v -> {
            String NAME, CONTACT, DOB;
            NAME = Name.getText().toString();
            CONTACT = Contact.getText().toString();
            DOB = Dob.getText().toString();

            if (TextUtils.isEmpty(NAME)){
                Toasty.warning(getApplicationContext(), "Please fill the name!", Toast.LENGTH_SHORT, true).show();
            } else {
                boolean checkupdatedata = DB.updateuserdata(NAME, CONTACT, DOB);
                if (checkupdatedata){
                    Toasty.success(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();
                } else {
                    Toasty.error(getApplicationContext(), "Data not updated!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        dialog.show();
    }

    void deleteDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_delete_data);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button deleteBtn = dialog.findViewById(R.id.deleteBtn);
        EditText Name = dialog.findViewById(R.id.name);
        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> dialog.dismiss());

        deleteBtn.setOnClickListener(v -> {
            String NAME;
            NAME = Name.getText().toString();

            if (TextUtils.isEmpty(NAME)){
                Toasty.warning(getApplicationContext(), "Please fill the name!", Toast.LENGTH_SHORT, true).show();
            } else {
                boolean checkdeletedata = DB.deletedata(NAME);
                if (checkdeletedata){
                    Toasty.success(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();
                } else {
                    Toasty.error(getApplicationContext(), "Data not deleted!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        dialog.show();
    }
}