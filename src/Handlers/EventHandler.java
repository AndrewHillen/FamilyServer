package Handlers;
import java.io.*;
import java.net.*;

import DataAccess.DBException;
import ReqRes.*;
import Services.EventService;
import Services.EventUserService;
import Services.PersonService;
import Services.PersonUserService;
import com.sun.net.httpserver.*;

public class EventHandler extends Serializer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {


        boolean success = false;

        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("get"))
            {

                Headers requestHeaders = exchange.getRequestHeaders();
                if(requestHeaders.containsKey("Authorization"))
                {
                    String token = requestHeaders.getFirst("Authorization");
                    String urlPath = exchange.getRequestURI().toString();

                    if(urlPath.equals("/event"))
                    {
                        EventUserService eventUserService = new EventUserService();
                        EventUserResult result = eventUserService.findEvents(token);
                        eventUserService.commit(false);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        String resultString = DeSerialize(result);

                        OutputStream responseBody = exchange.getResponseBody();
                        responseWriter(resultString, responseBody);
                        responseBody.close();
                    }
                    else
                    {
                        String eventID = urlPath.substring(7);
                        EventRequest request = new EventRequest(eventID, token);
                        EventService eventService = new EventService();
                        EventResult result = eventService.searchEvents(request);
                        eventService.commit(false);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        String resultString = DeSerialize(result);

                        OutputStream responseBody = exchange.getResponseBody();
                        responseWriter(resultString, responseBody);
                        responseBody.close();

                    }
                    success = true;


                }
                //Do a bunch of other stuff before this
                OutputStream respBody = exchange.getResponseBody();
                responseWriter("", respBody);
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
