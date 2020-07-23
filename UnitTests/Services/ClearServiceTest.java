package Services;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest
{
    AuthToken goodTokens[];
    Event goodEvents[];
    Person goodPersons[];
    User goodUsers[];
    UserAccess userDao;
    PersonAccess personDao;
    EventAccess eventDao;
    AuthTokenAccess authDao;
    Connection conn;
    ClearService clearService;
    Database db;

    @BeforeEach
    void setUp() throws DBException
    {
        db = new Database();
        clearService = new ClearService();
        conn = db.getConnection();
        goodTokens = new AuthToken[4];
        goodEvents = new Event[4];
        goodPersons = new Person[4];
        goodUsers = new User[4];

        userDao = new UserAccess(conn);
        personDao = new PersonAccess(conn);
        eventDao = new EventAccess(conn);
        authDao = new AuthTokenAccess(conn);


        for(int i = 0; i < 4; i++)
        {
            AuthToken newToken = new AuthToken(
                    Integer.toString(i),
                    "username0",
                    null
            );
            goodTokens[i] = newToken;
            authDao.postAuthToken(goodTokens[i]);
        }
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
            personDao.postPerson(goodPersons[i]);
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
            userDao.postUser(goodUsers[i]);
        }
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown()
    {

    }

    @Test
    @DisplayName("Clear")
    void clearDB() throws DBException
    {
        clearService.clearDB();
        assertNull(userDao.findUserInfoByUsername("username0"));
        assertNull(personDao.findPersonsByUsername("username0"));
        assertNull(eventDao.findEventsByUsername("username0"));
        assertNull(authDao.findAuthTokenByToken("0"));
    }
}