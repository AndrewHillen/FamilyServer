package ReqRes;

/**
 * An Event request
 */
public class EventRequest
{

    /**
     * The ID of the Event
     */
    private String eventID;
    /**
     * The required AuthToken string
     */
    private String token;

    /**
     * Initializes all fields instantly
     * @param eventID
     * @param token
     */
    public EventRequest(String eventID, String token)
    {
        setEventID(eventID);
        setToken(token);
    }

    /**
     * Default constructor
     */
    public EventRequest()
    {

    }

    public String getEventID()
    {
        return eventID;
    }

    public void setEventID(String eventID)
    {
        this.eventID = eventID;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

}
