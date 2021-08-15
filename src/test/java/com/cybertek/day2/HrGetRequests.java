package com.cybertek.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HrGetRequests {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://54.152.151.1:1000/ords/hr";
    }
    @DisplayName("GET request to /regions")
    @Test
    public void test1(){

        Response response = RestAssured.get("/regions");

        System.out.println(response.statusCode());


    }
    @DisplayName("GET request to /regions/2")
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .when()
                .get("/regions/2");

        assertEquals(response.statusCode(), 200);

        assertEquals(response.contentType(), "application/json");

        response.prettyPrint();

        assertTrue(response.body().asString().contains("Americas"));
        //Bellow is another way to do it
        //Assertions.assertEquals(response.body().asString().contains("Americas"),true);


    }


}
