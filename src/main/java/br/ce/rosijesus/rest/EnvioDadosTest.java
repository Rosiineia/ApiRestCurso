package br.ce.rosijesus.rest;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class EnvioDadosTest {
	
	@Test
	
	public void deveEnviarValorViaQuery() {
		
		//**Tras a resposta no formato json ou XML
		
		given()
		    .log().all()
		
		.when()
		    .get("https://restapi.wcaquino.me/v2/users?format=json")
		.then()
		    .log().all()
		    .statusCode(200)
		    .contentType(ContentType.JSON)
		;
		
		
	}
	
@Test
	
	public void deveEnviarValorViaQueryParametro() {
		
		//**Tras a resposta no formato  XML
		
		given()
		    .log().all()
		    .queryParam("format", "xml")
		    .queryParam("outra", "coisa")//posso passar varios parametrose não vai interferir
		
		.when()
		    .get("https://restapi.wcaquino.me/v2/users")
		    
		.then()
		    .log().all()
		    .statusCode(200)
		    .contentType(ContentType.XML)
		    .contentType(containsString("utf-8"))		    
		;	
	}

@Test

public void deveEnviarValorViaHeader() {
	
	//**Tras a resposta no formato json ou XML
	
	given()
	    .log().all()
	    .accept(ContentType.XML)
	
	.when()
	    .get("https://restapi.wcaquino.me/v2/users")
	.then()
	    .log().all()
	    .statusCode(200)
	    .contentType(ContentType.XML)
	;
	
	
}

}
