package br.ce.rosijesus.rest;

import static io.restassured.RestAssured.*;//colocar o * para charmar todos

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.*;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.junit.Assert;
import org.junit.BeforeClass;

public class UserJasonTest {
	
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	
	// ***IMPORTANTE Antes de executar qlq teste essa calsse será executada 
	
	@BeforeClass  
	public static void setup() {
		RestAssured.baseURI = "https://restapi.wcaquino.me";
		RestAssured.port = 443; //de acordo com o http portas 443 ou 80
		RestAssured.basePath = "";//exemplo "/v2"
		
		//RestAssured.authentication
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		reqSpec = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectStatusCode(200 );
		resSpec = resBuilder.build();
	}
	
	
	@Test
	public void deveVerificarPrimeiroNivel() {
		
		
		
		given()
			.spec(reqSpec)
			//.log().all()// mostrar log
		.when()
			.get("/users/1")			
		.then()
		.spec(resSpec)
		//.statusCode(200)
		.body("id", is(1))//passo a informação e com o "is" eu verifico
		.body("name", containsString("Silva"))
		.body("age", greaterThan(18))//verifica se é maior que (greaterThan)
		;	
		System.out.println("Deu certo, Parabéns!!");
	}
	
	@Test
	public void  deveVerificarPrimeiroNivelOutrasFormas() {
		
		Response response = RestAssured.request(Method.GET, "/users/1");	
				
		//path
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s","id"));
		
		//Jsonpath
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
		
		//From
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
		
	}
	
	@Test
	public void deveVerificarSegundoNivel() {
		given()
		.when()
			.get("/users/2")
		.then()
		.statusCode(200)
		.body("name", containsString("Joaquina"))
		.body("endereco.rua", is("Rua dos bobos"))//verifica sinformação no segundo nivel
		;	
		System.out.println("Deu certo o segundo nivel, Parabéns!!");		
	}
	
	@Test
	public void deveVerificarLista() {
		given()
		.when()
			.get("/users/3")
		.then()
		.statusCode(200)
		.body("name", containsString("Ana"))
		//tamanho da lista
		.body("filhos", hasSize(2))
		//Consulta em forma de array
		.body("filhos[0].name", is("Zezinho"))
		.body("filhos[1].name", is("Luizinho"))
		//Busca um ou mais itens
		.body("filhos.name", hasItem("Zezinho"))
		.body("filhos.name", hasItems("Zezinho","Luizinho"))
		;	
		System.out.println("Deu certo a Lista, Parabéns!!");		
	}
	
	@Test
	public void deveRetronarErroUsuarioInexistente() {
		given()
		.when()
			.get("/users/4")
		.then()
		.statusCode(404)
		.body("error", containsString("inexistente"))
		.body("error", is("Usuário inexistente"))
		;
		System.out.println("O status Erro deu certo, Parabéns!!");		
	}
	
	@Test
	
	public void deveVerificarListaNaRaiz() {
		
		given()
		.when()
			.get("/users")
		.then()
		.statusCode(200)
		.body("$", hasSize(3))
		.body("name", hasItems("João da Silva","Maria Joaquina","Ana Júlia"))
		.body("age[1]", is(25))
		.body("age[0]", is(30))
		.body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho")))
		.body("salary", contains(1234.5678f,2500, null))
		;
		System.out.println("A Lista Raiz deu certo, Parabéns!!");	
	}
	
	@Test
	public void deoFazerVerificacoesAvancadas() {
		given()
		.when()
			.get("/users")
		.then()
		.statusCode(200)
		.body("$", hasSize(3))
		//olhar documentação do grrove
		.body("age.findAll{it <= 25}.size()", is(2))//usuarios com idade menor ou igual
		.body("age.findAll{it <= 25 && it > 20}.size()", is(1))//usuarios com idade maior ou igual
		.body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
		.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
		.body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))//ultimo regristro
		.body("find{it.age <= 25}.name", is("Maria Joaquina"))//busca apenas o primeiro elemento
		.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia"))//verifica se existe um objeto dentro do elemento
		.body("findAll{it.name.length() > 10 }.name", hasItems("João da Silva", "Maria Joaquina"))//verifica tamanho dos caracteres
		.body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))// MUDA O ITEM
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))//ANTES DE MUDAR PEDIR PARA BUSCAR TODOS QUE COMEÇA COM "MARIA"
		//.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
		.body("age.collect{it * 2}", hasItems(60,50,40))//alterar idade (multipicar por 2 as idades na lista)
		.body("id.max()", is(3))//sabre maior id da minha coleção
		 .body("salary.min()", is(1234.5678f))//menor salario da lista
		 .body("salary.findAll{it != null}.sum()", is(closeTo(3734.5678f, 0.001)))//soma de todos so salarios
		 //.body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d),lessThan(5000)))//maior ou menor que 
		;
		
		System.out.println("Verificacoes Avancadas deu certo, Parabéns!!"); 
	}

	@Test
	public void devoUnirJsonPathComJava() {
		ArrayList<String> names = 
				given().when().get("/users")
				.then()
				.statusCode(200)
				.body("$", hasSize(3))
				.extract().path("name.findAll{it.startsWith('Maria')}")
		;
		Assert.assertEquals(1, names.size());
		Assert.assertEquals(names.get(0).toUpperCase(), "Maria joaquina".toUpperCase());

		System.out.println("Unir JsonPath Com Java deu certo, Parabéns!!");
	}
	
}
