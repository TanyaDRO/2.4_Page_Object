package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement cardFromField = $("[data-test-id=from] input");
    // private SelenideElement cardToField = $("[data-test-id=to] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");


    public TransferPage() {
        heading.shouldBe(visible);
    }


    public DashboardPage moneyTransfer(int amount, String cardFrom) {
        amountField.setValue(Integer.toString(amount));
        cardFromField.setValue(cardFrom);
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage cancelTransfer() {
        cancelButton.click();
        return new DashboardPage();
    }

}

