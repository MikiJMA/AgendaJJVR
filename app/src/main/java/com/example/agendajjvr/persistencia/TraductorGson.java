package com.example.agendajjvr.persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TraductorGson {

    
    public String empaquetarAgendaCompleta(ArrayList<Datos> listaContactos) {
        return new Gson().toJson(listaContactos);
    }

    
    public ArrayList<Datos> desempaquetarAgenda(String jsonGeneral) {
        if (jsonGeneral == null || jsonGeneral.isEmpty()) {
            return new ArrayList<>(); // Si está vacío, regresa una lista en blanco
        }
         
        Type tipoLista = new TypeToken<ArrayList<Datos>>(){}.getType();
        return new Gson().fromJson(jsonGeneral, tipoLista);
    }
}