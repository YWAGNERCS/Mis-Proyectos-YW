package com.upeu.sysventas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ConsultaDNI {

    public static String obtenerNombrePorDNI(String dni) {
        String resultado = "";
        try {
            // URL de la API con el número de DNI
            String urlConsulta = "https://api.apis.net.pe/v1/dni?numero=" + dni;
            URL url = new URL(urlConsulta);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar si la respuesta es OK
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error en la conexión: " + conn.getResponseCode());
            }

            // Leer respuesta
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder jsonOutput = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                jsonOutput.append(linea);
            }

            conn.disconnect();

            // Parsear JSON
            JSONObject jsonObject = new JSONObject(jsonOutput.toString());
            resultado = jsonObject.getString("nombre");

        } catch (Exception e) {
            e.printStackTrace();
            resultado = "Error al consultar DNI";
        }
        return resultado;
    }

}
