package ReqRes;

/**
 * Login request
 */
public class LoginRequest
{
    /**
     * The username
     */
    private String userName;
    /**
     * The password
     */
    private String password;

    /**
     * Initializes all fields
     * @param userName
     * @param password
     */
    public LoginRequest(String userName, String password)
    {
        setUserName(userName);
        setPassword(password);
    }

    /**
     * Default constructor
     */
    public LoginRequest()
    {

    }
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
