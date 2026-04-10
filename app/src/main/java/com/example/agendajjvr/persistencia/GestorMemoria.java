package com.example.agendajjvr.persistencia;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GestorMemoria {
   
    private final String NOMBRE_DOCUMENTO = "MiAgendaSegura.json";

    public String leerTodoElDocumento(Context contexto) {
        try {
            InputStreamReader isr = new InputStreamReader(contexto.openFileInput(NOMBRE_DOCUMENTO));
            BufferedReader lector = new BufferedReader(isr);
            StringBuilder constructorTexto = new StringBuilder();
            String renglon;

            
            while ((renglon = lector.readLine()) != null) {
                constructorTexto.append(renglon);
            }
            lector.close();
            return constructorTexto.toString();
        } catch (Exception e) {
            return ""; 
        }
    }

    public boolean guardarDocumento(Context contexto, String contenidoJson) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(contexto.openFileOutput(NOMBRE_DOCUMENTO, Context.MODE_PRIVATE));
            osw.write(contenidoJson); 
            osw.flush();
            osw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}