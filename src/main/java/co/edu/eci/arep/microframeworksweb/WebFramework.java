package co.edu.eci.arep.microframeworksweb;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class WebFramework {
    private static Map<String, Map<String, BiFunction<Request, Response, String>>> routes = new HashMap<>();
    private static String staticFilesLocation = "";

    public static void main(String[] args) {
        staticFiles("src/main/resources");

        get("/hello", (req, resp) -> {
            String name = req.getParam("name");
            return name != null && !name.isEmpty() ? "Hello " + name + "!" : "Hello World!";
        });

        post("/submit", (req, resp) -> "Received: " + req.getBody());

        WebServer.getInstance().startServer();
    }

    public static void get(String path, BiFunction<Request, Response, String> handler) {
        routes.computeIfAbsent("GET", k -> new HashMap<>()).put("/App" + path, handler);
    }

    public static void post(String path, BiFunction<Request, Response, String> handler) {
        routes.computeIfAbsent("POST", k -> new HashMap<>()).put("/App" + path, handler);
    }

    public static void staticFiles(String location) {
        staticFilesLocation = location;
    }

    public static Map<String, Map<String, BiFunction<Request, Response, String>>> getRoutes() {
        return routes;
    }

    public static String getStaticFilesLocation() {
        return staticFilesLocation;
    }
}
