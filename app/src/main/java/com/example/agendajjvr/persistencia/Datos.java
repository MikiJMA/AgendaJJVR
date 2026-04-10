package com.example.agendajjvr.persistencia;

public class Datos {
    private String nombreCompleto;
    private int edadAnios;
    private String direccionCorreo;

    // Constructor vacío
    public Datos() {}

    // Constructor con datos
    public Datos(String nombreCompleto, int edadAnios, String direccionCorreo) {
        this.nombreCompleto = nombreCompleto;
        this.edadAnios = edadAnios;
        this.direccionCorreo = direccionCorreo;
    }

    // Getters y Setters
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public int getEdadAnios() { return edadAnios; }
    public void setEdadAnios(int edadAnios) { this.edadAnios = edadAnios; }

    public String getDireccionCorreo() { return direccionCorreo; }
    public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
}