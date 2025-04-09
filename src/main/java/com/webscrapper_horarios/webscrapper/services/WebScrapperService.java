package com.webscrapper_horarios.webscrapper.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webscrapper_horarios.webscrapper.entidades.ComisionMateria;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WebScrapperService {


    // Esta funcion te devuelve en un String de json el horario
    // cada fila de la tabla es un objeto
    public String scrapeWebsite(String url, Integer numEspecialidad){

        List<ComisionMateria> comisiones = new ArrayList<>();
        boolean primera = true;

        try {


            // Los numeros para elegir especialidad
            //  5 - Sistemas
            //  8 - Electromecanica
            //  9 - Electronica
            // 15 - Telecomunicaciones
            // 27 - Quimica
            // 31 - Civil

            Document doc = Jsoup.connect( url )
                    .data("especialidad",   numEspecialidad.toString() )
                    .method(Connection.Method.POST)
                    .post();

            Element tbody = doc.getElementsByTag("tbody").first();


           if (tbody != null) {

               String lastAnioStr = "";
               String lastMateria = "";
               String lastCurso = "";

               for (Element fila : tbody.select("tr")) {

                   // skipeamos la primera fila
                   if (primera) {
                       primera = false;
                       continue;
                   }

                   Elements celdas = fila.select("td");

                   // si es del plan 2008 no se parsea la fila
                   if (celdas.get(4).text().trim().equals("2008")) {
                       continue;
                   }

                   // Año
                   String anioStr = celdas.get(0).text().trim();
                   if (!anioStr.isEmpty()) {
                       lastAnioStr = anioStr;
                   }
                   int anio = Integer.parseInt(lastAnioStr); // usamos el último conocido

                   // Materia
                   String materiaStr = celdas.get(2).text().trim();
                   if (!materiaStr.isEmpty()) {
                       lastMateria = materiaStr;
                   }
                   String materia = lastMateria;

                   // Curso
                   String cursoStr = celdas.get(3).text().trim();
                   if (!cursoStr.isEmpty()) {
                       lastCurso = cursoStr;
                   }
                   String curso = lastCurso;

                   // Horarios
                   String diaCursado = celdas.get(5).text().trim();
                   String horaInicio = celdas.get(6).text().trim();
                   String horaFin = celdas.get(7).text().trim();

                   ComisionMateria comision = new ComisionMateria(anio, materia, curso, diaCursado, horaInicio, horaFin);
                   comisiones.add(comision);
               }

           }

            //Conversion a json a travez de gson
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(comisiones);

            // Descomentar para generar un archivo .json con el contenido
            Files.writeString(Paths.get("comisiones.json"), json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return json;

        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }





    }

}
