package com.herokuapp.restful.steps;

import com.herokuapp.restful.constants.EndPoints;
import com.herokuapp.restful.model.HerokuappPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    @Step("Set authentication With username : {0}, password: {1}")
    public ValidatableResponse getAuthenticationToken(String username,
                                                      String password) {
        HerokuappPojo herokuappPojo = new HerokuappPojo();
        herokuappPojo.setUsername(username);
        herokuappPojo.setPassword(password);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(herokuappPojo)
                .when()
                .post(EndPoints.CREATE_AUTH)
                .then();
    }

    @Step("Create Booking  with firstname: {0}, lastname: {1}, totalprice: {2}, depositepaid: {3}, bookingsDates:{4}, addtionalsneeds : {5} ")
    public ValidatableResponse createBooking(String firstname,
                                             String lastname,
                                             int totalprice,
                                             boolean depositpaid,
                                             HashMap<Object, Object> bookingsDatesData,
                                             String additionalNeeds) {
        HerokuappPojo herokuappPojo = new HerokuappPojo();
        herokuappPojo.setFirstname(firstname);
        herokuappPojo.setLastname(lastname);
        herokuappPojo.setTotalprice(totalprice);
        herokuappPojo.setDepositpaid(depositpaid);
        herokuappPojo.setBookingdates(bookingsDatesData);
        herokuappPojo.setAdditionalneeds(additionalNeeds);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .auth().preemptive().basic("admin", "password123")
                .body(herokuappPojo)
                .when()
                .post(EndPoints.GET_BOOKING)
                .then();
    }

    @Step("Update Booking  BookingID: {0},firstname: {1}, lastname: {2}, totalprice: {3}, depositepaid: {4}, bookingsDates:{5}, addtionalsneeds : {6} ")
    public ValidatableResponse updateBooking(int bookingID,
                                             String firstname,
                                             String lastname,
                                             int totalprice,
                                             boolean depositpaid,
                                             HashMap<Object, Object> bookingsDatesData,
                                             String additionalNeeds) {
        HerokuappPojo herokuappPojo = new HerokuappPojo();
        herokuappPojo.setFirstname(firstname);
        herokuappPojo.setLastname(lastname);
        herokuappPojo.setTotalprice(totalprice);
        herokuappPojo.setDepositpaid(depositpaid);
        herokuappPojo.setBookingdates(bookingsDatesData);
        herokuappPojo.setAdditionalneeds(additionalNeeds);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(herokuappPojo)
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin", "password123")
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then();
    }

    @Step("Delete Booking  BookingID: {0}")
    public ValidatableResponse deleteBooking(int bookingID) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin", "password123")
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then();
    }

    @Step("Get Booking by BookingID: {0}")
    public ValidatableResponse getBookingByID(int bookingID) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("bookingID", bookingID)
                .when()
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then();
    }

}
