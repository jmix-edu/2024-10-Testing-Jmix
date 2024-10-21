package com.company.jmixpmflowbase.selenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.company.jmixpmflowbase.selenide.pages.SelenideLoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.editable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class UiLoginTest {

    @Test
    public void testLoginPage() {
        SelenideLoginPage loginPage = new SelenideLoginPage();

        loginPage.open();
        sleep(2000);
        loginPage.enterUsername("dev1");
        loginPage.enterPassword("admin");
        loginPage.clickLoginBtn();
    }

    @Test
    public void test_username_field(){
        Selenide.open("http://localhost:8080/");
        sleep(2000);
        SelenideElement usernameField = $(By.name("username"));
        usernameField.shouldBe(visible);
        usernameField.shouldBe(editable);
        Assertions.assertEquals(("admin"), usernameField.getValue());

    }

    @Test
    public void test_password_field(){
        Selenide.open("http://localhost:8080/");
        SelenideElement passwordField = $(By.name("password"));
        passwordField.shouldBe(visible);
        passwordField.shouldBe(editable);

        Assertions.assertEquals("password", passwordField.getAttribute("type"));
    }
}
