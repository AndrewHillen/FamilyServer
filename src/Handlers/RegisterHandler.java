package Handlers;
import java.io.*;
import java.net.*;

import DataAccess.DBException;
import ReqRes.LoadRequest;
import ReqRes.LoginResult;
import ReqRes.RegisterRequest;
import ReqRes.RegisterResult;
import com.sun.net.httpserver.*;
import Services.RegisterService;





public class RegisterHandler extends Serializer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;

        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {

                RegisterRequest request = new RegisterRequest();
                InputStream requestBody = exchange.getRequestBody();
                try
                {
                    String jsonString = readRequestBody(requestBody);
                    request = (RegisterRequest) Serialize(jsonString, request);
                }
                catch(com.google.gson.JsonSyntaxException ex)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                    return;
                }



                RegisterService regService = new RegisterService();

                RegisterResult result = regService.register(request);
                if(result.isSuccess())
                {
                    regService.commit(true);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

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
