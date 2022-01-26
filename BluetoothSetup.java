package com.example.myapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class BluetoothSetup extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "bluetooth setup";
    private BluetoothAdapter myBluetoothAdapter;
    private ArrayList<BluetoothDevice> pairedDeviceList= new ArrayList<>();
    public ViewAdapter viewAdapter;
    private ListView pairedDeviceView;
    private SocketSetup socketSetup;
    private static final UUID My_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //add UUID

    private BluetoothDevice chosenDevice;
    // Button btnStartConnection;


    final BroadcastReceiver myBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            View parentLayout = findViewById(android.R.id.content);

            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Snackbar.make(parentLayout, "Bluetooth is off", Snackbar.LENGTH_LONG).show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Snackbar.make(parentLayout, "Bluetooth is on", Snackbar.LENGTH_LONG).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Snackbar.make(parentLayout, "Turning on Bluetooth", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_setup);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //btnStartConnection = (Button) findViewById(R.id.btnStartConnection);

        myBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

        enableDisableBt();
        DeviceSetup();

    }


    public void enableDisableBt(){
        View parentLayout = findViewById(android.R.id.content);

        IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(myBroadcastReceiver1,BTIntent);

        if (myBluetoothAdapter == null) {
            Snackbar.make(parentLayout, "Device does not support bluetooth", Snackbar.LENGTH_LONG).show();
        }

        if (!myBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);

        }
    }
    public void DeviceSetup(){
        View parentLayout = findViewById(android.R.id.content);
        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
        pairedDeviceView = (ListView) findViewById(R.id.Paired_device_list);

        if (pairedDevices.size() > 0) {

            // There are paired devices. Get the name and address of each paired device.
            pairedDeviceList.addAll(pairedDevices);

            //printa ut arraylist till listview
            viewAdapter = new ViewAdapter(BluetoothSetup.this,R.layout.activity_device_list_adapter,pairedDeviceList);
            pairedDeviceView.setAdapter(viewAdapter);
        }
        else {
            Snackbar.make(parentLayout, "No paired devices", Snackbar.LENGTH_LONG).show();
        }
        pairedDeviceView.setOnItemClickListener(BluetoothSetup.this);
    }

    public void startBTConnection(BluetoothDevice device, UUID uuid){
        socketSetup.startClient(device,uuid);
    }

    public void startConnection(){
        startBTConnection(chosenDevice,My_UUID);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView,View view, int i, long l){

        myBluetoothAdapter.cancelDiscovery();

        String deviceName=pairedDeviceList.get(i).getName();
        String deviceAddress=pairedDeviceList.get(i).getAddress();
        Log.d(TAG, "device to connect to: "+ deviceName +"   "+ deviceAddress );

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN_MR1){
            pairedDeviceList.get(i).createBond();   //foundDevices.get(i).createBond();
            chosenDevice = pairedDeviceList.get(i);
            socketSetup = new SocketSetup(BluetoothSetup.this);
        }
        startConnection(); //TODO vart ska denna vara?
    }

    @Override
    public void onDestroy() { //TODO måste se till att alla recievers faktiskt är registrerade annars kommer appen krasha när man försöker köra unregister
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver1);
        myBluetoothAdapter.cancelDiscovery();
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
    //TODO recovery from fredde 14/12 innan 13

}