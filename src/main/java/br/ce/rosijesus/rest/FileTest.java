package br.ce.rosijesus.rest;

import org.junit.Test;

import io.restassured.RestAssured;

import static org.hamcrest.Matchers.*;

import java.io.File;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class FileTest {
	
	@Test
	public void deveObrigarEnvioArquivo() {
		
		given()
		    .log().all()
		.when()
		    .post("https://restapi.wcaquino.me/upload")
		
		.then()
		   .log().all()
		   .statusCode(404)
		   .body("error", is("Arquivo não enviado"))//deveria ser 400, mas o criador da api colocou 404
		
		;
	}
	
	@Test
	public void deveFazerUploadDoArquivo() {
		
		given()
		    .log().all()
		    .multiPart("arquivo", new File("src/main/resources/Arq.testeAPI.pdf"))
		.when()
		    .post("https://restapi.wcaquino.me/upload")
		
		.then()
		   .log().all()
		   .statusCode(200)
		   .body("name", is("Arq.testeAPI.pdf"))
		   
		
		;
	}
	

}
