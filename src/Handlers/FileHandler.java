package Handlers;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;


import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;

        try
        {
            if(!exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getRequestBody().close();
                return;
            }

            String urlPath = exchange.getRequestURI().toString();

            if( !( urlPath.equals("/") || urlPath.equals("/favicon.ico") || urlPath.equals("/css/main.css")) )
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
                exchange.getRequestBody().close();
                return;
            }

            StringBuilder filePath = new StringBuilder(Paths.get("").toAbsolutePath().toString());

            if(urlPath.equals("/"))
            {
                filePath.append("/web/index.html");
            }
            else
            {
                filePath.append("/web" + urlPath);
            }

            File returnFile = new File(filePath.toString());

            if(!returnFile.exists())
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                exchange.getRequestBody().close();
                return;
            }

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(returnFile.toPath(), respBody);
            respBody.close();

        }
        catch(IOException ex)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
