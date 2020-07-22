package Handlers;
import DataAccess.DBException;
import com.google.gson.*;

import java.io.*;

public class Serializer
{

    Gson gson;
    public Serializer()
    {
        gson = new Gson();
    }

    //TODO: Catch com.google.gson.JsonSyntaxException in handlers. return RequestFailure
    public Object Serialize(String jsonString, Object o)
    {

        o = gson.fromJson(jsonString, o.getClass());


        return o;
    }

    public String DeSerialize(Object o)
    {
        String jsonString = gson.toJson(o);

        return jsonString;
    }

    public String readRequestBody(InputStream in) throws IOException
    {
        StringBuilder returnString = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(in);
        char[] buffer = new char[1024];
        int len;
        while((len = reader.read(buffer)) > 0)
        {
            returnString.append(buffer, 0, len);
        }
        return returnString.toString();
    }

    public void responseWriter(String response, OutputStream out) throws IOException
    {
        OutputStreamWriter write = new OutputStreamWriter(out);
        write.write(response);
        write.flush();
    }

    public String exceptionErrorResponse(String message)
    {
        String jsonString = String.format("{" +
                "\"message\":\"%s\"," +
                "\"success\":false" +
                "}", message);

        return jsonString;
    }
}
