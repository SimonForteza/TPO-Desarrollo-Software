package com.example.pds.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GeocodingUtil {

    /**
     * Obtiene latitud y longitud a partir de calle, número y ciudad usando Nominatim (OpenStreetMap).
     * @param calle Nombre de la calle
     * @param numero Número de la calle
     * @param ciudad Ciudad
     * @return Un array [latitud, longitud] o null si no se encuentra
     */
    public static double[] getLatLongFromAddress(String calle, String numero, String ciudad) {
        try {
            String direccion = calle + " " + numero + ", " + ciudad;
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + URLEncoder.encode(direccion, "UTF-8");

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0"); // Nominatim requiere un User-Agent

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray results = new JSONArray(response.toString());
            if (results.length() > 0) {
                JSONObject location = results.getJSONObject(0);
                double lat = location.getDouble("lat");
                double lon = location.getDouble("lon");
                return new double[]{lat, lon};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // No se encontró o error
    }
} 