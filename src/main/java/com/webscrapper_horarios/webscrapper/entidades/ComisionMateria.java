package com.webscrapper_horarios.webscrapper.entidades;

public class ComisionMateria {
    private int anio;
    private String materia;
    private String curso;
    private String diaCursado;
    private String horaInicio;
    private String horaFin;

    // Constructor
    public ComisionMateria(int anio, String materia, String curso,
                           String diaCursado, String horaInicio, String horaFin) {
        this.anio = anio;
        this.materia = materia;
        this.curso = curso;
        this.diaCursado = diaCursado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

}
