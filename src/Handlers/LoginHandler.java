package Handlers;
import java.io.*;
import java.net.*;

import DataAccess.DBException;
import ReqRes.LoginRequest;
import ReqRes.LoginResult;
import Services.LoginService;
import com.sun.net.httpserver.*;


public class LoginHandler extends Serializer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        boolean success = false;
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                LoginRequest request = new LoginRequest();
                InputStream requestBody = exchange.getRequestBody();

                try
                {
                    String jsonString = readRequestBody(requestBody);
                    request = (LoginRequest) Serialize(jsonString, request);
                }
                catch(com.google.gson.JsonSyntaxException ex)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                    return;
                }

                LoginService loginService = new LoginService();

                LoginResult result = loginService.loginService(request);

                if(result.isSuccess())
                {
                    loginService.commit(true);
                }
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                //Turn it into JSON

                String resultString = DeSerialize(result);
                //

                OutputStream responseBody = exchange.getResponseBody();
                responseWriter(resultString, responseBody);
                responseBody.close();
                success = true;

            }
            if (!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException ex)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
        catch(DBException ex)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
