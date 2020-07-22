package Services;
import ReqRes.PersonRequest;
import ReqRes.PersonResult;
import Model.Person;
import DataAccess.PersonAccess;
import DataAccess.AuthTokenAccess;

/**
 * Person service
 */
public class PersonService
{
    /**
     * Default constructor
     */
    public PersonService()
    {

    }

    //Uses DAO to find a person Must verify authtoken

    /**
     * Uses DAO objects to verify authToken and retrieve a person
     * @param r The PersonRequest
     * @return A PersonResult
     */
    public PersonResult searchPerson(PersonRequest r)
    {
        return null;
    }

}
