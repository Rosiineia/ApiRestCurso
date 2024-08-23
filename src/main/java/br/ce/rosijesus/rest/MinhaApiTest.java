package br.ce.rosijesus.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;//colocar o * para charmar todos
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class MinhaApiTest {

	@Test
	public void apiRestPostman() {
		
		//Teste Gherkin
		
		given()//Pré Condições
			
		.when()//ação
			.get("https://jsonplaceholder.typicode.com/todos")
		.then()//verificações HTTP;posso colocar quantas verificações quiser
			//.assertThat()
		 	.statusCode(200)
		 	//.body(is("Mouse"))//pega o body todo
			//.body(containsString("Logitech MX Vertical"))//Verifica se existe uma infomração em particular
			.body(is(not(nullValue())));//veridica se o body não está vazio
		System.out.println("Deu certo");
	}
	

	@Test
	public void jsonDeveVerificarPrimeiroNivel() {
		given()
		.when()
			.get("https://jsonplaceholder.typicode.com/todos/1")
		.then()
		.statusCode(200)
 		.body("title", containsString("delectus aut autem"))
 		.body("userId", greaterThan(0))//verifica se é maior que (greaterThan)
		
		;	
		System.out.println("Deu certo, Parabéns!!");
	}
	
	@Test
	public void  jsonVerificarPrimeiroNivelOutrasFormas() {
		
		//ESTUDAR ESSA PARTE
		
		Response response = RestAssured.request(Method.GET, "https://jsonplaceholder.typicode.com/todos");	
				
		//path
		Assert.assertEquals(new Integer(1), response.path("userId"));
		Assert.assertEquals(new Integer(1), response.path("%s","userId"));
		
		//Jsonpath
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
		
		//From
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
		
	}
	@Test
	public void vaiVerificarSegundoNivel() {
		given()
		.when()
			.get("https://jsonplaceholder.typicode.com/todos/2")
		.then()
		.statusCode(200)
		.body("title", containsString("quis ut nam facilis et officia qui"))
		.body("completed", is(false))
		//.body("endereco.rua", is("Rua dos bobos"))//verifica informação no segundo nivel
		;	
		System.out.println("Deu certo o segundo nivel, Parabéns!!");		
	}
}
