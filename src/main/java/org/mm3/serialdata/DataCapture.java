package org.mm3.serialdata;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Created with IntelliJ IDEA.
 * User: NBT86YZ
 * Date: 7/6/15
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataCapture {

    public static void main(String args[]){

        SerialPort serialPort = new SerialPort("COM1");
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
            serialPort.writeBytes("This is a test string".getBytes());//Write data to port
            serialPort.closePort();//Close serial port
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }



    }
}
