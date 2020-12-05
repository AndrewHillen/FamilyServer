package ReqRes;

/**
 * Result of fill request
 */
public class FillResult
{
    /**
     * String containing response message
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
    public FillResult(String message, boolean success)
    {
        setMessage(message);
        setSuccess(success);
    }

    /**
     * Default constructor
     */
    public FillResult()
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
