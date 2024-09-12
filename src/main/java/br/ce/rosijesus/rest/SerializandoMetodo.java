package br.ce.rosijesus.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.junit.Assert;

public class SerializandoMetodo {
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("name", "Usuario Via Map");
		params.put("age", 30);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario Via Map"))
			.body("age", is(30))
		;
	}
	
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		User user = new User("Usuario Via Objeto", 30);
		
		
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario Via Objeto"))
			.body("age", is(30))
		;
	}
	

	@Test
	public void deveDeserializarObjetoAoSalvarUsuario() {
		User user = new User("Usuario Deserializado", 30);
		
		
		User usuarioInserido = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
		;
		System.out.println(usuarioInserido);
		Assert.assertEquals("Usuario Deserializado", usuarioInserido.getName());
		Assert.assertThat(usuarioInserido.getAge(), is(30))
		;
	}

}
