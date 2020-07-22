package Handlers;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class EventHandler extends Serializer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {



        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("get"))
            {



                //Do a bunch of other stuff before this
                OutputStream respBody = exchange.getResponseBody();
                responseWriter("", respBody);
            }
        }
        catch(IOException ex)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }

    }
}