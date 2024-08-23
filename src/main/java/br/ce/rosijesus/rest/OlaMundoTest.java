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
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
		
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");//"https://restapi.wcaquino.me/ola"
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue("O status code deveria ser 200",response.statusCode() == 200);
		Assert.assertEquals(200, response.statusCode());
		System.out.println(response.statusCode() == 200);
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		
		Response response =request(Method.GET, "https://restapi.wcaquino.me/ola");//"https://restapi.wcaquino.me/ola"		
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		//metodo importando o io.restassured.RestAssured.*;
		//get("https://restapi.wcaquino.me/ola").then().statusCode(200);
		
		//Teste Gherkin
		
		given()//Pré Condições
			
		.when()//ação
			.get("https://restapi.wcaquino.me/ola")
		.then()//verificações HTTP
			//.assertThat()
		 	.statusCode(200);		
		
	}
	
	@Test
	public void devoConhecerMatcherHamcrest() {
		//trabalhar com igualdades
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat( 130, Matchers.is( 130));
		Assert.assertThat( 130, Matchers.isA(Integer.class));//verificar tipo
		Assert.assertThat( 130d, Matchers.isA(Double.class));//verificar tipo
		Assert.assertThat( 130d, Matchers.greaterThan(120d));//verificar se é maior 
		Assert.assertThat( 130d, Matchers.lessThan(140d));//verificar se é menor
		
		//Listas
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares, hasSize(5)); //verifica tamanho da lista
		assertThat(impares, contains(1,3,5,7,9));//verifica se os itens estão na lista 
		assertThat(impares, containsInAnyOrder(1,5,3,9,7));//verificar independende da ordem
		assertThat(impares, hasItem(1));//verificar apenas um elemento
		assertThat(impares, hasItems(1,3,7,9));//verificar mais de  um elemento
		
		//como conectar varias assertivas
		assertThat("Maria", not("Ana"));
		assertThat("Maria", is("Maria"));
		assertThat("Maria", anyOf(is("Maria"), is("Ana")));// pode ser uma ou outra, varias verificações dentro do mesmo valor
		//assertThat("Paula", anyOf(is("Maria"), not("Paula")));
		assertThat("Angelica", allOf(startsWith("Ang"), endsWith("ca"), containsString("eli")));//verifica se começa(and) termina(end) e contem(containsString)
		
		
		
		
	}
	
	@Test
	public void devoValidarBody() {
		
		//Teste Gherkin
		
				given()//Pré Condições
					
				.when()//ação
					.get("https://restapi.wcaquino.me/ola")
				.then()//verificações HTTP;posso colocar quantas verificações quiser
					//.assertThat()
				 	.statusCode(200)
				 	.body(is("Ola Mundo!"))//pega o body todo
					.body(containsString("Mundo"))//Verifica se existe uma infomração em particular
					.body(is(not(nullValue())));//veridica se o body não está vazio
				System.out.println("Deu certo, Parabéns!!");
	}

}
