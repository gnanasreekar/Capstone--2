package com.rgs.capstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText name;
    EditText pass;
    Button button;
    String names;
    String passs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        button = findViewById(R.id.button);


    }

    public void onclick(View view) {
        names = String.valueOf(name.getText());
        passs = String.valueOf(pass.getText());
        if (names != null) {
            if (names.equals("admin") || names.equals("ADMIN") || names.equals("Admin")) {
                if (passs.equals("admin") || passs.equals("ADMIN") || passs.equals("Admin")) {
                    startActivity(new Intent(login.this, Details_choice.class));
                } else {
                    Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Incoreect Details", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "No Data FOund", Toast.LENGTH_SHORT).show();
        }
    }
}
