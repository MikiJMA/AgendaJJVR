package com.example.agendajjvr;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Archivo {

    // Archivo con tus iniciales como lo pidió el profesor
    private final String nombreArchivo = "datos.jjvr";

    public ArrayList<String> leerDatos(Context context) {
        ArrayList<String> datos = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(context.openFileInput(nombreArchivo));
            BufferedReader br = new BufferedReader(isr);
            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            // El archivo no existe aún, devuelve lista vacía
        }
        return datos;
    }

    public void grabarDatos(Context context, ArrayList<String> datos) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(nombreArchivo, Context.MODE_PRIVATE));
            for (String dato : datos) {
                osw.write(dato + "\n");
            }
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarArchivo(Context context) {
        context.deleteFile(nombreArchivo);
    }
}