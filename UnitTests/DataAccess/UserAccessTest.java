package DataAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Model.User;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserAccessTest
{
    private Database db;
    private User[] goodUsers;
    private User[] badUsers;
    private UserAccess userDao;

    @BeforeEach
    public void setup() throws DBException
    {
        db = new Database();
        Connection conn = db.getConnection();

        goodUsers = new User[3];
        badUsers = new User[8];
        userDao = new UserAccess(conn);

        for(int i = 0; i < 3; i++)
        {
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

        User newUser = new User(
                null,
                "a",
                "a",
                "a",
                "a",
                "m",
                "a"
        );
        badUsers[0] = newUser;

        newUser = new User(
                "b",
                null,
                "a",
                "a",
                "a",
                "m",
                "a"
        );

        badUsers[1] = newUser;

        newUser = new User(
                "c",
                "a",
                null,
                "a",
                "a",
                "m",
                "a"
        );

        badUsers[2] = newUser;

        newUser = new User(
                "d",
                "a",
                "a",
                null,
                "a",
                "m",
                "a"
        );

        badUsers[3] = newUser;

        newUser = new User(
                "e",
                "a",
                "a",
                "a",
                null,
                "m",
                "a"
        );

        badUsers[4] = newUser;

        //Bad Gender
        newUser = new User(
                "f",
                "a",
                "a",
                "a",
                "a",
                "a",
                "a"
        );

        badUsers[5] = newUser;

        newUser = new User(
                "g",
                "a",
                "a",
                "a",
                "a",
                "m",
                null
        );

        badUsers[6] = newUser;

        //Non unique name
        newUser = new User(
                "a0",
                "a",
                "a",
                "a",
                "a",
                "m",
                "a"
        );

        badUsers[7] = newUser;
    }

    @AfterEach
    public void teardown() throws DBException
    {
        db.closeConnection(false);
    }

    @Test
    @DisplayName("User Post")
    void testPostUser() throws DBException
    {
        for(int i = 0; i < 3; i++)
        {
            userDao.postUser(goodUsers[i]);
        }

        for(int i = 0; i < 3; i++)
        {
            assertEquals(goodUsers[i], userDao.verifyUser(goodUsers[i].getUserName(), goodUsers[i].getPassword()));
        }

    }

    @Test
    @DisplayName("Bad User Post")
    void badTestPostUser() throws DBException
    {
        userDao.postUser(goodUsers[0]);
        for (int i = 0; i < 8; i++)
        {
            int finalI = i;
            assertThrows(DBException.class, () -> userDao.postUser(badUsers[finalI]));

        }
    }

    @Test
    @DisplayName("Verify User")
    void testVerifyUser() throws DBException
    {
        User newUser = new User(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "f",
                "personid"
        );

        userDao.postUser(newUser);
        assertEquals(newUser, userDao.verifyUser("username", "password"));
    }

    @Test
    @DisplayName("Bad Verify User")
    void badTestVerifyUser() throws DBException
    {
        for(int i = 0; i < 3; i++)
        {
            userDao.postUser(goodUsers[i]);
        }

        assertNull(userDao.verifyUser(goodUsers[0].getUserName(), "Wrong password"));
        assertNull(userDao.verifyUser("Bad username", goodUsers[0].getPassword()));

    }

    @Test
    @DisplayName("Find User Info")
    void testFindUser() throws DBException
    {
        User newUser = new User(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "f",
                "personid"
        );

        userDao.postUser(newUser);
        User findUser = userDao.findUserInfoByUsername("username");

        assertEquals(newUser.getUserName(), findUser.getUserName());
        assertEquals(newUser.getFirstName(), findUser.getFirstName());
        assertEquals(newUser.getLastName(), findUser.getLastName());
        assertEquals(newUser.getGender(), findUser.getGender());
        assertEquals(newUser.getPersonID(), findUser.getPersonID());
        assertNull(findUser.getPassword());
        assertNull(findUser.getEmail());

    }

    @Test
    @DisplayName("Clear tables")
    void testClear() throws DBException
    {
        for(int i = 0; i < 3; i++)
        {
            userDao.postUser(goodUsers[i]);
        }

        userDao.clear();
        assertNull(userDao.verifyUser(goodUsers[0].getUserName(), goodUsers[0].getPassword()));
        assertNull(userDao.verifyUser(goodUsers[1].getUserName(), goodUsers[1].getPassword()));
    }
}