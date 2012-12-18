#include "LedControl.h"

/*
 pin 12 is connected to the DataIn 
 pin 11 is connected to the CLK 
 pin 10 is connected to LOAD 
 We have four MAX7219 devices.
*/
LedControl lc=LedControl(12,11,10,4);


//Input Push Buttons
int dButton = 5;
int uButton = 6;
int lButton = 7;
int rButton = 8;

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

void setup() 
{
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
}

//Ueful snippits 
// lc.setLed(address,row,col,true);

int CheckButton(int buttonPin);
void DecX();
void DecY();
void IncX();
void IncY();

void loop() 
{
  if(CheckButton(dButton))
  {
    DecY();
    lc.setLed(curMatrix,xCoor,yCoor,true);
  }
  if(CheckButton(uButton))
  {
    IncY();
    lc.setLed(curMatrix,xCoor,yCoor,true);
  }
  if(CheckButton(lButton))
  {
    DecX();
    lc.setLed(curMatrix,xCoor,yCoor,true);
  }
  if(CheckButton(rButton))
  {
    IncX();
    lc.setLed(curMatrix,xCoor,yCoor,true);
  }
}

int CheckButton(int buttonPin)
{
  int reading = digitalRead(buttonPin);

  if (reading != lastButtonState) 
  {
    lastDebounceTime = millis();
  } 
  
  if ((millis() - lastDebounceTime) > debounceDelay) 
  {
    buttonState = reading;
  }

  return buttonState;
}

void DecX()
{
  if(xCoor == 0 && (curMatrix == 4 ||curMatrix == 2))
  {
    curMatrix--;
    xCoor = 8;
  }
  else if(xCoor == 0 && (curMatrix == 1 || curMatrix == 3))
  {}
  else
  {
     xCoor--; 
  }
}

void DecY()
{
  if(yCoor == 0 && (curMatrix == 3 ||curMatrix == 4))
  {
    curMatrix--;
    yCoor = 8;
  }
  else if(yCoor == 0 && (curMatrix == 1 || curMatrix == 2))
  {}
  else
  {
     yCoor--; 
  }  
}

void IncX()
{
  if(xCoor == 8 && (curMatrix == 1 || curMatrix == 3))
  {
    curMatrix++;
    xCoor = 0;
  }
  else if(xCoor == 8 && (curMatrix == 2 || curMatrix == 4))
  {}
  else
  {
     xCoor++; 
  }
}

void IncY()
{
  if(yCoor == 8 && (curMatrix == 1 || curMatrix == 2))
  {
    curMatrix++;
    yCoor = 0;
  }
  else if(yCoor == 8 && (curMatrix == 3 || curMatrix == 4))
  {}
  else
  {
     yCoor++; 
  }
}
