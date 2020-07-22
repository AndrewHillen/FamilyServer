package ReqRes;

/**
 * Result of clear request
 */
public class ClearResult
{
    /**
     * Success or failure message
     */
    private String message;
    /**
     * Whether it succeeded or not
     */
    private boolean success;

    /**
     * Initializes all fields instantly
     * @param message
     * @param success
     */
    public ClearResult(String message, boolean success)
    {
        setMessage(message);
        setSuccess(success);
    }

    /**
     * Default constructor
     */
    public ClearResult()
    {

    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }
}
