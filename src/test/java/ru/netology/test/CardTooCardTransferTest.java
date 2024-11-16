package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import ru.netology.data.DataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.data.DataHelper.getAuthInfo;

public class CardTooCardTransferTest {

    @Test
    @DisplayName("Should transfer money between own cards")
    void shouldTransferMoneyBetweenOwnCards() {
        var info = getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);

        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);

        var verificationPage = loginPage.validLogin(info);
        verificationPage.validVerify(verificationCode);

        $("[data-test-id='dashboard']").shouldBe(Condition.visible);

        var firstCardNumber = "5559 0000 0000 0001";
        var secondCardNumber = "5559 0000 0000 0002";

        var firstCardBalance = 1_000;
        var secondCardBalance = 1_000;
        var transferAmount = 500;


        var firstCardTransfer = $$("[data-test-id='action-deposit']").first();
        var secondCardTransfer = $$("[data-test-id='action-deposit']").first(1);

        firstCardTransfer.click();
        $("[data-test-id='amount'] .input__control").setValue("123");
        $("[data-test-id='from'] .input__control").setValue(secondCardNumber);
        $("[data-test-id='action-transfer']").click();

        $("[data-test-id='dashboard']").shouldBe(Condition.visible);


        var id = $$("[data-test-id]").first().getValue();
    }
}
