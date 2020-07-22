package ReqRes;

/**
 * Result of load request
 */
public class LoadResult
{
    /**
     * The response message
     */
    private String message;
    /**
     * Whether the request succeeded
     */
    private boolean success;

    /**
     * Initializes all fields
     * @param message
     * @param success
     */
    public LoadResult(String message, boolean success)
    {
        setMessage(message);
        setSuccess(success);
    }

    /**
     * Default constructor
     */
    public LoadResult()
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
