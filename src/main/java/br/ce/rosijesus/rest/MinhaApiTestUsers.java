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

public class MinhaApiTestUsers {
	@Test
	public void apiInsiominia() {
		
		//Teste Gherkin
		
		given()//Pré Condições
			
		.when()//ação
			.get("https://jsonplaceholder.typicode.com/users")
		.then()//verificações HTTP;posso colocar quantas verificações quiser
			//.assertThat()
		 	.statusCode(200)
		 	.body("id[0]", is(1))//Consulta em forma de array
		 	.body("name", hasItems("Leanne Graham","Ervin Howell","Clementine Bauch"))
			.body(containsString("Leanne Graham"))//Verifica se existe uma infomração em particular
			.body(is(not(nullValue())));//veridica se o body não está vazio
		
		System.out.println("apiInsiominia, Deu certo");
	}
	

	@Test
	public void jsonDeveVerificarPrimeiroNivelInsiominia() {
		given()
		.when()
			.get("https://jsonplaceholder.typicode.com/users/2")
		.then()
		.statusCode(200)
 		.body("username", containsString("Antonette"))
 		.body("email", is("Shanna@melissa.tv"))
		
		;	
		System.out.println("jsonDeveVerificarPrimeiroNivelInsiominia Deu certo, Parabéns!!");
	}
	
	/*@Test
	public void  jsonVerificarPrimeiroNivelOutrasFormasInsiominia() {
		
		//ESTUDAR ESSA PARTE
		
		Response response = RestAssured.request(Method.GET, "https://jsonplaceholder.typicode.com/users");	
				
		//path
		Assert.assertEquals(new Integer(1), response.path("userId"));
		Assert.assertEquals(new Integer(1), response.path("%s","userId"));
		
		//Jsonpath
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
		
		//From
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
		
	}*/
	@Test
	public void vaiVerificarSegundoNivelInsiominia() {
		given()
		.when()
			.get("https://jsonplaceholder.typicode.com/users/3")
		.then()
			.statusCode(200)
			.body("name", containsString("Clementine Bauch"))		
			.body("company.name", is("Romaguera-Jacobson"))
			.body("company.catchPhrase", containsString("Face to face bifurcated interface"))//verifica informação no segundo nivel
		;	
		System.out.println("Deu certo o segundo nivel, Parabéns!!");		
	}
	
	@Test
	public void buscaUsuarioDaApi() {
		
		 get("https://jsonplaceholder.typicode.com/users/3")
		 .then().assertThat().body("company.name", equalTo("Romaguera-Jacobson"));
	}

}
