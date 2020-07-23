package Services;
import DataAccess.*;
import ReqRes.PersonResult;
import ReqRes.PersonUserResult;
import Model.User;
import Model.Person;
import Model.AuthToken;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * PersonUser service
 */
public class PersonUserService
{
    private PersonUserResult result;
    private Database db;
    private Connection conn;
    private PersonAccess personDao;
    private AuthTokenAccess authDao;
    /**
     * Default constructor
     */
    public PersonUserService() throws DBException
    {
        db = new Database();
        conn = db.getConnection();
        personDao = new PersonAccess(conn);
        authDao = new AuthTokenAccess(conn);
        result = new PersonUserResult();
    }

    //Verifies token to find user, retrieves info

    /**
     * Uses DAO objects to verify the token, then retrieves info
     * @param tokenString The token
     * @return A PersonUserResult
     */
    public PersonUserResult findFamily(String tokenString) throws DBException
    {

        AuthToken token = authDao.findAuthTokenByToken(tokenString);
        if(token == null)
        {
            result.setSuccess(false);
            result.setMessage("Error: Invalid AuthToken");
            return result;
        }
        Person[] person = personDao.findPersonsByUsername(token.getUserName());

        if(person == null)
        {
            result.setSuccess(false);
            result.setMessage("Error: No Persons associated with this AuthToken");
            return result;
        }


        ArrayList<PersonResult> personResults = new ArrayList<>();

        for(int i = 0; i < person.length; i++)
        {
            PersonResult personResult = new PersonResult();
            personResult.setAssociatedUsername(person[i].getUserName());
            personResult.setPersonID(person[i].getId());
            personResult.setFirstName(person[i].getFirstName());
            personResult.setLastName(person[i].getLastName());
            personResult.setGender(person[i].getGender());
            personResult.setFatherID(person[i].getFatherID());
            personResult.setMotherID(person[i].getMotherID());
            personResult.setSpouseID(person[i].getSpouseID());
            personResult.setSuccess(true);
            personResults.add(personResult);
        }

        PersonResult[] data = personResults.toArray(new PersonResult[personResults.size()]);

        result.setData(data);
        result.setSuccess(true);




        return result;
    }

    public Connection getConn()
    {
        return conn;
    }

    public void commit(boolean commit) throws DBException
    {
        db.closeConnection(commit);
    }
}
