package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Controller implements HttpHandler
{

    /**
     * Handle the given request and generate an appropriate response. See {@link HttpExchange} for a description of the
     * steps involved in handling an exchange.
     *
     * @param exchange
     *         the exchange containing the request from the client and used to send the response
     * @throws NullPointerException
     *         if exchange is {@code null}
     * @throws IOException
     *         if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        if("GET".equals(exchange.getRequestMethod())){
            String response = "Hi from api!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try(OutputStream os = exchange.getResponseBody()){
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // not allowed
        }
    }
}
