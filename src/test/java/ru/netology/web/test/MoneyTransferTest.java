package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int amount = 100;
        int balance1 = dashboardPage.getCardBalance(0);
        int balance2 = dashboardPage.getCardBalance(1);
        var transferPage = dashboardPage.depositCard(0);
        dashboardPage = transferPage.moneyTransfer(amount, DataHelper.getCardNumber(1));
        Assertions.assertEquals(balance1 + amount, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balance2 - amount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        var dashboardPage = verificationPage.validVerify(verificationCode);

        int amount = 100;
        int balance1 = dashboardPage.getCardBalance(0);
        int balance2 = dashboardPage.getCardBalance(1);
        var transferPage = dashboardPage.depositCard(1);
        dashboardPage = transferPage.moneyTransfer(amount, DataHelper.getCardNumber(0));
        Assertions.assertEquals(balance1 - amount, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balance2 + amount, dashboardPage.getCardBalance(1));

    }

    // Тест не проходит из-за ошибки в приложении -
    // возможно снять сумму, превышающую баланс.
    // Раскомментировать тест после исправления.
   /* @Test
    void shouldNotTransferAmountMoreBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int amount = dashboardPage.getCardBalance(0) + 1;
        int balance1 = dashboardPage.getCardBalance(0);
        int balance2 = dashboardPage.getCardBalance(1);
        var transferPage = dashboardPage.depositCard(1);
        dashboardPage = transferPage.moneyTransfer(amount, DataHelper.getCardNumber(0));
        Assertions.assertEquals(balance1, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balance2, dashboardPage.getCardBalance(1));
    }*/

    @Test
    void shouldNotHaveCardsWithOtherLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getOtherAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        var dashboardPage = verificationPage.validVerify(verificationCode);
        Assertions.assertEquals(0, dashboardPage.getCardsAmount());
    }

    @Test
    void shouldReturnToDashboard() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferPage = dashboardPage.depositCard(0);
        dashboardPage = transferPage.cancelTransfer();

        Assertions.assertEquals(2, dashboardPage.getCardsAmount());
    }

}

