package Services;
import ReqRes.PersonUserResult;
import Model.User;
import Model.Person;
import Model.AuthToken;
import DataAccess.UserAccess;
import DataAccess.PersonAccess;
import DataAccess.AuthTokenAccess;

/**
 * PersonUser service
 */
public class PersonUserService
{
    /**
     * Default constructor
     */
    public PersonUserService()
    {

    }

    //Verifies token to find user, retrieves info

    /**
     * Uses DAO objects to verify the token, then retrieves info
     * @param token The token
     * @return A PersonUserResult
     */
    public PersonUserResult findFamily(String token)
    {
        return null;
    }
}
