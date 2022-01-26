package com.example.myapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class SocketSetup extends Thread {
    private static final String TAG = "bluetooth socket setup";
    private static final String appName = "HC-05";
    private static final UUID My_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //add UUID  00001101-0000-1000-8000-00805F9B34FB.
    private final BluetoothAdapter bluetoothAdapter;
    Context mContext;
    private BluetoothDevice myDevice;
    private UUID deviceUUID;
    //private AcceptThread myAcceptThread;
    private ConnectThread myConnectThread;
    private ConnectedThread myConnectedThread;
    private ProgressDialog myProgressDialog;
    private static final int SEND_SMS_PERMISSIONS_REQUEST = 1;

    public SocketSetup(Context context) {
        mContext = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }
    /* private class AcceptThread extends Thread{
            private BluetoothServerSocket mmServerSocket;
            // Use a temporary object that is later assigned to mmServerSocket
            // because mmServerSocket is final.
        public AcceptThread() {
                BluetoothServerSocket tmp = null;
                try {
                    // MY_UUID is the app's UUID string, also used by the client code.
                    tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, My_UUID);
                    Log.d(TAG, "AcceptThread: Setting up Server using: " + My_UUID);
                    Log.d(TAG, "detta är tmp" + tmp);
                } catch (IOException e) {
                    Log.d(TAG, "Accept thread IO exception", e);
                }
                mmServerSocket = tmp;
                Log.d(TAG, "detta är mmServerSocket" + mmServerSocket);
        }
        public void run() {
            Log.d(TAG, "run: AcceptThread Running.");
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned.
            while(true) {
                try {
                    Log.d(TAG, "socket tried accepted" +", this is mmServerSocket:" + mmServerSocket);
                    socket = mmServerSocket.accept();  // !!!!!!!!!!!!!!!!!!!!!
                    Log.d(TAG, "socket accepted");
                } catch (IOException e) {
                    Log.d(TAG, "Socket's accept() method failed", e);
                }
                if (socket != null) {
                    // A connection was accepted. Perform work associated with
                    // the connection in a separate thread.
                    connected(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                Log.d(TAG, "END mAcceptThread ");
            }
        }
        // Closes the connect socket and causes the thread to finish.
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.d(TAG, "Could not close the connect socket", e);
            }
        }
    }*/

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            myDevice = device;
            ParcelUuid[] UUIDS = device.getUuids();
            deviceUUID=uuid;
            Log.d(TAG, "deviceUUID: " + deviceUUID);

            BluetoothSocket tmp = null;
            try {
                tmp = myDevice.createRfcommSocketToServiceRecord(deviceUUID);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            mmSocket = tmp;
        }

        public void run() {

            bluetoothAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
                Log.d(TAG, "run: ConnectThread connected.");
            } catch (IOException ioException) {
                try {
                    mmSocket.close();
                    Log.d(TAG, "run: Closed Socket. " + ioException.getMessage());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + My_UUID );
            }
            connected(mmSocket);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }

    /**
     * start acceptThread
     */
    public synchronized void start(){
        if (myConnectThread != null){
            myConnectThread.cancel();
            myConnectThread = null;
        }
          /*  if (myAcceptThread == null){
                myAcceptThread = new AcceptThread();
                myAcceptThread.start();
            }*/
    }

    public void startClient(BluetoothDevice device, UUID uuid){
        myProgressDialog =  ProgressDialog.show(mContext,"connecting bluetooth","please wait", true);
        myConnectThread = new ConnectThread(device,uuid);
        myConnectThread.start();
    }

    private class ConnectedThread extends Thread{
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;

        public ConnectedThread(BluetoothSocket socket){
            Log.d(TAG, "ConnectedThread: Starting.");
            mmSocket=socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try{
                myProgressDialog.dismiss();
            }catch(NullPointerException e){
                e.printStackTrace();
            }

            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;

        }
        public void run(){
            byte[] buffer; // mmBuffer store for the stream
            buffer = new byte[1024];

            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity.
                    String incomingMessage = new String(buffer,0,numBytes);
                    sendText();
                    Log.d(TAG, "input stream" + incomingMessage);

                } catch (IOException e) {   // Den kastar exception här!!!!!!!!!!!!!!
                    Log.e(TAG, "error reading input stream, " + e.getMessage() );
                    break;
                }
            }
        }
    }
    private void connected(BluetoothSocket mmSocket) {
        myConnectedThread = new ConnectedThread(mmSocket);
        myConnectedThread.start();
    }
    private void sendText() {
        Message message = new Message();
        SmsManager smsManager = SmsManager.getDefault();

        if(getDataFromSharedPreferences(String.valueOf(0),mContext) != null && !getDataFromSharedPreferences(String.valueOf(0), mContext).isEmpty())
            smsManager.sendTextMessage(getDataFromSharedPreferences(String.valueOf(0),mContext), null, message.getSms(), null, null);
        if(getDataFromSharedPreferences(String.valueOf(1),mContext) != null && getDataFromSharedPreferences(String.valueOf(1),mContext).isEmpty()==false)
            smsManager.sendTextMessage(getDataFromSharedPreferences(String.valueOf(1), mContext), null, message.getSms(), null, null);
        if(getDataFromSharedPreferences(String.valueOf(2),mContext) != null && getDataFromSharedPreferences(String.valueOf(2),mContext).isEmpty()==false)
            smsManager.sendTextMessage(getDataFromSharedPreferences(String.valueOf(2),mContext), null, message.getSms(), null, null);
    }

    public static String getDataFromSharedPreferences(String key, Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
