package com.example.myapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ViewContacts extends AppCompatActivity {
    private static final int SEND_SMS_PERMISSIONS_REQUEST = 1;
    private EditText phoneNumber0;
    private EditText phoneNumber1;
    private EditText phoneNumber2;
    private ArrayList<String> listOfContacts;
    private static final String pref_PhoneNO = "myPhoneNo";
    private TextView myTextView;
    private String saveList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        listOfContacts = new ArrayList<>();
        // calling the action bar

        ActionBar actionBar = getSupportActionBar();
        phoneNumber0 = (EditText) findViewById(R.id.addPhoneNumber);
        phoneNumber1 = (EditText) findViewById(R.id.phoneNumber1);
        phoneNumber2 = (EditText) findViewById(R.id.phoneNumber2);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getDataFromSharedPreferences(String.valueOf(0),getApplicationContext()) != null){
            phoneNumber0.setText(getDataFromSharedPreferences(String.valueOf(0),getApplicationContext()));

        }
        if(getDataFromSharedPreferences(String.valueOf(1),getApplicationContext()) != null){
            phoneNumber1.setText(getDataFromSharedPreferences(String.valueOf(1),getApplicationContext()));

        }
        if(getDataFromSharedPreferences(String.valueOf(2),getApplicationContext()) != null){
            phoneNumber2.setText(getDataFromSharedPreferences(String.valueOf(2),getApplicationContext()));

        }


        getPermissionToReadSMS();


    }
    @RequiresApi(api = Build.VERSION_CODES.M)

    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.SEND_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSIONS_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == SEND_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                //lägga in att man först här kan se knappen för att lägga till kontakter??
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onSendClick(View view) {
        Message message = new Message();
        EditText input;

        SmsManager smsManager = SmsManager.getDefault();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("PermissionTracking", "permissions are not granted");
            getPermissionToReadSMS();

        } else {
            Log.d("PermissionTracking", "Permissions are granted");

            if(getDataFromSharedPreferences(String.valueOf(0),getApplicationContext()) != null && getDataFromSharedPreferences(String.valueOf(0),getApplicationContext()).isEmpty()==false)
            smsManager.sendTextMessage(getDataFromSharedPreferences(String.valueOf(0),getApplicationContext()), null, message.getSms(), null, null);
            if(getDataFromSharedPreferences(String.valueOf(1),getApplicationContext()) != null && getDataFromSharedPreferences(String.valueOf(1),getApplicationContext()).isEmpty()==false)
                smsManager.sendTextMessage(getDataFromSharedPreferences(String.valueOf(1), getApplicationContext()), null, message.getSms(), null, null);
            if(getDataFromSharedPreferences(String.valueOf(2),getApplicationContext()) != null && getDataFromSharedPreferences(String.valueOf(2),getApplicationContext()).isEmpty()==false)
            smsManager.sendTextMessage(getDataFromSharedPreferences(String.valueOf(2),getApplicationContext()), null, message.getSms(), null, null);
            //  }
            Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void contactButtonClick(View view){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(String.valueOf(0));
        editor.commit();
        //Gå till metod putPref(), använder bara String.valueOf(0) men senare kan det förhoopningsvis vara någon typ av iterator för flera nummer.
        //putpref() lägger vårt telefonnr i sharedpreferences
        putPref(String.valueOf(0),phoneNumber0.getText().toString(),getApplicationContext());
        putPref(String.valueOf(1),phoneNumber1.getText().toString(),getApplicationContext());
        putPref(String.valueOf(2),phoneNumber2.getText().toString(),getApplicationContext());
        //Sätter en textview till att visa telefonnummret.

        Log.d("SPARAT", "This will save" + saveList);
        Log.d("Listan är", "Lista " + listOfContacts.toString());

    }

    public void ReadFile(){
        String tmp= "";
        String local=getDataFromSharedPreferences(String.valueOf(0),getApplicationContext());
        String[] number= local.split(";");
        for (int i = 0;i<number.length;i++){
            Log.d("LÄS", "Läste den rätt ? "+number[i]);
            if (!listOfContacts.contains(number[i])) {
                listOfContacts.add(number[i]);
            }
        }

      Log.d("LÄS", "Listan är  ? "+listOfContacts.toString());

    }

    public static String getDataFromSharedPreferences(String key, Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);


    }

    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }
