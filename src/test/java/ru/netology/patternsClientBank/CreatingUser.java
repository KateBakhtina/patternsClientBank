package ru.netology.patternsClientBank;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreatingUser {

    private CreatingUser() {}

    public static class User {
        Faker user;
        String login;
        String password;
        String status;

        private User(String status) {
            this.user = new Faker();
            this.login = generateLogin();
            this.password = generatePassword();
            this.status = status;
        }

        private String generateLogin() {
            return this. user.name().username();
        }

        private String generatePassword() {
            return this.user.internet().password();
        }
    }

    static User createUser(String status) {
        User user = new User(status);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("login", user.login);
        jsonAsMap.put("password", user.password);
        jsonAsMap.put("status", user.status);
        given()
                .baseUri("http://localhost")
                .port(9999)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }
}
