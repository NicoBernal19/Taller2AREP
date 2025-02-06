package co.edu.eci.arep.microframeworksweb;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private Map<String, String> queryParams;
    private String body;

    public Request(String queryString, String body) {
        this.queryParams = parseQueryString(queryString);
        this.body = body;
    }

    public String getParam(String key) {
        return queryParams.get(key);
    }

    public String getBody() {
        return body;
    }

    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> params = new HashMap<>();
        if (queryString != null && !queryString.isEmpty()) {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}

