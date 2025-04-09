package com.webscrapper_horarios.webscrapper;

import com.webscrapper_horarios.webscrapper.services.WebScrapperService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class WebscrapperApplication {

	public static void main(String[] args) {
//		SpringApplication.run(WebscrapperApplication.class, args);


		WebScrapperService webScrapperService = new WebScrapperService();

		// Los numeros para elegir especialidad
		//  5 - Sistemas
		//  8 - Electromecanica
		//  9 - Electronica
		// 15 - Telecomunicaciones
		// 27 - Quimica
		// 31 - Civil
		webScrapperService.scrapeWebsite("http://encuesta.frm.utn.edu.ar/horariocurso/", 5);
	}



}
