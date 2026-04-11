/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upeu.sysventas;
import java.io.ByteArrayOutputStream;
import javax.print.*;
/**
 *
 * @author AngelRC2
 */
public class ImpresoraPOS {
    
    public static void imprimirVoucher(String texto) {
        try {
            PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService impresoraPOS = null;

            for (PrintService impresora : impresoras) {
                if (impresora.getName().contains("POS-80")) {
                    impresoraPOS = impresora;
                    break;
                }
            }

            if (impresoraPOS == null) {
                System.out.println("No se encontró la impresora POS.");
                return;
            }

            // Añade avance de líneas y comando de corte
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(texto.getBytes("UTF-8"));
            outputStream.write("\n\n\n".getBytes());
            byte[] cortar = new byte[]{0x1D, 0x56, 0x01}; // GS V 1
            outputStream.write(cortar);

            byte[] datos = outputStream.toByteArray();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc documento = new SimpleDoc(datos, flavor, null);
            DocPrintJob job = impresoraPOS.createPrintJob();
            job.print(documento, null);

            System.out.println("Voucher impreso correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
