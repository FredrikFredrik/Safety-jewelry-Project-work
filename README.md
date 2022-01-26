# Safety-jewelry-Project-work

The creators of this project are: Joanna Kubik, Fredrik Jonsson and Sara Weman. During fall 2021 we did this project work as a part of our degree programme at KTH Royal Institute of Technology where we chose the elective course, Electronic Design - Project course.

<img width="370" alt="image" src="https://user-images.githubusercontent.com/63136833/151148638-f640c898-6e3d-403b-8d06-481aab6b4e32.png">

We have created an app that connects to a necklace by bluetooth.   
The necklace has a button which, when pressed, sends a signal to an app installed 
on the user's phone. The app will then interpret the signal and send a message to 
up to three contacts saved in the app, for now the text message is hard coded and cannot be 
changed. For test purposes we made a message in swedish which roughly translates to : 
"HELP I am in a dangerous situation. Please call me!"

Here are three pictures showing the user interface of the app:

<img width="361" alt="image" src="https://user-images.githubusercontent.com/63136833/151148399-cddef943-b863-4ecd-ab48-84be8f14870d.png">

We started our project by developing a circuit able to send a message to our app 
by using an Arduino Uno and a separate bluetooth module, the HC-05. Once the connection was
established and we could send messages by pushing the button on the hardware we started 
scaling down the hardware to better fit it into a piece of jewelry. We looked at datasheets for 
the Arduino uno and made a PCB design that incorporates our circuit and the integral parts
of the Arduino uno. Here you can see the PCB which uses the ATmega328p microcontroller:

<img width="341" alt="image" src="https://user-images.githubusercontent.com/63136833/151148238-6af7de17-aebb-43ab-993d-e8a7f652dd2c.png">

The PCB measures 3.5 by 2 centimeters and connected to it is the HC-05 along with a battery and a charging module. 
These components make the final height of the hardware about 2 centimeters which is a bit thicker than we would like,
but still small enough to fit in a necklace.

