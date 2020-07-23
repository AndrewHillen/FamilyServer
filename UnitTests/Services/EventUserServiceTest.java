package Services;

import DataAccess.AuthTokenAccess;
import DataAccess.DBException;
import DataAccess.EventAccess;
import Model.AuthToken;
import Model.Event;
import ReqRes.EventResult;
import ReqRes.EventUserResult;
import ReqRes.PersonResult;
import ReqRes.PersonUserResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventUserServiceTest
{
    private Event goodEvents[];
    private EventAccess eventDao;
    private Connection conn;
    private EventUserService eventUserService;
    private AuthTokenAccess authDao;
    private AuthToken token;
    private AuthToken token2;

    @BeforeEach
    void setUp() throws DBException
    {
        eventUserService = new EventUserService();
        conn = eventUserService.getConn();
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
        eventUserService.commit(false);
    }

    @Test
    @DisplayName("Good Find")
    void findEvents() throws DBException
    {
        EventUserResult result = eventUserService.findEvents("token");
        EventResult[] results = result.getData();
        for(int i = 0; i < results.length; i++)
        {
            assertEquals("username0", results[i].getAssociatedUsername());
        }
    }

    @Test
    @DisplayName("Bad Find")
    void badFindEvents() throws DBException
    {
        EventUserResult result = eventUserService.findEvents("token2");
        assertFalse(result.isSuccess());
    }
}