package DataAccess;

import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventAccessTest
{
    private Database db;
    private EventAccess eventDao;
    private Event[] goodEvents;
    private Event[] badEvents;
    private Event[] sharedUsernames1;
    private Event[] sharedUsernames2;


    @BeforeEach
    void setUp() throws DBException
    {
        db = new Database();
        Connection conn = db.getConnection();
        eventDao = new EventAccess(conn);
        ArrayBuilders();
    }

    @AfterEach
    void tearDown() throws DBException
    {
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Post Good Event")
    void postEvent() throws DBException
    {
        for(int i = 0; i < goodEvents.length; i++)
        {
            eventDao.postEvent(goodEvents[i]);
            assertEquals(goodEvents[i], eventDao.findEventByID(goodEvents[i].getId()));
        }
    }

    @Test
    @DisplayName("Post Bad Event")
    void postBadEvent() throws DBException
    {
        eventDao.postEvent(goodEvents[0]);

        for(int i = 0; i < badEvents.length; i++)
        {
            int finalI = i;
            assertThrows(DBException.class, () -> eventDao.postEvent(badEvents[finalI]));
        }
    }

    @Test
    @DisplayName("Delete By Username")
    void deleteByUsername() throws DBException
    {
        for(int i = 0; i < sharedUsernames1.length; i++)
        {
            eventDao.postEvent(sharedUsernames1[i]);
            eventDao.postEvent(sharedUsernames2[i]);
        }

        assertArrayEquals(sharedUsernames1, eventDao.findEventsByUsername("username1"));
        assertArrayEquals(sharedUsernames2, eventDao.findEventsByUsername("username2"));

        eventDao.deleteByUsername("username1");

        assertNull(eventDao.findEventsByUsername("username1"));
        assertArrayEquals(sharedUsernames2, eventDao.findEventsByUsername("username2"));

        eventDao.deleteByUsername("username2");

        assertNull(eventDao.findEventsByUsername("username2"));

    }

    @Test
    @DisplayName("Find By ID")
    void findEventByID() throws DBException
    {
        Event event1 = new Event(
                "ID",
                "username",
                "personID",
                (float) (1.5),
                (float) (1.5),
                "country",
                "city",
                "eventType",
                100
        );

        eventDao.postEvent(event1);
        assertEquals(event1, eventDao.findEventByID("ID"));
    }

    @Test
    @DisplayName("Bad Find By ID")
    void badFindEventByID() throws DBException
    {
        Event event1 = new Event(
                "ID",
                "username",
                "personID",
                (float) (1.5),
                (float) (1.5),
                "country",
                "city",
                "eventType",
                100
        );

        eventDao.postEvent(event1);
        assertNull(eventDao.findEventByID("BadID"));
    }

    @Test
    @DisplayName("Find By Username")
    void findEventsByUsername() throws DBException
    {
        for(int i = 0; i < sharedUsernames1.length; i++)
        {
            eventDao.postEvent(sharedUsernames1[i]);
            eventDao.postEvent(sharedUsernames2[i]);
        }

        assertArrayEquals(sharedUsernames1, eventDao.findEventsByUsername("username1"));
        assertArrayEquals(sharedUsernames2, eventDao.findEventsByUsername("username2"));
    }

    @Test
    @DisplayName("Bad Find by Username")
    void badFindEventsByUsername() throws DBException
    {
        for(int i = 0; i < sharedUsernames1.length; i++)
        {
            eventDao.postEvent(sharedUsernames1[i]);
            eventDao.postEvent(sharedUsernames2[i]);
        }

        assertNull(eventDao.findEventsByUsername("username3"));
    }

    @Test
    void clear() throws DBException
    {
        for(int i = 0; i < 4; i++)
        {
            eventDao.postEvent(goodEvents[i]);
            eventDao.postEvent(sharedUsernames1[i]);
            eventDao.postEvent(sharedUsernames2[i]);
        }
        eventDao.clear();
        for(int i = 0; i < 4; i++)
        {
            assertNull(eventDao.findEventByID(goodEvents[i].getId()));
            assertNull(eventDao.findEventByID(sharedUsernames1[i].getId()));
            assertNull(eventDao.findEventByID(sharedUsernames2[i].getId()));
        }
    }

    private void ArrayBuilders()
    {
        goodEvents = new Event[4];
        badEvents = new Event[10];
        sharedUsernames1 = new Event[4];
        sharedUsernames2 = new Event[4];

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

            Event event2 = new Event(
                    Integer.toString(i) + 100,
                    "username1",
                    "personID" + Integer.toString(i+ 100),
                    (float) (i + 1.5),
                    (float) (i + 1.5),
                    "country" + Integer.toString(i),
                    "city" + Integer.toString(i),
                    "eventType" + Integer.toString(i),
                    i
            );
            sharedUsernames1[i] = event2;

            Event event3 = new Event(
                    Integer.toString(i) + 200,
                    "username2",
                    "personID" + Integer.toString(i+ 200),
                    (float) (i + 1.5),
                    (float) (i + 1.5),
                    "country" + Integer.toString(i),
                    "city" + Integer.toString(i),
                    "eventType" + Integer.toString(i),
                    i
            );
            sharedUsernames2[i] = event3;
        }

        Event badEvent = new Event(
                null,
                "badUsername",
                "badPersonID",
                (float) (1.5),
                (float) (1.5),
                "badCountry",
                "badCity",
                "badEventType",
                2020
        );
        badEvents[0] = badEvent;

        badEvent = new Event(
                "badID",
                null,
                "badPersonID",
                (float) (1.5),
                (float) (1.5),
                "badCountry",
                "badCity",
                "badEventType",
                2020
        );
        badEvents[1] = badEvent;

        badEvent = new Event(
                "badID",
                "badUsername",
                null,
                (float) (1.5),
                (float) (1.5),
                "badCountry",
                "badCity",
                "badEventType",
                2020
        );
        badEvents[2] = badEvent;

        badEvent = new Event(
                "badID",
                "badUsername",
                "badPersonID",
                null,
                (float) (1.5),
                "badCountry",
                "badCity",
                "badEventType",
                2020
        );
        badEvents[3] = badEvent;

        badEvent = new Event(
                "badID",
                "badUsername",
                "badPersonID",
                (float) (1.5),
                null,
                "badCountry",
                "badCity",
                "badEventType",
                2020
        );
        badEvents[4] = badEvent;

        badEvent = new Event(
                "badID",
                "badUsername",
                "badPersonID",
                (float) (1.5),
                (float) (1.5),
                null,
                "badCity",
                "badEventType",
                2020
        );
        badEvents[5] = badEvent;

        badEvent = new Event(
                "badID",
                "badUsername",
                "badPersonID",
                (float) (1.5),
                (float) (1.5),
                "badCountry",
                null,
                "badEventType",
                2020
        );
        badEvents[6] = badEvent;

        badEvent = new Event(
                "badID",
                "badUsername",
                "badPersonID",
                (float) (1.5),
                (float) (1.5),
                "badCountry",
                "badCity",
                null,
                2020
        );
        badEvents[7] = badEvent;

        badEvent = new Event(
                "badID",
                "badUsername",
                "badPersonID",
                (float) (1.5),
                (float) (1.5),
                "badCountry",
                "badCity",
                "badEventType",
                null
        );
        badEvents[8] = badEvent;

        badEvent = new Event(
                "0",
                "badUsername",
                "badPersonID",
                (float) (1.5),
                (float) (1.5),
                "badCountry",
                "badCity",
                "badEventType",
                2020
        );
        badEvents[9] = badEvent;


    }
}