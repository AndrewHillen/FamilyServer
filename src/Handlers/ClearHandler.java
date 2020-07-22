package Handlers;
import java.io.*;
import java.net.*;

import DataAccess.DBException;
import ReqRes.ClearResult;
import Services.ClearService;
import com.sun.net.httpserver.*;

public class ClearHandler extends Serializer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        boolean success = false;

        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {

                ClearService clearService = new ClearService();
                ClearResult result = clearService.clearDB();
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
