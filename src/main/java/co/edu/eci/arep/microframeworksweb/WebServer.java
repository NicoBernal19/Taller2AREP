package co.edu.eci.arep.microframeworksweb;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.BiFunction;

public class WebServer {
    private static WebServer instance;
    private static final int PORT = 35000;

    private WebServer() {}

    public static WebServer getInstance() {
        if (instance == null) {
            instance = new WebServer();
        }
        return instance;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Listening on port " + PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleRequest(clientSocket);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
        }
    }

    private void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();
        String inputLine = in.readLine();
        if (inputLine != null) {
            String[] requestParts = inputLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];

            if (path.startsWith("/App/")) {
                handleServiceRequest(method, path, in, out);
            } else {
                handleStaticFileRequest(path, out);
            }
        }
    }

    private void handleServiceRequest(String method, String path, BufferedReader in, OutputStream out) throws IOException {
        String[] pathParts = path.split("\\?");
        String servicePath = pathParts[0];
        String queryString = pathParts.length > 1 ? pathParts[1] : "";

        // Leer headers y determinar el tamaño del cuerpo
        int contentLength = 0;
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        // Leer el cuerpo solo si es una solicitud POST
        StringBuilder requestBody = new StringBuilder();
        if ("POST".equals(method) && contentLength > 0) {
            char[] buffer = new char[contentLength];
            in.read(buffer, 0, contentLength);
            requestBody.append(buffer);
        }

        BiFunction<Request, Response, String> service = WebFramework.getRoutes().getOrDefault(method, new HashMap<>()).get(servicePath);
        if (service != null) {
            Request req = new Request(queryString, requestBody.toString());
            Response resp = new Response();
            String result = service.apply(req, resp);
            sendResponse(out, "200 OK", "text/plain", result.getBytes());
        } else {
            sendResponse(out, "404 Not Found", "text/plain", "Service not found".getBytes());
        }
    }

    private void handleStaticFileRequest(String path, OutputStream out) throws IOException {
        if ("/".equals(path)) {
            path = "/index.html";
        }

        String filePath = WebFramework.getStaticFilesLocation() + path;
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            String contentType = Files.probeContentType(Paths.get(filePath));
            byte[] fileContent = Files.readAllBytes(file.toPath());
            sendResponse(out, "200 OK", contentType, fileContent);
        } else {
            sendResponse(out, "404 Not Found", "text/plain", "File not found".getBytes());
        }
    }

    private void sendResponse(OutputStream out, String status, String contentType, byte[] body) throws IOException {
        PrintWriter writer = new PrintWriter(out, true);
        writer.println("HTTP/1.1 " + status);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + body.length);
        writer.println();
        writer.flush();
        out.write(body);
        out.flush();
    }
}

