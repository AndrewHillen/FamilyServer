package ReqRes;

/**
 * The result of a login request
 */
public class LoginResult
{
    /**
     * A String containing the AuthToken
     */
    private String authToken;
    /**
     * The username
     */
    private String userName;
    /**
     * The personID
     */
    private String personID;
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
     * @param authToken
     * @param userName
     * @param personID
     * @param success
     * @param message
     */
    public LoginResult(String authToken, String userName, String personID, boolean success, String message)
    {
        setAuthToken(authToken);
        setUserName(userName);
        setPersonID(personID);
        setSuccess(success);
        setMessage(message);
    }

    /**
     * Default constructor
     */
    public LoginResult()
    {

    }

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
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
