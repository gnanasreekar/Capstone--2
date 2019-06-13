package com.rgs.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Feedback extends AppCompatActivity {

    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.button_feed)
    Button buttonFeed;
    @BindView(R.id.edit2)
    EditText edit2;
    @BindView(R.id.edit3)
    EditText edit3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        edit.getText();


    }

    public void feedback_todb(View view) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");
        databaseReference.child("Name").setValue(edit.getText().toString());
        databaseReference.child("Email").setValue(edit2.getText().toString());
        databaseReference.child("Feedback").setValue(edit3.getText().toString());
        Toast.makeText(this, "ThankYou For the feedback", Toast.LENGTH_SHORT).show();
    }
}
