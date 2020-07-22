package ReqRes;

/**
 * Result of register request
 */
public class RegisterResult
{
    /**
     * The authToken String
     */
    private String authToken;
    /**
     * The username
     */
    private String userName;
    /**
     * The personID of the User
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
     * @param message
     * @param success
     */
    public RegisterResult(String authToken, String userName, String personID, String message, boolean success)
    {
        setAuthToken(authToken);
        setUserName(userName);
        setPersonID(personID);
        setMessage(message);
        setSuccess(success);
    }

    /**
     * Default constructor
     */
    public RegisterResult()
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

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getErrorMessage()
    {
        return message;
    }
}
