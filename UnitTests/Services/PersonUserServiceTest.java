package Services;

import DataAccess.AuthTokenAccess;
import DataAccess.DBException;
import DataAccess.PersonAccess;
import Model.AuthToken;
import Model.Person;
import ReqRes.PersonResult;
import ReqRes.PersonUserResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonUserServiceTest
{

    private Person goodPersons[];
    private PersonAccess personDao;
    private Connection conn;
    private PersonUserService personUserService;
    private AuthTokenAccess authDao;
    private AuthToken token;
    private AuthToken token2;

    @BeforeEach
    void setUp() throws DBException
    {
        personUserService = new PersonUserService();
        conn = personUserService.getConn();
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
        personUserService.commit(false);
    }

    @Test
    @DisplayName("Good Find")
    void findFamily() throws DBException
    {
        PersonUserResult result = personUserService.findFamily("token");
        PersonResult[] results = result.getData();
        for(int i = 0; i < results.length; i++)
        {
            assertEquals("username0", results[i].getAssociatedUsername());
        }
    }

    @Test
    @DisplayName("Bad Find")
    void badFindFamily() throws DBException
    {
        PersonUserResult result = personUserService.findFamily("token2");
        assertFalse(result.isSuccess());
    }
}