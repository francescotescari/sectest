package it.unitn.sectest.xss_suite;

import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.ProcedureHelper;
import utils.XssPayload;

public class XssOrdersPhp22Min extends BaseTest {
    private Integer orderId;

    /*
    Attack description:
    - create order with double quote attribute escape xss payload as clientContact (eg: " /><h1>Ciao</h1><input x=")
    - go to the orders edit url for that specific order
     */
    @Test
    public void test() {
        XssPayload payload = XssPayload.genDoubleQuoteAttributePayload("input", true);
        helper.requireLoginAdmin();
        orderId = helper.createDummyOrder("dummy", payload.toString());
        helper.get(ProcedureHelper.ORDERS_EDIT_URL(orderId));
        assertPayloadNextTo(payload, "clientContact");
    }

    @Override
    public void clean() {
        if (orderId != null) {
            helper.deleteOrder(orderId);
            orderId = null;
        }
    }
}
