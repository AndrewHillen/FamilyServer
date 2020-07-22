package Services;
import DataAccess.UserAccess;
import DataAccess.Database;
import DataAccess.DBException;
import ReqRes.RegisterRequest;
import ReqRes.RegisterResult;
import Model.User;

import ReqRes.LoginRequest;
import ReqRes.LoginResult;
import ReqRes.FillRequest;
import ReqRes.FillResult;

import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;



/**
 * Register service
 */
public class RegisterService
{
    private Database db;
    private Connection conn;
    private UUID idGenerator;
    private LoginService login;
    private FillService fill;
    private UserAccess userDao;
    private RegisterResult result;



    /**
     * Default constructor
     */

    public RegisterService() throws DBException, IOException
    {
        db = new Database();
        conn = db.getConnection();
        userDao = new UserAccess(conn);
        result = new RegisterResult();
    }

    //Posts a user, person, and authToken for the user. returns registerResult

    /**
     * Uses DAO objects to post user, person, and authToken
     * @param r The RegisterRequest
     * @return A RegisterResult
     */
    public RegisterResult register(RegisterRequest r) throws DBException, IOException
    {
        try
        {
            idGenerator = UUID.randomUUID();
            String personID = idGenerator.toString();
            User newUser = new User(
                    r.getUserName(),
                    r.getPassword(),
                    r.getEmail(),
                    r.getFirstName(),
                    r.getLastName(),
                    r.getGender(),
                    personID
            );
            userDao.postUser(newUser);
            commit(true);
            login = new LoginService();

            LoginRequest loginRequest = new LoginRequest(r.getUserName(), r.getPassword());
            LoginResult loginResult = login.loginService(loginRequest);
            login.commit(true);

            fill = new FillService();
            FillRequest fillRequest = new FillRequest(r.getUserName(), 4);
            FillResult fillResult = fill.fill(fillRequest);
            fill.commit(true);


            result.setAuthToken(loginResult.getAuthToken());
            result.setUserName(loginResult.getUserName());
            result.setPersonID(loginResult.getPersonID());
            result.setSuccess(loginResult.isSuccess());


            if( !loginResult.isSuccess() || !fillResult.isSuccess() )
            {
                throw new DBException("Login failed");
            }

        }
        catch(DBException ex)
        {
            result.setSuccess(false);
            result.setMessage("Error: " + ex.getMessage());
            commit(false);
        }
        return result;
    }

    public void commit(boolean commit) throws DBException
    {
        db.closeConnection(commit);
    }
}
