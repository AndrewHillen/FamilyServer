package Services;
import DataAccess.*;
import ReqRes.ClearResult;

import java.sql.Connection;

/**
 * Clear service
 */
public class ClearService
{

    private Database db;
    private Connection conn;
    private UserAccess userDao;
    private PersonAccess personDao;
    private EventAccess eventDao;
    private AuthTokenAccess authDao;
    private ClearResult result;
    /**
     * Default constructor
     */
    public ClearService() throws DBException
    {
        db = new Database();
        result = new ClearResult();
        conn = db.getConnection();
    }

    /**
     * Calls the DAO objects to clear the database
     * @return The ClearResult
     */
    public ClearResult clearDB() throws DBException
    {
        try
        {

            userDao = new UserAccess(conn);
            userDao.clear();

            personDao = new PersonAccess(conn);
            personDao.clear();

            eventDao = new EventAccess(conn);
            eventDao.clear();

            authDao = new AuthTokenAccess(conn);
            authDao.clear();

            db.closeConnection(true);

            result.setMessage("Clear succeeded");
            result.setSuccess(true);
        }
        catch(DBException ex)
        {
            result.setMessage("Error: " + ex.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    //For Testing purposes
    public Connection getConn()
    {
        return conn;
    }
}
