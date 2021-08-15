package com.cybertek.day2;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanNegitiveGetTest {

    @BeforeAll
    public static void init(){

        baseURI = "http://54.152.151.1:8000";
    }
    @DisplayName("GET request to /api/spartans/10 in XML = error code 406")
    @Test
    public void test1(){
        // /api/spartans/10 individual search like /10 cannot be searched/does not accept in XML
        Response response = given()
                                    .accept(ContentType.XML)
                            .when()
                                    .get("/api/spartans/10");


        assertEquals(406, response.statusCode());

        assertEquals("application/xml;charset=UTF-8", response.contentType());

    }
}
