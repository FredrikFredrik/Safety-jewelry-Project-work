package com.example.myapp;

public class Message {
    private String sms;
    private String cancel;
    public Message() {
    sms = "Hej, Jag är i en farlig situation. Snälla hjälp mig genom att ringa upp mig";
    cancel = "Jag är inte i fara längre, ignorera föregående meddelande";
    }
    public String getSms() {
        return sms;
    }
    public String getCancel(){return cancel;}
}
