#include "LedControl.h"

/*
 pin 12 is connected to the DataIn 
 pin 11 is connected to the CLK 
 pin 10 is connected to LOAD 
 We have four MAX7219 devices.
 */
LedControl lc=LedControl(12,11,10,4);

/*Wait between updates of the display */
unsigned long delaytime=10;

void setup() 
{
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

void loop() 
{
  
}
