package ReqRes;

/**
 * Person request
 */
public class PersonRequest
{
    /**
     * The ID of the person
     */
    private String personID;
    /**
     * The AuthToken String
     */
    private String token;

    /**
     * Initializes all fields
     * @param personID
     * @param token
     */
    public PersonRequest(String personID, String token)
    {
        setPersonID(personID);
        setToken(token);
    }

    /**
     * Default constructor
     */
    public PersonRequest()
    {

    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
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
