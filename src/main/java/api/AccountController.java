package api;

import com.google.gson.Gson;
import com.google.inject.Inject;
import exception.InsufficientFundsException;
import exception.NotFoundException;
import exception.TransactionQueueIsFullException;
import org.eclipse.jetty.http.HttpStatus;
import service.Account;
import service.IAccountService;

import java.math.BigDecimal;

import static spark.Spark.*;

public class AccountController implements IApiController {

    private final Gson gson = new Gson();
    private final IAccountService accountService;

    @Inject
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    public void setUp() {
        path("/accounts", () -> {
            // CREATE
            post("", (request, response) -> {
                Account account = accountService.create();
                response.status(HttpStatus.CREATED_201);
                response.header("Location", "accounts/"+account.getId());
                return account;
            }, gson::toJson);
            // GET
            get("", (request, response) -> accountService.getAll(), gson::toJson);
            // GET
            get("/:id", (request, response) -> {
                long id = Long.parseLong(request.params("id"));
                return accountService.get(id);
            }, gson::toJson);
            // DELETE
            delete("/:id", (request, response) -> {
                long id = Long.parseLong(request.params("id"));
                accountService.delete(id);
                response.status(HttpStatus.NO_CONTENT_204);
                return null;
            }, gson::toJson);

            post("/:id/withdraw", (request, response) -> {
                long id = Long.parseLong(request.params("id"));
                double amount = Double.parseDouble(request.queryParams("amount"));
                accountService.withdraw(id, BigDecimal.valueOf(amount));
                response.status(HttpStatus.NO_CONTENT_204);
                return null;
            }, gson::toJson);

            post("/:id/deposit", (request, response) -> {
                long id = Long.parseLong(request.params("id"));
                double amount = Double.parseDouble(request.queryParams("amount"));
                accountService.deposit(id, BigDecimal.valueOf(amount));
                response.status(HttpStatus.NO_CONTENT_204);
                return null;
            }, gson::toJson);

            post("/transfer", (request, response) -> {
                long fromId = Long.parseLong(request.queryParams("fromId"));
                long toId = Long.parseLong(request.queryParams("toId"));
                double amount = Double.parseDouble(request.queryParams("amount"));
                accountService.transfer(fromId, toId, BigDecimal.valueOf(amount));
                response.status(HttpStatus.NO_CONTENT_204);
                return null;
            }, gson::toJson);

        });

        exception(NotFoundException.class, (exception, request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            response.body(gson.toJson(exception.getMessage()));
        });
        exception(TransactionQueueIsFullException.class, (exception, request, response) -> {
            response.status(HttpStatus.SERVICE_UNAVAILABLE_503);
            response.body(gson.toJson(exception.getMessage()));
        });
        exception(InsufficientFundsException.class, (exception, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(gson.toJson(exception.getMessage()));
        });
        exception(IllegalArgumentException.class, (exception, request, response) -> {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.body(gson.toJson(exception.getMessage()));
        });
        exception(NumberFormatException.class, (exception, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(gson.toJson(exception.getMessage()));
        });
    }
}