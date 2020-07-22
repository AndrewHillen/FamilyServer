package ReqRes;
import Model.Person;

/**
 * The result of a personUser request
 */
public class PersonUserResult
{
    /**
     * An array containing Persons associated with the User
     */
    private Person[] data;
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
     * @param data
     * @param success
     * @param message
     */
    public PersonUserResult(Person[] data, boolean success, String message)
    {
        setData(data);
        setSuccess(success);
        setMessage(message);
    }

    /**
     * Default constructor
     */
    public PersonUserResult()
    {

    }

    public Person[] getData()
    {
        return data;
    }

    public void setData(Person[] data)
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
