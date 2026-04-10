package com.example.agendajjvr.controlador;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.agendajjvr.R;
import com.example.agendajjvr.persistencia.Datos;
import com.example.agendajjvr.persistencia.GestorMemoria;
import com.example.agendajjvr.persistencia.TraductorGson;

public class ControladorAgenda {

    // La pantalla que este controlador va a manejar
    private AppCompatActivity actividad;

    // Elementos de la pantalla
    private EditText inputNombre, inputEdad, inputCorreo;
    private Button btnAccionNuevo, btnAccionGuardar, btnAccionEditar, btnAccionBorrar;
    private GridView cuadriculaDatos;

    // Listas de datos
    private ArrayList<Datos> inventarioContactos = new ArrayList<>();
    private ArrayList<String> celdasGrid = new ArrayList<>();
    private ArrayAdapter<String> adaptadorGrid;
    private int indexSeleccionado = -1;

    // El Constructor recibe la pantalla (Activity) cuando arranca la app
    public ControladorAgenda(AppCompatActivity actividad) {
        this.actividad = actividad;
        enlazarVistas();
        configurarClics();
        recuperarDeMemoria();
        pintarCuadricula();
        bloquearBotonesInicio();
    }

    // 1. Busca los botones en la pantalla
    private void enlazarVistas() {
        inputNombre = actividad.findViewById(R.id.txtNombre);
        inputEdad = actividad.findViewById(R.id.txtEdad);
        inputCorreo = actividad.findViewById(R.id.txtCorreo);

        btnAccionNuevo = actividad.findViewById(R.id.btnNuevo);
        btnAccionGuardar = actividad.findViewById(R.id.btnGrabar);
        btnAccionEditar = actividad.findViewById(R.id.btnLectura);
        btnAccionBorrar = actividad.findViewById(R.id.btnEliminar);

        cuadriculaDatos = actividad.findViewById(R.id.lvDatos);

        btnAccionEditar.setText("EDITAR");
        btnAccionGuardar.setText("GUARDAR");
    }

    // 2. Le da vida a todos los botones
    private void configurarClics() {
        cuadriculaDatos.setOnItemClickListener((parent, view, position, id) -> {
            if (position < 3) return;

            indexSeleccionado = (position / 3) - 1;
            Datos seleccionado = inventarioContactos.get(indexSeleccionado);

            inputNombre.setText(seleccionado.getNombreCompleto());
            inputEdad.setText(String.valueOf(seleccionado.getEdadAnios()));
            inputCorreo.setText(seleccionado.getDireccionCorreo());

            btnAccionNuevo.setEnabled(true);
            btnAccionGuardar.setEnabled(false);
            btnAccionEditar.setEnabled(true);
            btnAccionBorrar.setEnabled(true);
        });

        btnAccionNuevo.setOnClickListener(v -> {
            vaciarCampos();
            indexSeleccionado = -1;
            btnAccionNuevo.setEnabled(false);
            btnAccionGuardar.setEnabled(true);
            btnAccionEditar.setEnabled(false);
            btnAccionBorrar.setEnabled(false);
        });

        btnAccionGuardar.setOnClickListener(v -> {
            try {
                Datos nuevoContacto = new Datos(
                        inputNombre.getText().toString(),
                        Integer.parseInt(inputEdad.getText().toString()),
                        inputCorreo.getText().toString()
                );

                if (indexSeleccionado != -1) {
                    inventarioContactos.set(indexSeleccionado, nuevoContacto);
                } else {
                    inventarioContactos.add(nuevoContacto);
                }

                guardarEnMemoria();
                pintarCuadricula();
                vaciarCampos();
                bloquearBotonesInicio();
            } catch (Exception e) {
                Toast.makeText(actividad, "Verifica que la edad sea un número", Toast.LENGTH_SHORT).show();
            }
        });

        btnAccionEditar.setOnClickListener(v -> {
            btnAccionGuardar.setEnabled(true);
            btnAccionEditar.setEnabled(false);
        });

        btnAccionBorrar.setOnClickListener(v -> {
            if (indexSeleccionado != -1) {
                inventarioContactos.remove(indexSeleccionado);
                guardarEnMemoria();
                pintarCuadricula();
                vaciarCampos();
                bloquearBotonesInicio();
                Toast.makeText(actividad, "Registro eliminado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 3. Funciones de lógica
    private void pintarCuadricula() {
        celdasGrid.clear();
        celdasGrid.add("NOMBRE");
        celdasGrid.add("EDAD");
        celdasGrid.add("EMAIL");

        for (Datos contacto : inventarioContactos) {
            celdasGrid.add(contacto.getNombreCompleto());
            celdasGrid.add(String.valueOf(contacto.getEdadAnios()));
            celdasGrid.add(contacto.getDireccionCorreo());
        }

        adaptadorGrid = new ArrayAdapter<>(actividad, android.R.layout.simple_list_item_1, celdasGrid);
        cuadriculaDatos.setAdapter(adaptadorGrid);
    }

    private void recuperarDeMemoria() {
        GestorMemoria gestor = new GestorMemoria();
        TraductorGson traductor = new TraductorGson();
        String jsonGigante = gestor.leerTodoElDocumento(actividad);
        if (!jsonGigante.isEmpty()) {
            inventarioContactos = traductor.desempaquetarAgenda(jsonGigante);
        } else {
            inventarioContactos.clear();
        }
    }

    private void guardarEnMemoria() {
        TraductorGson traductor = new TraductorGson();
        GestorMemoria gestor = new GestorMemoria();
        String jsonGigante = traductor.empaquetarAgendaCompleta(inventarioContactos);
        gestor.guardarDocumento(actividad, jsonGigante);
    }

    private void vaciarCampos() {
        inputNombre.setText("");
        inputEdad.setText("");
        inputCorreo.setText("");
    }

    private void bloquearBotonesInicio() {
        btnAccionNuevo.setEnabled(true);
        btnAccionGuardar.setEnabled(false);
        btnAccionEditar.setEnabled(false);
        btnAccionBorrar.setEnabled(false);
    }
}