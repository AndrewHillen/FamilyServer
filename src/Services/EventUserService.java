package Services;
import DataAccess.*;
import ReqRes.EventResult;
import ReqRes.EventUserResult;
import Model.User;
import Model.Event;
import Model.AuthToken;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * The EventUser service
 */
public class EventUserService
{
    private EventUserResult result;
    private Database db;
    private Connection conn;
    private EventAccess eventDao;
    private AuthTokenAccess authDao;
    /**
     * Default constructor
     */
    public EventUserService() throws DBException
    {
        db = new Database();
        conn = db.getConnection();
        eventDao = new EventAccess(conn);
        authDao = new AuthTokenAccess(conn);
        result = new EventUserResult();
    }

    /**
     * Uses DAO objects to verify the token, then retrieve the result
     * @param tokenString
     * @return An EventUserResult
     */
    public EventUserResult findEvents(String tokenString) throws DBException
    {
        AuthToken token = authDao.findAuthTokenByToken(tokenString);
        if(token == null)
        {
            result.setSuccess(false);
            result.setMessage("Error: Invalid AuthToken");
            return result;
        }

        Event[] event = eventDao.findEventsByUsername(token.getUserName());

        if(event == null)
        {
            result.setSuccess(false);
            result.setMessage("Error: No Events associated with this AuthToken");
            return result;
        }

        ArrayList<EventResult> eventResults = new ArrayList<>();

        for(int i = 0; i < event.length; i++)
        {
            EventResult eventResult = new EventResult();

            eventResult.setAssociatedUsername(event[i].getUserName());
            eventResult.setEventID(event[i].getId());
            eventResult.setPersonID(event[i].getPersonID());
            eventResult.setLatitude(event[i].getLatitude());
            eventResult.setLongitude(event[i].getLongitude());
            eventResult.setCountry(event[i].getCountry());
            eventResult.setCity(event[i].getCity());
            eventResult.setEventType(event[i].getEventType());
            eventResult.setYear(event[i].getYear());
            eventResult.setSuccess(true);

            eventResults.add(eventResult);
        }

        EventResult[] data = eventResults.toArray(new EventResult[eventResults.size()]);

        result.setData(data);
        result.setSuccess(true);

        return result;
    }

    public Connection getConn()
    {
        return conn;
    }

    public void commit(boolean commit) throws DBException
    {
        db.closeConnection(commit);
    }
}
