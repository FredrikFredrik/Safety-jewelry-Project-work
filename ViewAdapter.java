
package com.example.myapp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewAdapter extends ArrayAdapter<BluetoothDevice> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<BluetoothDevice> mDevices;
    private int mViewResourceId;

    public ViewAdapter(Context context, int tvResourceId, ArrayList<BluetoothDevice> devices){
        super(context,tvResourceId,devices);
        this.mDevices = devices;
        mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
    }
    public View getView(int position, View deviceView, ViewGroup parent){
        deviceView = mLayoutInflater.inflate(mViewResourceId,null);
        BluetoothDevice device = mDevices.get(position);
        if(device!=null){
            TextView deviceName = (TextView) deviceView.findViewById(R.id.DeviceName);

            if(deviceName!=null){
                deviceName.setText(device.getName()+"\n");

            }
        }
        return deviceView;
    }
}