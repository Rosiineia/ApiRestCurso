package br.ce.rosijesus.rest;

import org.junit.Test;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class VerbosTest {

	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"João Dos Santos\",\"age\": 36}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("João Dos Santos"))
			.body("age", is(36))
		;
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{\"age\": 36}")
	.when()
		.post("https://restapi.wcaquino.me/users")
	.then()
		.log().all()
		.statusCode(400)
		.body("id", is(nullValue()))
		.body("error", is("Name é um atributo obrigatório"))
		;
	}
	@Test
	public void deveSalvarUsuarioXML() {
		given()
			.log().all()
			.contentType("application/xml")
			.body("<user><name> Maria da Silva</name> <age> 40 </age> </user>")
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Maria da Silva"))
			.body("user.age", is("40"))
		;
	}
	@Test
	public void deveAlterarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Usuario Alterado\",\"age\": 40}")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario Alterado"))
			.body("age", is(40))
			.body("salary", is(1234.5678F))
		;
	}
	@Test
	public void devoDeletarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Usuario Alterado\",\"age\": 40}")
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204)
			;
	} 
	@Test
	public void NaoDevoDeletarUsuarioInexistente() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"Usuario Alterado\",\"age\": 40}")
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
			;
	} 
}

