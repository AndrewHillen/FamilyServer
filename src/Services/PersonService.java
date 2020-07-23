package Services;
import DataAccess.DBException;
import DataAccess.Database;
import Model.AuthToken;
import ReqRes.PersonRequest;
import ReqRes.PersonResult;
import Model.Person;
import DataAccess.PersonAccess;
import DataAccess.AuthTokenAccess;

import java.sql.Connection;

/**
 * Person service
 */
public class PersonService
{

    private PersonResult result;
    private Database db;
    private Connection conn;
    private PersonAccess personDao;
    private AuthTokenAccess authDao;

    /**
     * Default constructor
     */
    public PersonService() throws DBException
    {
        db = new Database();
        conn = db.getConnection();
        personDao = new PersonAccess(conn);
        authDao = new AuthTokenAccess(conn);
        result = new PersonResult();
    }

    //Uses DAO to find a person Must verify authtoken

    /**
     * Uses DAO objects to verify authToken and retrieve a person
     * @param request The PersonRequest
     * @return A PersonResult
     */
    public PersonResult searchPerson(PersonRequest request) throws DBException
    {
        AuthToken token = authDao.findAuthTokenByToken(request.getToken());
        Person person = personDao.findPersonByID(request.getPersonID());

        if(token == null || person == null)
        {
            result.setSuccess(false);
            result.setMessage("Error: Invalid AuthToken or ID");
            return result;
        }
        if(!token.getUserName().equals(person.getUserName()))
        {
            result.setSuccess(false);
            result.setMessage("Error: Person does not belong to this user");
            return result;
        }

        result.setAssociatedUsername(person.getUserName());
        result.setPersonID(person.getId());
        result.setFirstName(person.getFirstName());
        result.setLastName(person.getLastName());
        result.setGender(person.getGender());
        result.setFatherID(person.getFatherID());
        result.setMotherID(person.getMotherID());
        result.setSpouseID(person.getSpouseID());
        result.setSuccess(true);


        return result;
    }

    //For Testing purposes
    public Connection getConn()
    {
        return conn;
    }

    public void commit(boolean commit) throws DBException
    {
        db.closeConnection(commit);
    }

}
