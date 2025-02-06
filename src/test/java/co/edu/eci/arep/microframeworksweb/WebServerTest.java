package co.edu.eci.arep.microframeworksweb;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import java.net.Socket;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebServerTest {

    private static final String SERVER_URL = "http://localhost:35000/App/hello";

    @Test
    void testHandleRequest() throws IOException {
        // Simula un socket con una petición GET /hello
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GET /hello HTTP/1.1\n\n".getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        WebServer.getInstance().handleRequest(mockSocket);

        verify(mockSocket, atLeastOnce()).getInputStream();
        verify(mockSocket, atLeastOnce()).getOutputStream();
    }

    @Test
    void testGetRequest() throws IOException {
        URL url = new URL(SERVER_URL + "?name=Maria");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        assertEquals(200, conn.getResponseCode());
        assertTrue(new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .contains("Hello Maria!"));
    }

    @Test
    void testPostRequest() throws IOException {
        URL url = new URL("http://localhost:35000/App/submit");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write("data=Test".getBytes(StandardCharsets.UTF_8));

        assertEquals(200, conn.getResponseCode());
        assertTrue(new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .contains("Received: data=Test"));
    }
}



