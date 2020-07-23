package Services;

import DataAccess.DBException;
import Model.User;

import DataAccess.UserAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ReqRes.FillRequest;
import ReqRes.FillResult;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest
{
    private FillService fillService;
    private Connection conn;
    private UserAccess userDao;

    @BeforeEach
    void setUp() throws IOException, DBException
    {
        fillService = new FillService();
        conn = fillService.getConn();
        userDao = new UserAccess(conn);

    }

    @AfterEach
    void tearDown() throws DBException
    {
        fillService.commit(false);
    }

    @Test
    @DisplayName("Existing User Fill")
    void fill() throws DBException
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

        int generations = 4;

        FillRequest fillRequest = new FillRequest();
        fillRequest.setGenerations(generations);
        fillRequest.setUserName("username");

        FillResult expectedResult = new FillResult();


        int personsInserted = calculateInserts(generations);
        int eventsInserted = ((personsInserted - 1) * 3) + 1;

        String message = String.format("Successfully added %d persons and %d events to the database.", personsInserted, eventsInserted);

        expectedResult.setSuccess(true);
        expectedResult.setMessage(message);

        FillResult result = fillService.fill(fillRequest);

        assertEquals(expectedResult.isSuccess(), result.isSuccess());
        assertEquals(expectedResult.getMessage(), result.getMessage());
    }

    @Test
    @DisplayName("No User Fill")
    void badFill() throws DBException
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

        FillRequest badUser = new FillRequest();
        badUser.setGenerations(4);
        badUser.setUserName("badusername");

        FillRequest badGenerations = new FillRequest();
        badGenerations.setGenerations(0);
        badGenerations.setUserName("username");

        FillResult expectedResult = new FillResult();

        expectedResult.setSuccess(false);
        expectedResult.setMessage("Error: Bad user or generations");

        FillResult result = fillService.fill(badUser);
        FillResult result2 = fillService.fill(badGenerations);

        assertEquals(expectedResult.isSuccess(), result.isSuccess());
        assertEquals(expectedResult.getMessage(), result.getMessage());
        assertEquals(expectedResult.isSuccess(), result2.isSuccess());
        assertEquals(expectedResult.getMessage(), result2.getMessage());
    }

    int calculateInserts(int generations)
    {
        int insertions = 1;

        for(int i = 0; i < generations; i++)
        {
            insertions += Math.pow(2, i+1);
        }
        return insertions;
    }
}