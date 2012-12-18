#include "LedControl.h"

/*
 pin 12 is connected to the DataIn 
 pin 11 is connected to the CLK 
 pin 10 is connected to LOAD 
 We have four MAX7219 devices.
 */
LedControl lc = LedControl(12,11,10,4);

/*Wait between updates of the display */
unsigned long delaytime=10;

void setup() {

  //Wake Up Max's  
  lc.shutdown(0,false);
  lc.shutdown(1,false);
  lc.shutdown(2,false);
  lc.shutdown(3,false);

  //Set Intensity
  lc.setIntensity(0,8);
  lc.setIntensity(1,8);
  lc.setIntensity(2,8);
  lc.setIntensity(3,8);

  //Clear Diaplays
  lc.clearDisplay(0);
  lc.clearDisplay(1);
  lc.clearDisplay(2);
  lc.clearDisplay(3);
}

/*
  This function lights up a some Leds in a row.
 The pattern will be repeated on every row.
 The pattern will blink along with the row-number.
 row number 4 (index==3) will blink 4 times etc.
 */
void rows(int address) {
  for(int row=0;row<8;row++) {
    delay(delaytime);
    lc.setRow(address,row,B10100000);
    delay(delaytime);
    lc.setRow(address,row,(byte)0);
    for(int i=0;i<row;i++) {
      delay(delaytime);
      lc.setRow(address,row,B10100000);
      delay(delaytime);
      lc.setRow(address,row,(byte)0);
    }
  }
}

/*
  This function lights up a some Leds in a column.
 The pattern will be repeated on every column.
 The pattern will blink along with the column-number.
 column number 4 (index==3) will blink 4 times etc.
 */
void columns(int address) {
  for(int col=0;col<8;col++) {
    delay(delaytime);
    lc.setColumn(address,col,B10100000);
    delay(delaytime);
    lc.setColumn(0,col,(byte)0);
    for(int i=0;i<col;i++) {
      delay(delaytime);
      lc.setColumn(address,col,B10100000);
      delay(delaytime);
      lc.setColumn(address,col,(byte)0);
    }
  }
}

/* 
 This function will light up every Led on the matrix.
 The led will blink along with the row-number.
 row number 4 (index==3) will blink 4 times etc.
 */
void single(int address) {
  for(int row=0;row<8;row++) {
    for(int col=0;col<8;col++) {
      delay(delaytime);
      lc.setLed(address,row,col,true);
      delay(delaytime);
      for(int i=0;i<col;i++) {
        lc.setLed(address,row,col,false);
        delay(delaytime);
        lc.setLed(address,row,col,true);
        delay(delaytime);
      }
    }
  }
}

void loop() { 
  rows(0);
  columns(0);
  single(0);
  rows(1);
  columns(1);
  single(1);
  rows(2);
  columns(2);
  single(2);
  rows(3);
  columns(3);
  single(3);
}
