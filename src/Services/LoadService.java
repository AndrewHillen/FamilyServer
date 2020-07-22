package Services;
import DataAccess.*;
import ReqRes.LoadRequest;
import ReqRes.LoadResult;
import Model.User;
import Model.Person;
import Model.Event;

import java.util.UUID;
import java.sql.Connection;

/**
 * The Load service
 */
public class LoadService
{
    private Database db;
    private Connection conn;
    private UUID idGenerator;
    private UserAccess userDao;
    private PersonAccess personDao;
    private EventAccess eventDao;
    private User[] users;
    private Person[] persons;
    private Event[] events;
    private LoadResult result;
    private ClearService clearService;

    /**
     * Default constructor
     */
    public LoadService() throws DBException
    {
        clearService = new ClearService();
        clearService.clearDB();
        db = new Database();
        conn = db.getConnection();
        userDao = new UserAccess(conn);
        personDao = new PersonAccess(conn);
        eventDao = new EventAccess(conn);
        result = new LoadResult();
    }

    /**
     * Uses DAO objects to load information into DB
     * @param request The LoadRequest containing information
     * @return A LoadResult
     */
    public LoadResult load(LoadRequest request)
    {
        users = request.getUsers();
        persons = request.getPersons();
        events = request.getEvents();
        if(users == null || persons == null || events == null )
        {
            result.setSuccess(false);
            result.setMessage("Error: Incomplete fill data");
            return result;
        }
        try
        {
            int userCount = users.length;
            int personCount = persons.length;
            int eventCount = events.length;

            for (int i = 0; i < users.length; i++)
            {
                userDao.postUser(users[i]);
            }
            for (int i = 0; i < persons.length; i++)
            {
                personDao.postPerson(persons[i]);
            }
            for (int i = 0; i < events.length; i++)
            {
                eventDao.postEvent(events[i]);
            }

            result.setSuccess(true);
            String message = String.format("Successfully added %d users, %d persons, and %d events to the database.", userCount, personCount, eventCount);
            result.setMessage(message);
        }
        catch(DBException ex)
        {
            result.setSuccess(false);
            result.setMessage("Error: " + ex.getMessage());
        }

        return result;
    }

    public void commit(boolean commit) throws DBException
    {
        db.closeConnection(commit);
    }

    //For Testing purposes
    public Connection getConn()
    {
        return conn;
    }
}
