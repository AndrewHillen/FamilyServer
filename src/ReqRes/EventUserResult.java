package ReqRes;

import Model.Event;

/**
 * The result of an EventUser request
 */
public class EventUserResult
{
    /**
     * An array containing Events
     */
    private Event[] data;
    /**
     * A String containing an error message
     */
    private String message;
    /**
     * A boolean showing whether the request was successful
     */
    private boolean success;

    /**
     * Initializes all fields instantly
     * @param data
     * @param success
     * @param message
     */
    public EventUserResult(Event[] data, boolean success, String message)
    {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    /**
     * Default constructor
     */
    public EventUserResult()
    {

    }

    public Event[] getData()
    {
        return data;
    }

    public void setData(Event[] data)
    {
        this.data = data;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
