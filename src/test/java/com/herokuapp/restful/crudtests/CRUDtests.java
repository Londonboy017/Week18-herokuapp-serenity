package com.herokuapp.restful.crudtests;

import com.herokuapp.restful.steps.BookingSteps;
import com.herokuapp.restful.testbase.TestBase;
import com.herokuapp.restful.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class CRUDtests extends TestBase {
    public static String username = "admin";
    public static String password = "password123";
    public static String firstname = "Jason" + TestUtils.getRandomValue();
    public static String lastname = "Roy" + TestUtils.getRandomValue();
    public static Integer totalprice = 55;
    public static Boolean depositpaid = true;
    public static String additionalneeds = "SwimmingPool";
    public static int bookingID;
    public static String token;

    @Steps
    BookingSteps bookingSteps;

    @Title("This will set authentication of user")
    @Test
    public void test001() {
        ValidatableResponse response = bookingSteps.getAuthenticationToken(username, password);
        response.log().all().statusCode(200);
        HashMap<?, ?> tokenMap = response.log().all().extract().path("");
        Assert.assertThat(tokenMap, hasKey("token"));
        System.out.println(token);
    }

    @Title("This test will Create a new booking of user")
    @Test
    public void test002() {
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2018-09-11");
        bookingsDatesData.put("checkout", "2022-05-12");
        ValidatableResponse response = bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingsDatesData, additionalneeds);
        response.log().all().statusCode(200);
        bookingID = response.log().all().extract().path("bookingid");
        String nameFetched = response.log().all().extract().path("booking.firstname");
        System.out.println("Check firstname : " + nameFetched);
        Assert.assertEquals(firstname,nameFetched);
    }

    @Title("This test will Update a booking")
    @Test
    public void test003() {
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2018-09-01");
        bookingsDatesData.put("checkout", "2020-11-04");
        firstname = firstname + "_updated";
        lastname = lastname + "_updated";
        additionalneeds = "no";
        ValidatableResponse response = bookingSteps.updateBooking(bookingID, firstname, lastname, totalprice, depositpaid, bookingsDatesData, additionalneeds);
        response.log().all().statusCode(200);
        String nameFetched = response.log().all().extract().path("firstname");
        System.out.println("Check firstname : " + nameFetched);
        Assert.assertEquals( firstname,nameFetched);

    }

    @Title("This will Deleted a user")
    @Test
    public void test004() {

        ValidatableResponse response = bookingSteps.deleteBooking(bookingID);
        response.log().all().statusCode(201);
        ValidatableResponse response1 = bookingSteps.getBookingByID(bookingID);
        response1.log().all().statusCode(404);

    }


}
