# Safety-jewelry-Project-work


![FinishedProduct2](https://user-images.githubusercontent.com/63136833/151145342-75e0c701-10b6-4490-9dfd-d87e81466c7b.jpg)
![FinishedProduct1](https://user-images.githubusercontent.com/63136833/151145401-91e673a1-59c4-4a9c-9d57-7faeccca6170.jpg)

We have created an app that connects to a necklace by bluetooth.   
the necklace has a button which, when pressed, sends a signal to an app installed 
on the user's phone. The app will then interpret the signal and send a message to 
up to three contacts saved in the app, for now the text message is hard coded and cannot be 
changed. For test purposes we made a message in swedish which roughly translates to : 
"HELP I am in a dangerous situation. Please call me!"

Here are three pictures showing the user interface of the app:

BILD

We started our project by developing a circuit able to send a message to our app 
by using an Arduino Uno and a separate bluetooth module, the HC-05. Once the connection was
established and we could send messages by pushing the button on the hardware we started 
scaling down the hardware to better fit it into a piece of jewelry. We looked at datasheets for 
the Arduino uno and made a PCB design that incorporates our circuit and the integral parts
of the arduino uno. Here you can see the PCB which uses the ATmega328p microcontroller:

BILD

The PCB measures 3.5 by 2 centimeters and connected to it is the  
the HC-05 along with a battery and a charging module, these components make the final height of the 
hardware about 2 centimeters which is a bit thicker than we would like but still small enough to fit 
in a necklace.

