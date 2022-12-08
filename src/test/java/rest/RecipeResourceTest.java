package rest;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Disabled
class RecipeResourceTest
{
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    String recipeName = "king%20crab%20risotto";

    static HttpServer startServer()
    {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/recipe").then().statusCode(200);
    }

    @Test
    void hello()
    {
        given()
                .contentType("application/json")
                .get("/recipe").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello, food junkie"));
    }

//    @Test
//    @Disabled
//    void getRecipes()
//    {
//        given()
//                .contentType("application/json")
//                .get("/recipe/search/{recipe}",recipeName).then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode());
//    }

    @Test
    @Disabled
    void getBookmarksByUsername()
    {
        given()
                .contentType("application/json")
                .get("/bookmark/user").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("userName",notNullValue());
    }

    @Test
    @Disabled
    void getMealPlansByUsername()
    {
        given()
                .contentType("application/json")
                .get("/mealPlan/user").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("userName",notNullValue());
    }

}