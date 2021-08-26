package com.cybertek.Homework;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Homework_1 {


    /**
     * Q1:
     * - Given accept type is Json
     * - Path param value- US
     * - When users sends request to /countries
     * - Then status code is 200
     * - And Content - Type is Json
     * - And country_id is US
     * - And Country_name is United States of America
     * - And Region_id is
     * abcd
     */

    @BeforeAll
    public static void init() {

        baseURI = "http://54.152.151.1:1000/ords/hr";
    }

    @DisplayName("Get request to / countries with query param")
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"country_id\":\"US\"}")
                .when()
                .get("/countries");

        System.out.println("response.getStatusCode() = " + response.getStatusCode());
        response.prettyPrint();

        String country_id = response.path("items[0].country_id");
        String country_name = response.path("items[0].country_name");
        int Region_id = response.path("items[0].region_id");


        System.out.println("country_id = " + country_id);

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.getContentType());
        assertEquals("US", country_id);
        assertEquals("United States of America", country_name);
        assertEquals(2, Region_id);


    }

    /**
     * Q2:
     * - Given accept type is Json
     * - Query param value - q={"department_id":80}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And Content - Type is Json
     * - And all job_ids start with 'SA'
     * - And all department_ids are 80
     * - Count is 25
     */

    @DisplayName("GET request from employee_id:80 and assert")
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)

                .and().queryParam("q", "{\"department_id\":80}")
                .when()
                .get("/employees");

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.getContentType());
        //response.prettyPrint();

        List<String> job_idArray = response.path("items.job_id");

        for (String each : job_idArray) {
            assertTrue(each.contains("SA"));

        }

        List<Integer> department_ids = response.path("items.department_id");

        for (Integer eachOne : department_ids) {
            assertEquals(80, (int) eachOne);

        }

        List<String> count = response.path("items");
        //System.out.println("count = " + count);
        assertEquals(25, count.size());


    }

    /**
     * Q3:
     * - Given accept type is Json
     * -Query param value q= region_id 3
     * - When users sends request to /countries
     * - Then status code is 200
     * - And all regions_id is 3
     * - And count is 6
     * - And hasMore is false
     * - And Country_name are;
     * Australia,China,India,Japan,Malaysia,Singapore
     */

    @DisplayName("GET countries name based on region ID")
    @Test
    public void test3() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when()
                .get("/countries");


        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.getContentType());
        response.prettyPrint();

        List<Integer> region_id = response.path("items.region_id");

        for (Integer each : region_id) {
            assertEquals(3, each);
        }

        assertEquals(6, region_id.size());

        boolean hasMore = response.path("hasMore");
        assertEquals(false, hasMore);

        List<String> expectedCountries = Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");

        List<String> actualCountries = response.path("items.country_name");

        assertEquals(expectedCountries, actualCountries);

    }


}
