package Handlers;
import java.io.*;
import java.net.*;

import DataAccess.DBException;
import ReqRes.FillRequest;
import ReqRes.FillResult;
import Services.FillService;
import com.sun.net.httpserver.*;

public class FillHandler extends Serializer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        boolean success = false;
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                String username;
                int generations;
                String urlPath = exchange.getRequestURI().toString();
                String userInput = urlPath.substring(6);
                int generationCheck = userInput.indexOf('/');
                if(generationCheck == -1)
                {
                    username = userInput;
                    generations = 4;
                }
                else
                {
                    String generationString = userInput.substring(generationCheck + 1);
                    username = userInput.substring(0, generationCheck);
                    try
                    {
                        generations = Integer.parseInt(generationString);
                    }
                    catch(NumberFormatException ex)
                    {
                        generations = -2;
                    }

                }
                FillRequest request = new FillRequest(username, generations);
                FillService fillService = new FillService();

                FillResult result = fillService.fill(request);

                if(result.isSuccess())
                {
                    fillService.commit(true);
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
