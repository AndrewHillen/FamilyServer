package Services;
import ReqRes.EventRequest;
import ReqRes.EventResult;
import Model.Event;
import DataAccess.EventAccess;
import DataAccess.AuthTokenAccess;

/**
 * The EventService
 */
public class EventService
{
    /**
     * Default constructor
     */
    public EventService()
    {

    }

    /**
     * Verifies the token and returns the result using the DAO objects
     * @param r
     * @return An EventResult
     */
    public EventResult searchEvents(EventRequest r)
    {
        return null;
    }
}
