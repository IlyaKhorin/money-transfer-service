package api;

import dao.InMemoryAccountDao;
import io.restassured.RestAssured;
import model.Account;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.AccountService;
import service.AccountServiceImpl;
import spark.Spark;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.URLENC;

/**
 * Integration test for whole {@link RestApiController}.
 */
public class AccountControllerIntegrationTest {

    private static final int PORT = 1234;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:" + PORT;
        AccountService service = new AccountServiceImpl(new InMemoryAccountDao());
        new RestApiController(PORT, service).start();
        Spark.awaitInitialization();
    }

    @AfterClass
    public static void tearDown() {
        Spark.stop();
    }

    @Test
    public void createAccount() {
        double balance = 1.4;

        String result = given().
                param("balance", balance).
                contentType(URLENC).
                when().
                post("/accounts").
                then().
                statusCode(HttpStatus.CREATED_201).
                extract().body().asString();

        Assert.assertTrue(result.contains(String.valueOf(balance)));
    }

    @Test
    public void createAccount_shouldReturn400() {
        given().
                param("balance", -123.456).
                contentType(URLENC).
                when().
                post("/accounts").
                then().
                statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void getAccount() {
        long id = createAccount(123);

        given().
                pathParam("id", id).
                when().
                get("/accounts/{id}").
                then().
                statusCode(HttpStatus.OK_200);
    }

    @Test
    public void getAccount_shouldReturn404() {
        given().
                pathParam("id", 123456).
                when().
                get("/accounts/{id}").
                then().
                statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    public void deleteAccount() {
        long id = createAccount(123);

        given().
                pathParam("id", id).
                when().
                delete("/accounts/{id}").
                then().
                statusCode(HttpStatus.OK_200);
    }

    @Test
    public void withdraw() {
        long id = createAccount(123);

        given().
                pathParam("id", id).
                param("amount", 11).
                when().
                post("/accounts/{id}/withdraw").
                then().
                statusCode(HttpStatus.OK_200);
    }

    @Test
    public void withdraw_shouldReturn400() {
        long id = createAccount(123);

        given().
                pathParam("id", id).
                param("amount", 1111).
                when().
                post("/accounts/{id}/withdraw").
                then().
                statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void deposit() {
        long id = createAccount(123);

        given().
                pathParam("id", id).
                param("amount", 1122345).
                when().
                post("/accounts/{id}/deposit").
                then().
                statusCode(HttpStatus.OK_200);
    }

    @Test
    public void transfer() {
        long fromId = createAccount(20);
        long toId = createAccount(10);

        given().
                param("fromId", fromId).
                param("toId", toId).
                param("amount", 1).
                when().
                post("/accounts/transfer").
                then().
                statusCode(HttpStatus.OK_200);

        String result = given().
                when().
                get("/accounts").
                then().
                statusCode(HttpStatus.OK_200).
                extract().body().asString();
        Assert.assertTrue(result.contains(new Account(fromId, new BigDecimal(19)).toString()));
        Assert.assertTrue(result.contains(new Account(toId, new BigDecimal(11)).toString()));
    }


    // It's better to use JSON serialization/deserialization
    private static long createAccount(double balance) {
        String result = given().
                param("balance", balance).
                contentType(URLENC).
                when().
                post("/accounts").
                then().
                statusCode(HttpStatus.CREATED_201).
                extract().body().asString();

        return Long.parseLong(result.substring(result.indexOf("id=") + 3, result.indexOf(',')));
    }
}