package Services;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import ReqRes.EventRequest;
import ReqRes.PersonRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest
{
    private Event goodEvents[];
    private EventAccess eventDao;
    private Connection conn;
    private EventService eventService;
    private AuthTokenAccess authDao;
    private AuthToken token;
    private AuthToken token2;

    @BeforeEach
    void setUp() throws DBException
    {
        eventService = new EventService();
        conn = eventService.getConn();
        goodEvents = new Event[4];
        eventDao = new EventAccess(conn);
        authDao = new AuthTokenAccess(conn);

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
            eventDao.postEvent(goodEvents[i]);
        }
        token = new AuthToken("token", "username0", null);
        token2 = new AuthToken("token2", "username", null);
        authDao.postAuthToken(token);
        authDao.postAuthToken(token2);
    }

    @AfterEach
    void tearDown() throws DBException
    {
        eventService.commit(false);
    }

    @Test
    @DisplayName("Good Find")
    void searchEvents() throws DBException
    {
        for(int i = 0; i < 4; i++)
        {
            EventRequest request = new EventRequest(goodEvents[i].getId(), "token");
            assertEquals(goodEvents[i].getId(), eventService.searchEvents(request).getEventID());
        }
    }
    @Test
    @DisplayName("Bad Find")
    void badSearchEvents() throws DBException
    {
        for(int i = 0; i < 4; i++)
        {
            EventRequest request = new EventRequest(goodEvents[i].getId(), "badtoken");
            assertFalse(eventService.searchEvents(request).isSuccess());
        }

        for(int i = 0; i < 4; i++)
        {
            EventRequest request = new EventRequest(goodEvents[i].getId(), "token2");
            assertFalse(eventService.searchEvents(request).isSuccess());        }

        for(int i = 0; i < 4; i++)
        {
            EventRequest request = new EventRequest("badID", "token");
            assertFalse(eventService.searchEvents(request).isSuccess());        }
    }
}