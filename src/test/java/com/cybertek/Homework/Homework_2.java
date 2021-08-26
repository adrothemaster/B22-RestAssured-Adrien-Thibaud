package com.cybertek.Homework;

import com.cybertek.pojo.Spartan;
import com.cybertek.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import javafx.scene.layout.Priority;
import org.junit.jupiter.api.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Homework_2 extends SpartanTestBase {

    static int id = 0;

    @DisplayName("Post to API")
    @Test
    @Order(1)


    public void post1(){

        Spartan postSpartan = new Spartan();
        postSpartan.setName("Steven");
        postSpartan.setGender("Male");
        postSpartan.setPhone(9876543210L);


        Response response = given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().body(postSpartan).log().all()
                .when().post("/api/spartans");

        String expectedHeader = "A Spartan is Born!";
                response.prettyPrint();

                assertThat(response.statusCode(),is(201));
                assertThat(response.contentType(),is("application/json"));
                assertThat(response.path("success"),is(expectedHeader));
                assertThat(response.path("data.name"),is(postSpartan.getName()));
                assertThat(response.path("data.gender"),is(postSpartan.getGender()));
                assertThat(response.path("data.phone"),is(postSpartan.getPhone()));
                id = response.path("data.id");

    }

    @DisplayName("Put to API")
    @Test
    @Order(2)

    public void putTest(){

        Spartan postSpartan = new Spartan();
        postSpartan.setName("Alan");
        postSpartan.setGender("Male");
        postSpartan.setPhone(7776543210L);

        Map<String,Object> putSpartan = new LinkedHashMap<>();
        putSpartan.put("name", "Alan");
        putSpartan.put("gender" , "male");
        putSpartan.put("phone", 111111111111L);

        given().contentType(ContentType.JSON)
                .body(postSpartan)
                .pathParam("id",id)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204);

    }

    @DisplayName("Patch to API")
    @Test
    @Order(3)

    public void patchTest(){
        Map<String,Object> putSpartan = new LinkedHashMap<>();

        putSpartan.put("gender" , "Male");
        putSpartan.put("phone", 77525687568L);


        given().contentType(ContentType.JSON)
                .body(putSpartan)
                .pathParam("id", id)
                .log().all()
                .patch("/api/spartans/{id}")
                .then().statusCode(204);





    }

    @DisplayName("Delete from API")
    @Test
    @Order(4)

    public void testDelete(){

        given().contentType(ContentType.JSON)
                .pathParam("id", id)
                .delete("/api/spartans/{id}")
                .then().statusCode(204);




    }
}
