package api;

import org.eclipse.jetty.http.HttpStatus;
import settings.WebServerSettings;


import static spark.Spark.*;

public class AccountController {

    private final WebServerSettings serverSettings;

    public AccountController(WebServerSettings serverSettings) {
        this.serverSettings = serverSettings;
    }

    public void start() {
        port(serverSettings.getPort());

//        path("/accounts", () -> {
//            // Create new account
//            post("", (request, response) -> {
//                double balance = Double.parseDouble(request.queryParams("balance"));
//                response.status(HttpStatus.CREATED_201);
//                return accountService.create(balance);
//            });
//            // Get info about particular account
//            get("/:id", (request, response) -> {
//                long id = Long.parseLong(request.params("id"));
//                return accountService.get(id);
//            });
//            // Delete particular account
//            delete("/:id", (request, response) -> {
//                long id = Long.parseLong(request.params("id"));
//                accountService.delete(id);
//                return "Deleted";
//            });
//            // Withdraw money from particular account
//            post("/:id/withdraw", (request, response) -> {
//                long id = Long.parseLong(request.params("id"));
//                double amount = Double.parseDouble(request.queryParams("amount"));
//                accountService.withdraw(id, amount);
//                return "Withdrawn";
//            });
//            // Deposit money to particular account
//            post("/:id/deposit", (request, response) -> {
//                long id = Long.parseLong(request.params("id"));
//                double amount = Double.parseDouble(request.queryParams("amount"));
//                accountService.deposit(id, amount);
//                return "Deposited";
//            });
//            // Deposit money between two particular accounts
//            post("/transfer", (request, response) -> {
//                long fromId = Long.parseLong(request.queryParams("fromId"));
//                long toId = Long.parseLong(request.queryParams("toId"));
//                double amount = Double.parseDouble(request.queryParams("amount"));
//                accountService.transfer(fromId, toId, amount);
//                return "Transferred";
//            });
//            // Get info about all accounts
//            get("", (request, response) -> accountService.getAll());
//        });
//
//        exception(AccountNotFoundException.class, (exception, request, response) -> {
//            response.status(HttpStatus.NOT_FOUND_404);
//            response.body(String.format("{\"message\": \"%s\"}", exception.getMessage()));
//        });
//        exception(InsufficientBalanceException.class, (exception, request, response) -> {
//            response.status(HttpStatus.BAD_REQUEST_400);
//            response.body(String.format("{\"message\": \"%s\"}", exception.getMessage()));
//        });
//        exception(IllegalArgumentException.class, (exception, request, response) -> {
//            response.status(HttpStatus.BAD_REQUEST_400);
//            response.body(String.format("{\"message\": \"%s\"}", exception.getMessage()));
//        });
    }
}