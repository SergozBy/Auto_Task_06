package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.data.DataHelper.getAuthInfo;

public class CardTooCardTransferTest {

    @Test
    @DisplayName("Should transfer money between own cards")
    void shouldTransferMoneyFromSecondToFirst() {
        var info = getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);

        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);

        var verificationPage = loginPage.validLogin(info);
        verificationPage.validVerify(verificationCode);

        $("[data-test-id='dashboard']").shouldBe(Condition.visible);

        // Init cards info
        // First card
        var firstCardNumber = "5559 0000 0000 0001";
        var firstCardId = "92df3f1c-a033-48e6-8390-206f6b1f56c0";

        // Second card
        var secondCardNumber = "5559 0000 0000 0002";
        var secondCardId = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";

        // Set amount for transaction
        var transferAmount = 500;
        var transferAmountString = Integer.toString(transferAmount);

        // Find cards on page
        var transferOnFirstCard = $$("[data-test-id='action-deposit']").first();
        var transferOnSecondCard = $$("[data-test-id='action-deposit']").first(1);

        // Check balance before transaction
        DashboardPage db = new DashboardPage();
        var balanceBeforeTransfer = db.getCardBalance(firstCardId);
        System.out.println("balanceBeforeTransferString = " + balanceBeforeTransfer);

        // Transaction
        transferOnFirstCard.click();
        $("[data-test-id='amount'] .input__control").setValue(transferAmountString);
        $("[data-test-id='from'] .input__control").setValue(secondCardNumber);
        $("[data-test-id='action-transfer']").click();

        // Must return to "Личный кабинет"
        $("[data-test-id='dashboard']").shouldBe(Condition.visible);

        // Count expected balance after transaction
        var balanceAfterTransferSum = balanceBeforeTransfer + transferAmount;
        var balanceAfterTransferString = Integer.toString(balanceAfterTransferSum);

        // Check actual balance after transaction and comparing it to expected
        $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").shouldHave(Condition.text(balanceAfterTransferString));
        System.out.println("balanceAfterTransferSum = " + balanceAfterTransferSum);
    }

    @Test
    @DisplayName("Should transfer money between own cards")
    void shouldTransferMoneyFromFirstToSecond() {
        var info = getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);

        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);

        var verificationPage = loginPage.validLogin(info);
        verificationPage.validVerify(verificationCode);

        $("[data-test-id='dashboard']").shouldBe(Condition.visible);

        // Init cards info
        // First card
        var firstCardNumber = "5559 0000 0000 0001";
        var firstCardId = "92df3f1c-a033-48e6-8390-206f6b1f56c0";

        // Second card
        var secondCardNumber = "5559 0000 0000 0002";
        var secondCardId = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";

        // Set amount for transaction
        var transferAmount = 500;
        var transferAmountString = Integer.toString(transferAmount);

        // Find cards on page
        var transferOnFirstCard = $$("[data-test-id='action-deposit']").first();
        var transferOnSecondCard = $("li:nth-child(2) [data-test-id='action-deposit']");

        // Check balance before transaction
        DashboardPage db = new DashboardPage();
        var balanceBeforeTransfer = db.getCardBalance(secondCardId);
        System.out.println("balanceBeforeTransferString = " + balanceBeforeTransfer);

        // Transaction
        transferOnSecondCard.click();
        $("[data-test-id='amount'] .input__control").setValue(transferAmountString);
        $("[data-test-id='from'] .input__control").setValue(firstCardNumber);
        $("[data-test-id='action-transfer']").click();

        // Must return to "Личный кабинет"
        $("[data-test-id='dashboard']").shouldBe(Condition.visible);

        // Count expected balance after transaction
        var balanceAfterTransferSum = balanceBeforeTransfer + transferAmount;
        var balanceAfterTransferString = Integer.toString(balanceAfterTransferSum);

        // Check actual balance after transaction and comparing it to expected
        $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").shouldHave(Condition.text(balanceAfterTransferString));
        System.out.println("balanceAfterTransferSum = " + balanceAfterTransferSum);
    }
}
