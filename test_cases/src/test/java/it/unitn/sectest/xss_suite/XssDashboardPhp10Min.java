package it.unitn.sectest.xss_suite;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import utils.BaseTest;
import utils.ProcedureHelper;
import utils.XssPayload;

public class XssDashboardPhp10Min extends BaseTest {
    private Integer orderId, userId;

    /*
    Attack description:
    - create user with plain xss payload as username (eg: <h1>Ciao</h1>)
    - login as admin and go to dashboard page
     */
    @Test
    public void test() {
        helper.requireLoginAdmin();
        XssPayload payload = XssPayload.genPlainPayload();
        userId = helper.createDummyUser(payload.toString());
        helper.requireLogin(payload.toString());
        orderId = helper.createDummyOrder();
        helper.requireLoginAdmin();
        helper.get(ProcedureHelper.DASHBOARD_PATH);
        assert payload.isInElement(helper.findElement(By.id("productTable")));
    }

    @Override
    public void clean() {
        if (orderId != null) {
            helper.deleteOrder(orderId);
            orderId = null;
        }
        if (userId != null) {
            helper.deleteUser(userId);
            userId = null;
        }
    }
}
