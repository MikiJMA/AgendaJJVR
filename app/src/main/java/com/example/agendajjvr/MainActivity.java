package com.example.agendajjvr;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre, txtEdad, txtCorreo;
    Button btnNuevo, btnGrabar, btnLectura, btnEliminar;
    ListView lvDatos;

    ArrayList<String> listaContactos;
    ArrayAdapter<String> adaptador;
    Archivo archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar vistas
        txtNombre = findViewById(R.id.txtNombre);
        txtEdad = findViewById(R.id.txtEdad);
        txtCorreo = findViewById(R.id.txtCorreo);

        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnLectura = findViewById(R.id.btnLectura);
        btnEliminar = findViewById(R.id.btnEliminar);

        lvDatos = findViewById(R.id.lvDatos);

        archivo = new Archivo();
        listaContactos = new ArrayList<>();

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContactos);
        lvDatos.setAdapter(adaptador);

        btnNuevo.setOnClickListener(v -> {
            txtNombre.setText("");
            txtEdad.setText("");
            txtCorreo.setText("");
            Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show();
            txtNombre.requestFocus();
        });

        btnGrabar.setOnClickListener(v -> {
            String nombre = txtNombre.getText().toString().trim();
            String edad = txtEdad.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();

            if (!nombre.isEmpty() && !edad.isEmpty() && !correo.isEmpty()) {
                String registro = nombre + " " + edad + " " + correo;

                listaContactos = archivo.leerDatos(this);
                listaContactos.add(registro);

                archivo.grabarDatos(this, listaContactos);
                actualizarPantalla();

                txtNombre.setText("");
                txtEdad.setText("");
                txtCorreo.setText("");

                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Llena todos los campos primero", Toast.LENGTH_SHORT).show();
            }
        });

        btnLectura.setOnClickListener(v -> {
            actualizarPantalla();
            Toast.makeText(this, "Archivo leído", Toast.LENGTH_SHORT).show();
        });

        btnEliminar.setOnClickListener(v -> {
            archivo.eliminarArchivo(this);
            listaContactos.clear();
            adaptador.notifyDataSetChanged();
            Toast.makeText(this, "Registros eliminados", Toast.LENGTH_SHORT).show();
        });
    }

    private void actualizarPantalla() {
        listaContactos.clear();
        listaContactos.addAll(archivo.leerDatos(this));
        adaptador.notifyDataSetChanged();
    }
}