package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToViewContacts (View view){
        Intent intent = new Intent (this, ViewContacts.class);
        startActivity(intent);
    }
    public void goToBluetoothSetup (View view){
        Intent intent = new Intent (this, BluetoothSetup.class);
        startActivity(intent);
    }

}
