package utils;

import net.minidev.json.JSONObject;


public class JsonObjectCreator {

    public static JSONObject getCustomerJsonObject(String name, String email, String contact, String fail_existing, String note) {
        JSONObject notes = new JSONObject();
        notes.put("note", note);
        JSONObject customerInfo = new JSONObject();
        customerInfo.put("name", name);
        customerInfo.put("email", email);
        customerInfo.put("contact", contact);
        customerInfo.put("fail_existing", fail_existing);
        customerInfo.put("notes", notes);
        return customerInfo;
    }

    public static JSONObject getOrderJsonObject(int amount, String currency, String receipt, boolean payment_capture, String note) {
        JSONObject notes = new JSONObject();
        notes.put("note", note);
        JSONObject orderInfo = new JSONObject();
        orderInfo.put("amount", amount);
        orderInfo.put("currency", currency);
        orderInfo.put("receipt", receipt);
        orderInfo.put("payment_capture", payment_capture);
        orderInfo.put("notes", notes);
        return orderInfo;
    }
}
