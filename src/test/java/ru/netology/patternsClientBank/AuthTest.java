package ru.netology.patternsClientBank;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.*;
import io.restassured.specification.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.withText;


public class AuthTest {

    private static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeEach
    void openApplication() {
        Selenide.open("http://0.0.0.0:9999");
    }

    @Test
    void positiveSignTest() {
        CreatingUser.User user = CreatingUser.createUser("active");
        SelenideElement form = $("#root form");
        form.$("[data-test-id='login'] input[name='login']").setValue(user.login);
        form.$("[data-test-id='password'] input[name='password']").setValue("0000");
        form.$("button[data-test-id='action-login']").click();
        $(withText("Личный кабинет"));
    }

    @Test
    void negativeSignWithInvalidLoginTest() {
        CreatingUser.User user = CreatingUser.createUser("active");
        SelenideElement form = $("#root form");
        form.$("[data-test-id='login'] input[name='login']").setValue("other.login");
        form.$("[data-test-id='password'] input[name='password']").setValue(user.password);
        form.$("button[data-test-id='action-login']").click();
        $("#root [data-test-id='error-notification']").shouldBe(Condition.visible);
    }

    @Test
    void negativeSignWithInvalidPasswordTest() {
        CreatingUser.User user = CreatingUser.createUser("active");
        SelenideElement form = $("#root form");
        form.$("[data-test-id='login'] input[name='login']").setValue(user.login);
        form.$("[data-test-id='password'] input[name='password']").setValue("0000");
        form.$("button[data-test-id='action-login']").click();
        $("#root [data-test-id='error-notification']").shouldBe(Condition.visible);
    }

    @Test
    void negativeSignWithBlockedStatusTest() {
        CreatingUser.User user = CreatingUser.createUser("blocked");
        SelenideElement form = $("#root form");
        form.$("[data-test-id='login'] input[name='login']").setValue(user.login);
        form.$("[data-test-id='password'] input[name='password']").setValue(user.password);
        form.$("button[data-test-id='action-login']").click();
        $("#root [data-test-id='error-notification']").shouldBe(Condition.visible);
    }
}
