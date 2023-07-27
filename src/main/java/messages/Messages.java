package messages;


import java.util.HashMap;

public class Messages {
    public static HashMap<String, Object> endpointMessage(String message, int status) {
        return new HashMap<>() {{
            put("message", message);
            put("status", status);
        }};
    }

}
