package Services;
import ReqRes.EventUserResult;
import Model.User;
import Model.Event;
import Model.AuthToken;
import DataAccess.UserAccess;
import DataAccess.EventAccess;
import DataAccess.AuthTokenAccess;

/**
 * The EventUser service
 */
public class EventUserService
{
    /**
     * Default constructor
     */
    public EventUserService()
    {

    }

    /**
     * Uses DAO objects to verify the token, then retrieve the result
     * @param token
     * @return An EventUserResult
     */
    public EventUserResult findEvents(String token)
    {
        return null;
    }
}
