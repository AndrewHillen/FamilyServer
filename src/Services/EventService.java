package Services;
import DataAccess.*;
import Model.AuthToken;
import ReqRes.EventRequest;
import ReqRes.EventResult;
import Model.Event;
import ReqRes.PersonResult;

import java.sql.Connection;

/**
 * The EventService
 */
public class EventService
{
    private EventResult result;
    private Database db;
    private Connection conn;
    private EventAccess eventDao;
    private AuthTokenAccess authDao;
    /**
     * Default constructor
     */
    public EventService() throws DBException
    {
        db = new Database();
        conn = db.getConnection();
        eventDao = new EventAccess(conn);
        authDao = new AuthTokenAccess(conn);
        result = new EventResult();
    }

    /**
     * Verifies the token and returns the result using the DAO objects
     * @param request
     * @return An EventResult
     */
    public EventResult searchEvents(EventRequest request) throws DBException
    {
        AuthToken token = authDao.findAuthTokenByToken(request.getToken());
        Event event = eventDao.findEventByID(request.getEventID());

        if(token == null || event == null)
        {
            result.setSuccess(false);
            result.setMessage("Error: Invalid AuthToken or ID");
            return result;
        }
        if(!token.getUserName().equals(event.getUserName()))
        {
            result.setSuccess(false);
            result.setMessage("Error: Event does not belong to this user");
            return result;
        }

        result.setAssociatedUsername(event.getUserName());
        result.setEventID(event.getId());
        result.setPersonID(event.getPersonID());
        result.setLatitude(event.getLatitude());
        result.setLongitude(event.getLongitude());
        result.setCountry(event.getCountry());
        result.setCity(event.getCity());
        result.setEventType(event.getEventType());
        result.setYear(event.getYear());
        result.setSuccess(true);



        return result;
    }

    //For Testing purposes
    public Connection getConn()
    {
        return conn;
    }

    public void commit(boolean commit) throws DBException
    {
        db.closeConnection(commit);
    }
}
