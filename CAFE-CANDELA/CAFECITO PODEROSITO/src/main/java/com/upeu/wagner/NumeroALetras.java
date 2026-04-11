/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upeu.sysventas;

/**
 *
 * @author AngelRC2
 */
public class NumeroALetras {

    private final static String[] UNIDADES = {
        "", "UNO", "DOS", "TRES", "CUATRO", "CINCO",
        "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ",
        "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE",
        "DIECISÉIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE"
    };

    private final static String[] DECENAS = {
        "VEINTI", "TREINTA", "CUARENTA", "CINCUENTA",
        "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
    };

    private final static String[] CENTENAS = {
        "", "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS",
        "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"
    };

    public static String convertir(double numero) {
        long parteEntera = (long) numero;
        int parteDecimal = (int) Math.round((numero - parteEntera) * 100);
        return convertirNumero(parteEntera) + " CON " + String.format("%02d", parteDecimal) + "/100 SOLES";
    }

    private static String convertirNumero(long numero) {
        if (numero == 0) return "CERO";
        if (numero == 1) return "UNO";
        String resultado = "";

        if (numero >= 1_000_000) {
            long millones = numero / 1_000_000;
            resultado += convertirNumero(millones) + (millones == 1 ? " MILLÓN " : " MILLONES ");
            numero %= 1_000_000;
        }
        if (numero >= 1000) {
            long miles = numero / 1000;
            if (miles == 1) {
                resultado += "MIL ";
            } else {
                resultado += convertirNumero(miles) + " MIL ";
            }
            numero %= 1000;
        }
        if (numero > 0) {
            if (numero <= 20) {
                resultado += UNIDADES[(int) numero];
            } else if (numero < 100) {
                int decena = (int) (numero / 10);
                int unidad = (int) (numero % 10);
                resultado += DECENAS[decena - 2];
                if (unidad > 0) {
                    if (decena == 2) {
                        resultado += UNIDADES[unidad].toLowerCase();
                    } else {
                        resultado += " Y " + UNIDADES[unidad];
                    }
                }
            } else {
                int centena = (int) (numero / 100);
                int resto = (int) (numero % 100);
                if (numero == 100) {
                    resultado += "CIEN";
                } else {
                    resultado += CENTENAS[centena] + " " + convertirNumero(resto);
                }
            }
        }

        return resultado.trim();
    }

    // Ejemplo de uso:
    public static void main(String[] args) {
        double totalVenta = 3548.75;
        String totalEnLetras = NumeroALetras.convertir(totalVenta);
        System.out.println("Total en letras: " + totalEnLetras);
    }
}