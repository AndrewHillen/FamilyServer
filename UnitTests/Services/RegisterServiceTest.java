package Services;

import DataAccess.DBException;
import ReqRes.RegisterRequest;
import ReqRes.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest
{
    RegisterService registerService;
    ClearService clearService;


    @BeforeEach
    void setUp() throws IOException, DBException
    {
        registerService = new RegisterService();
    }

    //This does no good since it commits as it goes, need to clear all tables
    @AfterEach
    void tearDown() throws DBException
    {
        clearService = new ClearService();
        clearService.clearDB();
    }

    @Test
    @DisplayName("Test good register")
    void register() throws DBException, IOException
    {
        RegisterRequest request = new RegisterRequest(
                "username",
                "password",
                "email",
                "firstName",
                "lastName",
                "f"
        );

        RegisterResult result = registerService.register(request);
        assertEquals(true, result.isSuccess());
        //Maybe check daos for stuff

    }

    @Test
    @DisplayName("Test bad register")
    void badRegister() throws DBException, IOException
    {
        //missing stuff
        RegisterRequest request = new RegisterRequest(
                "username",
                "password",
                "email",
                null,
                null,
                null
        );

        RegisterResult result = registerService.register(request);
        assertEquals(false, result.isSuccess());

    }
}