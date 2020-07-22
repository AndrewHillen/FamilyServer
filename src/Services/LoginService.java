package Services;
import DataAccess.DBException;
import DataAccess.Database;
import ReqRes.LoginRequest;
import ReqRes.LoginResult;
import Model.User;
import Model.AuthToken;
import DataAccess.AuthTokenAccess;
import DataAccess.UserAccess;

import java.sql.Connection;
import java.util.UUID;

/**
 * Login service
 */
public class LoginService
{
    private Database db;
    private Connection conn;
    private UserAccess userDao;
    private AuthTokenAccess authDao;
    private LoginResult result;
    private UUID idGenerator;
    /**
     * Default Constructor
     */
    public LoginService() throws DBException
    {
        db = new Database();
        conn = db.getConnection();
        userDao = new UserAccess(conn);
        authDao = new AuthTokenAccess(conn);
        result = new LoginResult();

    }

    //Searches for user and posts an authToken on success returns result

    /**
     * Uses DAO objects to search for the User
     * @param r The Login request
     * @return A LoginResult
     */
    public LoginResult loginService(LoginRequest r) throws DBException
    {
        try
        {
            User user = userDao.verifyUser(r.getUserName(), r.getPassword());
            if(user == null)
            {
                throw new DBException("Invalid login");
            }
            idGenerator = UUID.randomUUID();
            String tokenID = idGenerator.toString();
            AuthToken token = new AuthToken(tokenID, user.getUserName(), null);
            authDao.postAuthToken(token);

            result.setAuthToken(tokenID);
            result.setUserName(user.getUserName());
            result.setPersonID(user.getPersonID());
            result.setSuccess(true);


        }
        catch (DBException ex)
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

    //For Testing purposes
    public Connection getConn()
    {
        return conn;
    }


}
