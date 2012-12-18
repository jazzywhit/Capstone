#include "LedControl.h"

/*
 pin 12 is connected to the DataIn 
 pin 11 is connected to the CLK 
 pin 10 is connected to LOAD 
 We have four MAX7219 devices.
*/
LedControl lc = LedControl(12,11,10,4);
#define HIGHBOUND 13
#define LOWBOUND 0
#define MIDBOUND 6
#define DELAY 100

//Input Push Buttons
int dButton = A4;
int uButton = A2;
int lButton = A3;
int rButton = A5;

//Delay between updates of the display
unsigned long delaytime=10;

//Button Debounce Variables
int buttonState;             // the current reading from the input pin
int lastButtonState = LOW;   // the previous reading from the input pin
long lastDebounceTime = 0;  // the last time the output pin was toggled
long debounceDelay = 50;    // the debounce time; increase if the output flickers

//X & Y Coordinates
int xCoor;
int yCoor;

//Current Matrix
int curMatrix;

void setup() {
  xCoor = 0;
  yCoor = 0;
  curMatrix = 0;
  
  //Assign inputs for buttons
  pinMode(dButton, INPUT);
  pinMode(uButton, INPUT);
  pinMode(lButton, INPUT);
  pinMode(rButton, INPUT);
  
  //Wake Up Max's  
  lc.shutdown(0,false);
  lc.shutdown(1,false);
  lc.shutdown(2,false);
  lc.shutdown(3,false);

  //Set Intensity
  lc.setIntensity(0,1);
  lc.setIntensity(1,1);
  lc.setIntensity(2,1);
  lc.setIntensity(3,1);

  //Clear Diaplays
  lc.clearDisplay(0);
  lc.clearDisplay(1);
  lc.clearDisplay(2);
  lc.clearDisplay(3);
  
  lc.setLed(0,0,0,true);
}

//Ueful snippits 
// lc.setLed(address,row,col,true);

//int CheckButton(int buttonPin);
void DecX();
void DecY();
void IncX();
void IncY();
void ChangeLEDState(int tmpX, int tmpY, boolean state);
void BlinkCursor();
void WriteCursorChange();

void loop() {
  if (digitalRead(dButton) == HIGH && digitalRead(rButton) == HIGH){
      IncX();
      DecY();
      WriteCursorChange();
  } else if (digitalRead(uButton) == HIGH && digitalRead(rButton) == HIGH) { 
      IncX();
      IncY();
      WriteCursorChange();
  } else if (digitalRead(dButton) == HIGH && digitalRead(lButton) == HIGH) { 
      DecX();
      DecY();
      WriteCursorChange();
  } else if (digitalRead(uButton) == HIGH && digitalRead(lButton) == HIGH) { 
      DecX();
      IncY();
      WriteCursorChange();
  } else {  
    if(digitalRead(uButton) == HIGH) {
      IncY();
    }
    if(digitalRead(dButton) == HIGH) {
      DecY();
    }
    if(digitalRead(rButton) == HIGH) {
      IncX();
    }
    if(digitalRead(lButton) == HIGH) {
      DecX();
    }
    WriteCursorChange();
  }
  BlinkCursor(); //Provides all the delays we need.
}

void BlinkCursor() {
    ChangeLEDState(xCoor, yCoor, false);
    delay(200);
    ChangeLEDState(xCoor, yCoor, true);
    delay(200);
}

void ChangeLEDState(int tmpX, int tmpY, boolean state) {
    int matrix = 0;
    
    if (tmpX > MIDBOUND) {
       matrix += 1;
       tmpX -= 7;
    } if (tmpY > MIDBOUND) {
       matrix += 2;
       tmpY -= 7;
    }
    lc.setLed(matrix, tmpX, tmpY, state);
}

void WriteCursorChange() {
    ChangeLEDState(xCoor,yCoor,true);
}

void DecX() {
  if (xCoor > LOWBOUND){
    xCoor--;
  }
}

void DecY() {
  if (yCoor > LOWBOUND){
    yCoor--;
  }
}

void IncX() {
  if (xCoor < HIGHBOUND){
    xCoor++;
  }
}

void IncY() {
  if (yCoor < HIGHBOUND){
    yCoor++;
  }
}
