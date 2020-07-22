package DataAccess;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonAccessTest
{

    private Database db;
    private Person[] goodPersons;
    private Person[] badPersons;
    private PersonAccess personDao;
    //Multiple person testing
    private Person[] goodPersons2;

    @BeforeEach
    public void setup() throws DBException
    {
        db = new Database();
        Connection conn = db.getConnection();
        goodPersons = new Person[4];
        badPersons = new Person[6];
        personDao = new PersonAccess(conn);
        for(int i = 0; i < 3; i++)
        {
            Person newPerson = new Person(
                    "id" + Integer.toString(i),
                    "username" + Integer.toString(i),
                    "firstname" + Integer.toString(i),
                    "lastname" + Integer.toString(i),
                    "f",
                    "fatherid" + Integer.toString(i),
                    "motherid" + Integer.toString(i),
                    "spouseid" + Integer.toString(i)
            );
            goodPersons[i] = newPerson;
        }
        Person newPerson = newPerson = new Person(
                "id3",
                "username4",
                "firstname4",
                "lastname4",
                "f",
                null,
                null,
                null
        );

        goodPersons[3] = newPerson;

        newPerson = new Person(
                null,
                "username",
                "firstname",
                "lastname",
                "f",
                "fatherid",
                "motherid",
                "spouseid"
        );

        badPersons[0] = newPerson;

        newPerson = new Person(
                "badid1",
                null,
                "firstname",
                "lastname",
                "f",
                "fatherid",
                "motherid",
                "spouseid"
        );

        badPersons[1] = newPerson;

        newPerson = new Person(
                "badid2",
                "username",
                null,
                "lastname",
                "f",
                "fatherid",
                "motherid",
                "spouseid"
        );

        badPersons[2] = newPerson;

        newPerson = new Person(
                "badid3",
                "username",
                "firstname",
                null,
                "f",
                "fatherid",
                "motherid",
                "spouseid"
        );

        badPersons[3] = newPerson;

        newPerson = new Person(
                "badid4",
                "username",
                "firstname",
                "lastname",
                "x",
                "fatherid",
                "motherid",
                "spouseid"
        );

        badPersons[4] = newPerson;

        newPerson = new Person(
                "id0",
                "username",
                "firstname",
                "lastname",
                "f",
                "fatherid",
                "motherid",
                "spouseid"
        );

        badPersons[5] = newPerson;

    }

    @AfterEach
    public void teardown() throws DBException
    {
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Post Person")
    void testPostPerson() throws DBException
    {
        for(int i = 0; i < 4; i++)
        {
            personDao.postPerson(goodPersons[i]);
        }

        for(int i = 0; i < 4; i++)
        {
            assertEquals(goodPersons[i], personDao.findPersonByID(goodPersons[i].getId()));
        }
    }

    @Test
    @DisplayName("Post Bad Person")
    void badTestPostPerson() throws DBException
    {
        Person newPerson = new Person(
                "id0",
                "username",
                "firstname",
                "lastname",
                "f",
                "fatherid",
                "motherid",
                "spouseid"
        );

        personDao.postPerson(newPerson);
        for(int i = 0; i < 6; i++)
        {
            int finalI = i;
            assertThrows(DBException.class, () -> personDao.postPerson(badPersons[finalI]));
        }
    }

    @Test
    @DisplayName("Find Person")
    void testFindPersonByID() throws DBException
    {
        Person newPerson = newPerson = new Person(
                "id",
                "username",
                "firstname",
                "lastname",
                "f",
                "fatherid",
                "motherid",
                "spouseid"
        );
        personDao.postPerson(newPerson);

        assertEquals(newPerson, personDao.findPersonByID(newPerson.getId()));
    }

    @Test
    @DisplayName("Find Bad Person")
    void badTestFindPersonByID() throws DBException
    {

        for(int i = 0; i < 4; i++)
        {
            personDao.postPerson(goodPersons[i]);
        }

        assertNull(personDao.findPersonByID("BadID"));

    }

    @Test
    @DisplayName("Find People")
    void testFindPersonByUsername() throws DBException
    {
        //Initialize goodPersons for this test
        multiPerson();
        for(int i = 0; i < goodPersons.length; i++)
        {
            personDao.postPerson(goodPersons[i]);
            personDao.postPerson(goodPersons2[i]);
        }

        assertArrayEquals(goodPersons, personDao.findPersonsByUsername("username"));
        assertArrayEquals(goodPersons2, personDao.findPersonsByUsername("username2"));

    }

    @Test
    @DisplayName("Find Bad People")
    void badTestFindPersonByUsername() throws DBException
    {

        //Initialize goodPersons for this test
        multiPerson();
        for(int i = 0; i < goodPersons.length; i++)
        {
            personDao.postPerson(goodPersons[i]);
            personDao.postPerson(goodPersons2[i]);
        }


        assertNull(personDao.findPersonsByUsername("notAUsername"));

    }


    @Test
    @DisplayName("Clear Person Tables")
    void testClear() throws DBException
    {
        for(int i = 0; i < 4; i++)
        {
            personDao.postPerson(goodPersons[i]);
        }

        personDao.clear();

        for(int i = 0; i < 4; i++)
        {
            assertNull(personDao.findPersonByID(goodPersons[i].getId()));
        }

    }

    @Test
    @DisplayName("Delete By Username")
    void testDeleteByUsername() throws DBException
    {
        multiPerson();
        for(int i = 0; i < goodPersons.length; i++)
        {
            personDao.postPerson(goodPersons[i]);
            personDao.postPerson(goodPersons2[i]);
        }

        assertArrayEquals(goodPersons, personDao.findPersonsByUsername("username"));
        assertArrayEquals(goodPersons2, personDao.findPersonsByUsername("username2"));

        personDao.deleteByUsername("username");

        assertNull(personDao.findPersonsByUsername("username"));
        assertArrayEquals(goodPersons2, personDao.findPersonsByUsername("username2"));

        personDao.deleteByUsername("username2");
        assertNull(personDao.findPersonsByUsername("username2"));


    }

    @Test
    @DisplayName("Update Family")
    void testUpdateFamily() throws DBException
    {
        personDao.postPerson(goodPersons[3]);
        String[] familyIDs = new String[3];
        familyIDs[0] = "fatherID";
        familyIDs[1] = "motherID";
        familyIDs[2] = "spouseID";

        personDao.addFamilyByID(goodPersons[3].getId(), familyIDs);

        goodPersons[3].setFatherID("fatherID");
        goodPersons[3].setMotherID("motherID");
        goodPersons[3].setSpouseID("spouseID");

        assertEquals(goodPersons[3], personDao.findPersonByID(goodPersons[3].getId()));


    }

    private void multiPerson()
    {
        goodPersons = new Person[4];
        goodPersons2 = new Person[4];
        for(int i = 0; i < 4; i++)
        {
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
            Person newPerson2 = new Person(
                    "id" + Integer.toString(i + 100),
                    "username2",
                    "firstname" + Integer.toString(i),
                    "lastname" + Integer.toString(i),
                    "f",
                    "fatherid" + Integer.toString(i),
                    "motherid" + Integer.toString(i),
                    "spouseid" + Integer.toString(i)
            );
            goodPersons[i] = newPerson;
            goodPersons2[i] = newPerson2;

        }
    }
}