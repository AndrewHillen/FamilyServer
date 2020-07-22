package DataAccess;

import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenAccessTest
{
    private Database db;
    private AuthToken[] goodTokens;
    private AuthToken[] badTokens;
    private AuthTokenAccess authDao;

    @BeforeEach
    void setUp() throws DBException
    {
        db = new Database();
        Connection conn = db.getConnection();
        authDao = new AuthTokenAccess(conn);
        ArrayBuilder();
    }

    @AfterEach
    void tearDown() throws DBException
    {
        db.closeConnection(false);
    }


    @Test
    @DisplayName("Post Token")
    void postAuthToken() throws DBException
    {
        for(int i = 0; i < goodTokens.length; i++)
        {
            authDao.postAuthToken(goodTokens[i]);
            assertEquals(goodTokens[i], authDao.findAuthTokenByToken(Integer.toString(i)));
        }
    }

    @Test
    @DisplayName("Post Bad Token")
    void postBadAuthToken() throws DBException
    {
        authDao.postAuthToken(goodTokens[0]);
        for(int i = 0; i < badTokens.length; i++)
        {
            int finalI = i;
            assertThrows(DBException.class, () -> authDao.postAuthToken(badTokens[finalI]));
        }
    }

    @Test
    @DisplayName("Find AuthToken")
    void findAuthTokenByToken() throws DBException
    {
        AuthToken newToken = new AuthToken(
                "GoodToken",
                "username",
                null
        );
        authDao.postAuthToken(newToken);

        assertEquals(newToken, authDao.findAuthTokenByToken("GoodToken"));
    }

    @Test
    @DisplayName("Bad Find AuthToken")
    void findBadAuthTokenByToken() throws DBException
    {
        AuthToken newToken = new AuthToken(
                "GoodToken",
                "username",
                null
        );
        authDao.postAuthToken(newToken);

        assertNull(authDao.findAuthTokenByToken("BadToken"));
    }

    @Test
    @DisplayName("Clear")
    void clear() throws DBException
    {
        for(int i = 0; i < goodTokens.length; i++)
        {
            authDao.postAuthToken(goodTokens[i]);
            assertEquals(goodTokens[i], authDao.findAuthTokenByToken(Integer.toString(i)));
        }
        authDao.clear();
        for(int i = 0; i < goodTokens.length; i++)
        {
            assertNull(authDao.findAuthTokenByToken(goodTokens[i].getToken()));
        }
    }

    private void ArrayBuilder()
    {
        goodTokens = new AuthToken[4];
        badTokens = new AuthToken[3];

        for(int i = 0; i < 4; i++)
        {
            AuthToken newToken = new AuthToken(
                    Integer.toString(i),
                    "username" + Integer.toString(i),
                    null
            );
            goodTokens[i] = newToken;
        }

        AuthToken newToken = new AuthToken(
                null,
                "username",
                null
        );
        badTokens[0] = newToken;

        newToken = new AuthToken(
                "badToken",
                null,
                null
        );
        badTokens[1] = newToken;

        newToken = new AuthToken(
                "0",
                "username",
                null
        );
        badTokens[2] = newToken;
    }

}