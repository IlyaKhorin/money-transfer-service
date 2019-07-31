package api;

import dao.dto.AccountDto;
import io.restassured.response.ValidatableResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AccountControllerIntegrationTest extends ApiTestBase {

    @Test
    public void createAccount() {

        ValidatableResponse response = given()
                .when()
                .post("/accounts")
                .then()
                .statusCode(HttpStatus.CREATED_201);

        AccountDto account = response.extract().body().as(AccountDto.class);
        assertEquals(BigDecimal.valueOf(0),account.getBalance());
        assertEquals(response.extract().header("Location"), "accounts/" + account.getId().toString());
    }

    @Test
    public void getAccount() {
        BigDecimal balance = BigDecimal.valueOf(123);
        Long accountId = createAccount(balance);

        AccountDto actual = given()
                .pathParam("id", accountId)
                .when()
                .get("/accounts/{id}")
                .then()
                .statusCode(HttpStatus.OK_200)
                .extract()
                .body()
                .as(AccountDto.class);
        assertEquals(accountId, actual.getId());
        assertEquals(0,balance.compareTo( actual.getBalance()));
    }

    @Test
    public void getAccounts() {
        BigDecimal balance1 = BigDecimal.valueOf(100);
        BigDecimal balance2 = BigDecimal.valueOf(200);
        Long accountId1 = createAccount(balance1);
        Long accountId2 = createAccount(balance2);

        AccountDto[] actual = given()
                .when()
                .get("/accounts")
                .then()
                .statusCode(HttpStatus.OK_200)
                .extract()
                .body()
                .as(AccountDto[].class);

        AccountDto actual1 = Arrays.stream(actual).filter(accountDto -> accountDto.getId().equals(accountId1)).findFirst().get();
        assertEquals(0, balance1.compareTo(actual1.getBalance()));

        AccountDto actual2 = Arrays.stream(actual).filter(accountDto -> accountDto.getId().equals(accountId2)).findFirst().get();
        assertEquals(0, balance2.compareTo(actual2.getBalance()));
    }

    @Test
    public void getAccount_shouldReturn404() {
        given()
                .pathParam("id", 1)
                .when()
                .get("/accounts/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    public void deleteAccount() {
        long id = createAccount(BigDecimal.valueOf(0));

        given().
                pathParam("id", id).
                when().
                delete("/accounts/{id}").
                then().
                statusCode(HttpStatus.NO_CONTENT_204);
    }

    @Test
    public void withdraw() {
        long id = createAccount(BigDecimal.valueOf(100));

        given().
                pathParam("id", id).
                param("amount", 50).
                when().
                post("/accounts/{id}/withdraw").
                then().
                statusCode(HttpStatus.NO_CONTENT_204);
    }

    @Test
    public void withdraw_shouldReturn400() {
        long id = createAccount(BigDecimal.valueOf(100));

        given().
                pathParam("id", id).
                param("amount", 500).
                when().
                post("/accounts/{id}/withdraw").
                then().
                statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void deposit() {
        long id = createAccount(BigDecimal.valueOf(100));

        given().
                pathParam("id", id).
                param("amount", 500).
                when().
                post("/accounts/{id}/deposit").
                then().
                statusCode(HttpStatus.NO_CONTENT_204);
    }

    @Test
    public void deposit_shouldReturn400() {
        long id = createAccount(BigDecimal.valueOf(100));

        given().
                pathParam("id", id).
                param("amount", "abc").
                when().
                post("/accounts/{id}/deposit").
                then().
                statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void transfer() {
        BigDecimal balanceFrom = BigDecimal.valueOf(100);
        BigDecimal balanceTo = BigDecimal.valueOf(50);
        BigDecimal amount = BigDecimal.valueOf(60);
        long fromId = createAccount(balanceFrom);
        long toId = createAccount(balanceTo);

        given().
                param("fromId", fromId).
                param("toId", toId).
                param("amount", 60).
                when().
                post("/accounts/transfer").
                then().
                statusCode(HttpStatus.NO_CONTENT_204);

        AccountDto accountFrom = get(fromId);
        AccountDto accountTo = get(toId);
        Assert.assertEquals(0, accountFrom.getBalance().compareTo(balanceFrom.subtract(amount)));
        Assert.assertEquals(0, accountTo.getBalance().compareTo(balanceTo.add(amount)));
    }

    private static Long createAccount(BigDecimal amount) {
        Long id = given()
                .when()
                .post("/accounts")
                .then()
                .statusCode(HttpStatus.CREATED_201)
                .extract().body().as(AccountDto.class).getId();
        given().pathParam("id", id)
                .param("amount", amount)
                .when()
                .post("/accounts/{id}/deposit")
                .then()
                .statusCode(HttpStatus.NO_CONTENT_204);
        return id;
    }

    private static AccountDto get(Long id) {
        return given()
                .pathParam("id", id)
                .when()
                .get("/accounts/{id}")
                .then()
                .statusCode(HttpStatus.OK_200)
                .extract().body().as(AccountDto.class);
    }
}