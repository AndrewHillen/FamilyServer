package Services;

import Model.AuthToken;
import DataAccess.AuthTokenAccess;
import DataAccess.DBException;
import Model.User;
import DataAccess.UserAccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ReqRes.LoginRequest;
import ReqRes.LoginResult;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest
{
    private LoginService loginService;
    private Connection conn;
    private AuthTokenAccess authDao;
    private UserAccess userDao;

    @BeforeEach
    void setUp() throws DBException
    {
        loginService = new LoginService();
        conn = loginService.getConn();
        authDao = new AuthTokenAccess(conn);
        userDao = new UserAccess(conn);
    }

    @AfterEach
    void tearDown() throws DBException
    {
        loginService.commit(false);
    }

    @Test
    @DisplayName("Login Positive, Bad Password")
    void loginService() throws DBException
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


        LoginRequest request = new LoginRequest(newUser.getUserName(), newUser.getPassword());

        LoginResult result = loginService.loginService(request);
        assertNotNull(result.getAuthToken());
        assertNotNull(result.getUserName());
        assertNotNull(result.getPersonID());
        assertEquals(true, result.isSuccess());

        AuthToken returnToken = authDao.findAuthTokenByToken(result.getAuthToken());

        assertNotNull(returnToken);

        request = new LoginRequest(newUser.getUserName(), "badPassword");
        result = loginService.loginService(request);

        assertEquals(false, result.isSuccess());


    }

    @Test
    @DisplayName("User does not exist")
    void noUserTest() throws DBException
    {
        LoginRequest request = new LoginRequest("username", "password");

        LoginResult result = loginService.loginService(request);

        assertEquals(false, result.isSuccess());
    }
}