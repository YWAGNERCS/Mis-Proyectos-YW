/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upeu.sysventas;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
/**
 *
 * @author AngelRC2
 */
public class Funciones {
    
    public static String mesLetras(String mes){        
        String m = null;
        switch(mes){
            case "1":m="Enero";break;
            case "2":m="Febrero";break;
            case "3":m="Marzo";break;   
            case "4":m="Abril";break;    
            case "5":m="Mayo";break;
            case "6":m="Junio";break;    
            case "7":m="Julio";break;    
            case "8":m="Agosto";break;    
            case "9":m="Septiembre";break;
            case "10":m="Octubre";break;    
            case "11":m="Noviembre";break;    
            case "12":m="Diciembre";break;    
            default: m="error";break;
        }
        return m;
    }
    
    public static String diaLetras(String dia){        
        String d = null;
        switch(dia){
            case "1":d="Domingo";break;
            case "2":d="Lunes";break;
            case "3":d="Martes";break;   
            case "4":d="Miercoles";break;    
            case "5":d="Jueves";break;
            case "6":d="Viernes";break;    
            case "7":d="Sabado";break;               
        }
        return d;
    }
    
    
}
