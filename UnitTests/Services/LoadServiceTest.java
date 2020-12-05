package Services;

import DataAccess.DBException;
import Model.Event;
import Model.Person;
import Model.User;
import ReqRes.LoadRequest;
import ReqRes.LoadResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest
{
    LoadService loadService;
    LoadRequest request;
    User[] goodUsers;
    Person[] goodPersons;
    Event[] goodEvents;

    @BeforeEach
    void setUp() throws DBException
    {
        loadService = new LoadService();
    }

    @AfterEach
    void tearDown() throws DBException
    {
        loadService.commit(false);
    }

    @Test
    @DisplayName("Good load")
    void load()
    {
        initializeLoadRequest();
        LoadResult result = loadService.load(request);

        assertEquals(true, result.isSuccess());
        String message = "Successfully added 4 users, 4 persons, and 4 events to the database.";
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Bad Loads")
    void badLoad()
    {
        initializeLoadRequest();
        request.setEvents(null);

        LoadResult result = loadService.load(request);

        assertEquals(false, result.isSuccess());
        assertNotEquals(-1, result.getMessage().toLowerCase().indexOf("error"));

        initializeLoadRequest();
        Person[] badPersons = request.getPersons();
        badPersons[2].setGender("x");
        request.setPersons(badPersons);

        result = loadService.load(request);

        assertEquals(false, result.isSuccess());
        assertNotEquals(-1, result.getMessage().toLowerCase().indexOf("error"));



    }

    void initializeLoadRequest()
    {
        goodEvents = new Event[4];
        goodPersons = new Person[4];
        goodUsers = new User[4];
        request = new LoadRequest();

        for(int i = 0; i < 4; i++)
        {
            Event event1 = new Event(
                    Integer.toString(i),
                    "username0",
                    "personID" + Integer.toString(i),
                    (float) (i + 1.5),
                    (float) (i + 1.5),
                    "country" + Integer.toString(i),
                    "city" + Integer.toString(i),
                    "eventType" + Integer.toString(i),
                    i
            );
            goodEvents[i] = event1;
        }

        for(int i = 0; i < 4; i++)
        {
            Person newPerson = new Person(
                    "id" + Integer.toString(i),
                    "username0",
                    "firstname" + Integer.toString(i),
                    "lastname" + Integer.toString(i),
                    "f",
                    "fatherid" + Integer.toString(i),
                    "motherid" + Integer.toString(i),
                    "spouseid" + Integer.toString(i)
            );
            goodPersons[i] = newPerson;
        }

        for(int i = 0; i < 4; i++)
        {
            User newUser = new User(
                    "username" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "a" + Integer.toString(i),
                    "m",
                    "a" + Integer.toString(i)
            );
            goodUsers[i] = newUser;
        }
        request.setUsers(goodUsers);
        request.setPersons(goodPersons);
        request.setEvents(goodEvents);


    }
}