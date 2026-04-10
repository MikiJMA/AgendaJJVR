package com.example.agendajjvr;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agendajjvr.controlador.ControladorAgenda;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        new ControladorAgenda(this);
    }
}