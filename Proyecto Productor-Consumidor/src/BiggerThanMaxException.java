/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alejandro
 */
import java.io.*;

public class BiggerThanMaxException extends Exception{
    private int value;
    
    public BiggerThanMaxException(int value){
        this.value = value;
    }
    
    public int getValue(){
        return value;
    }
    
}
