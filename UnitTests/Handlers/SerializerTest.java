package Handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ReqRes.*;
import Model.Event;
import Model.User;
import Model.Person;

import static org.junit.jupiter.api.Assertions.*;

class SerializerTest
{
    public Serializer s;

    @BeforeEach
    public void setup()
    {
        s = new Serializer();
    }

    /*
    TODO: Serialize for requests only. Deserialize Response only
    TODO: test com.google.gson.JsonSyntaxExceptions in individual handler tests


     */



    //Register testing----------------------------------------------------------------------
    @Test
    @DisplayName("Serialize RegisterRequest test")
    void serializeRegisterRequest()
    {
        RegisterRequest r = new RegisterRequest();
        String jsonString = "{\n" +
                "    \"userName\": \"susan\",        // Non-empty string \n" +
                "    \"password\": \"mysecret\",    // Non-empty string\n" +
                "    \"email\": \"susan@gmail.com\",    // Non-empty string        \n" +
                "    \"firstName\": \"Susan\",        // Non-empty string\n" +
                "    \"lastName\": \"Ellis\",        // Non-empty string\n" +
                " \"gender\": \"f\"            // “f” or “m”\n" +
                "}\n";
        r = (RegisterRequest) s.Serialize(jsonString, r);
        assertEquals("susan", r.getUserName());
        assertEquals("mysecret", r.getPassword());
        assertEquals("susan@gmail.com", r.getEmail());
        assertEquals("Susan", r.getFirstName());
        assertEquals("Ellis", r.getLastName());
        assertEquals("f", r.getGender());
    }

    @Test
    @DisplayName("Serialize Incomplete RegisterRequest test")
    void incompleteSerializeRegisterRequest()
    {
        RegisterRequest r = new RegisterRequest();
        //missingLastName
        String jsonString = "{" +
                "\"userName\":\"susan\"," +
                "\"password\":\"mysecret\"," +
                "\"email\":\"susan@gmail.com\"," +
                "\"firstName\":\"Susan\"," +
                "\"gender\":\"f\"" +
                "}";
        r = (RegisterRequest) s.Serialize(jsonString, r);
        assertEquals("susan", r.getUserName());
        assertEquals("mysecret", r.getPassword());
        assertEquals("susan@gmail.com", r.getEmail());
        assertEquals("Susan", r.getFirstName());
        assertNull(r.getLastName());
        assertEquals("f", r.getGender());
    }

    //TODO: Ask TAs about specific return request results. "true" or true
    @Test
    @DisplayName("DeSerialize RegisterResult test")
    void deSerializeRegisterResult()
    {
        RegisterResult r = new RegisterResult();
        r.setAuthToken("theToken");
        r.setUserName("theUsername");
        r.setPersonID("theID");
        r.setSuccess(true);
        String jsonString = "{" +
                "\"authToken\":\"theToken\"," +
                "\"userName\":\"theUsername\"," +
                "\"personID\":\"theID\"," +
                "\"success\":true" +
                "}";

        String returnString = s.DeSerialize(r);
        assertEquals(jsonString, returnString, "Success scenario failed");

        r = new RegisterResult();
        r.setMessage("Screwed up m8");
        r.setSuccess(false);

        returnString = s.DeSerialize(r);

        jsonString = "{" +
                "\"message\":\"Screwed up m8\"," +
                "\"success\":false" +
                "}";
        assertEquals(jsonString, returnString, "Failure scenario failed");


    }

//TODO: Ask TAs if they will pass in improperly formatted JSON EX:comma after last element, etc.



    //Login testing --------------------------------------------------------------------------------
    @Test
    @DisplayName("Serialize LoginRequest test")
    void serializeLoginRequest()
    {
        LoginRequest l = new LoginRequest();
        String jsonString = "{" +
                "\"userName\":\"theUsername\"," +
                "\"password\":\"thePassword\"" +
                "}";
        l = (LoginRequest) s.Serialize(jsonString, l);
        assertEquals("theUsername", l.getUserName());
        assertEquals("thePassword", l.getPassword());

        jsonString = "{}";
        l = (LoginRequest) s.Serialize(jsonString, l);
        assertNull(l.getUserName());
        assertNull(l.getPassword());


    }

    @Test
    @DisplayName("Deserialize LoginResult")
    void deSerializeLoginResult()
    {
        LoginResult l = new LoginResult();
        l.setAuthToken("theToken");
        l.setUserName("theUsername");
        l.setPersonID("theID");
        l.setSuccess(true);

        String jsonString = "{" +
                "\"authToken\":\"theToken\"," +
                "\"userName\":\"theUsername\"," +
                "\"personID\":\"theID\"," +
                "\"success\":true" +
                "}";
        String returnString = s.DeSerialize(l);

        assertEquals(jsonString,returnString);

        l = new LoginResult();
        l.setMessage("Failed");
        l.setSuccess(false);

        jsonString = "{" +
                "\"message\":\"Failed\"," +
                "\"success\":false" +
                "}";

        returnString = s.DeSerialize(l);

        assertEquals(jsonString, returnString);
    }

    //Clear Testing ---------------------------------------------------------------------------

    @Test
    @DisplayName("Deserialize Clear Result")
    void deSerializeClearResult()
    {
        ClearResult c = new ClearResult();
        c.setMessage("Message");
        c.setSuccess(false);

        String returnString = s.DeSerialize(c);

        String jsonString = "{" +
                "\"message\":\"Message\"," +
                "\"success\":false" +
                "}";

        assertEquals(jsonString, returnString);

    }

    //Fill Testing ------------------------------------------------

    @Test
    @DisplayName("Deserialize fill result")
    void deSerializeFillResult()
    {
        FillResult f = new FillResult();
        f.setMessage("Message");
        f.setSuccess(false);

        String returnString = s.DeSerialize(f);

        String jsonString = "{" +
                "\"message\":\"Message\"," +
                "\"success\":false" +
                "}";

        assertEquals(jsonString, returnString);
    }

    //Load Testing ---------------------------------------------------------------------------------

    @Test
    @DisplayName("Serialize Load Request")
    void serializeLoadRequest()
    {
        Event goodEvents[] = new Event[4];
        Person goodPersons[] = new Person[4];
        User goodUsers[] = new User[4];
        for(int i = 0; i < 4; i++)
        {
            Event event1 = new Event(
                    Integer.toString(i),
                    "goodEventsUsername" + Integer.toString(i),
                    "personID" + Integer.toString(i),
                    (float) (i + 1.5),
                    (float) (i + 1.5),
                    "country" + Integer.toString(i),
                    "city" + Integer.toString(i),
                    "eventType" + Integer.toString(i),
                    i
            );
            goodEvents[i] = event1;

            Person newPerson = new Person(
                    "id" + Integer.toString(i),
                    "username",
                    "firstname" + Integer.toString(i),
                    "lastname" + Integer.toString(i),
                    "f",
                    "fatherid" + Integer.toString(i),
                    "motherid" + Integer.toString(i),
                    "spouseid" + Integer.toString(i)
            );
            goodPersons[i] = newPerson;

            User newUser = new User(
                    "a" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "m",
                    "a" + Integer.toString(i)
            );
            goodUsers[i] = newUser;
        }

        LoadRequest l = new LoadRequest();
        l.setEvents(goodEvents);
        l.setPersons(goodPersons);
        l.setUsers(goodUsers);

        String jsonString = s.DeSerialize(l);

        LoadRequest returnLoad = (LoadRequest) s.Serialize(jsonString, l);

        assertEquals(l, returnLoad);
    }

    //Person, Event, and arrays are serialized normally. No need for
    //further testing

    


}