package Services;

import DataAccess.AuthTokenAccess;
import DataAccess.DBException;
import DataAccess.Database;
import DataAccess.PersonAccess;
import Model.AuthToken;
import Model.Person;
import ReqRes.PersonRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest
{
    private Person goodPersons[];
    private PersonAccess personDao;
    private Connection conn;
    private PersonService personService;
    private AuthTokenAccess authDao;
    private AuthToken token;
    private AuthToken token2;

    @BeforeEach
    void setUp() throws DBException
    {
        personService = new PersonService();
        conn = personService.getConn();
        personDao = new PersonAccess(conn);
        authDao = new AuthTokenAccess(conn);

        goodPersons = new Person[4];

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
        token = new AuthToken("token", "username0", null);
        token2 = new AuthToken("token2", "username", null);
        authDao.postAuthToken(token);
        authDao.postAuthToken(token2);
    }

    @AfterEach
    void tearDown() throws DBException
    {
        personService.commit(false);
    }

    @Test
    @DisplayName("Good find")
    void searchPerson() throws DBException
    {
        for(int i = 0; i < 4; i++)
        {
            PersonRequest request = new PersonRequest(goodPersons[i].getId(), "token");
            assertEquals(goodPersons[i].getId(), personService.searchPerson(request).getPersonID());
        }
    }

    @Test
    @DisplayName("Bad find")
    void badSearchPerson() throws DBException
    {
        for(int i = 0; i < 4; i++)
        {
            PersonRequest request = new PersonRequest(goodPersons[i].getId(), "badToken");
            assertFalse(personService.searchPerson(request).isSuccess());
        }
        for(int i = 0; i < 4; i++)
        {
            PersonRequest request = new PersonRequest("badID", "token");
            assertFalse(personService.searchPerson(request).isSuccess());
        }
        for(int i = 0; i < 4; i++)
        {
            PersonRequest request = new PersonRequest(goodPersons[i].getId(), "token2");
            assertFalse(personService.searchPerson(request).isSuccess());
        }
    }

}