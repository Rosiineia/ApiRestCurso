package br.ce.rosijesus.rest;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class HTML {
	
	@Test
	
	public void deveFazerBuscasComHTML() {
		 given()
		     .log().all()
		.when()
		    .get("https://restapi.wcaquino.me/v2/users")
		
		.then()
		    .log().all()
		    .statusCode(200)
		    .contentType(ContentType.HTML)
		    .body("html.body.div.table.tbody.tr.size()", is(3))//Verifica o HTML e a quantidade de linhas na tabela
	        .body("html.body.div.table.tbody.tr[1].td[2]", is("25"))//Verifica o conteudo da celula
		    .appendRootPath("html.body.div.table.tbody")
		;		
	}

}
