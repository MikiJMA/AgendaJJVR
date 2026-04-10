package com.example.agendajjvr.persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TraductorGson {

    // Agarra TODA la lista de contactos y la hace un solo texto JSON
    public String empaquetarAgendaCompleta(ArrayList<Datos> listaContactos) {
        return new Gson().toJson(listaContactos);
    }

    // Lee el súper bloque JSON y te devuelve la lista ya armada
    public ArrayList<Datos> desempaquetarAgenda(String jsonGeneral) {
        if (jsonGeneral == null || jsonGeneral.isEmpty()) {
            return new ArrayList<>(); // Si está vacío, regresa una lista en blanco
        }
        // Esta línea es magia de GSON para entender que es una Lista de objetos
        Type tipoLista = new TypeToken<ArrayList<Datos>>(){}.getType();
        return new Gson().fromJson(jsonGeneral, tipoLista);
    }
}