package Services;
import DataAccess.DBException;
import DataAccess.Database;
import ReqRes.FillRequest;
import ReqRes.FillResult;
import Model.User;
import Model.Person;
import Model.Event;
import DataAccess.PersonAccess;
import DataAccess.EventAccess;
import DataAccess.UserAccess;
import ReqRes.LoadResult;
import SampleData.*;
import Handlers.Serializer;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Random;
import java.util.UUID;


/**
 * The fill service
 */
public class FillService
{
    private Database db;
    private Connection conn;
    private UUID idGenerator;
    private Serializer serializer;
    private LocationData locations;
    private PersonData maleNames;
    private PersonData femaleNames;
    private PersonData lastNames;
    private String username;
    private UserAccess userDao;
    private PersonAccess personDao;
    private EventAccess eventDao;
    private int generationParam;
    private int currentYear;
    private FillResult result;
    int personsInserted;
    int eventsInserted;

    /**
     * Default constructor
     */
    public FillService() throws DBException, IOException
    {
        db = new Database();
        conn = db.getConnection();
        serializer = new Serializer();
        initializeData();
        userDao = new UserAccess(conn);
        personDao = new PersonAccess(conn);
        eventDao = new EventAccess(conn);
        result = new FillResult();
        personsInserted = 0;
        eventsInserted = 0;

    }

    /**
     * Uses DAO objects to fill DB tables. Information to fill tables generated here?
     * @param r The FillRequest containing information
     * @return A FillResult
     */
    public FillResult fill(FillRequest r) throws DBException
    {
        username = r.getUserName();
        generationParam = r.getGenerations();
        currentYear = 2020;

        try
        {
            User self = userDao.findUserInfoByUsername(username);
            if(self == null || generationParam < 1)
            {
                throw new DBException("Bad user or generations");
            }
            personDao.deleteByUsername(username);
            eventDao.deleteByUsername(username);
            postUserPerson(self);

            String message = String.format("Successfully added %d persons and %d events to the database.", personsInserted, eventsInserted);

            result.setSuccess(true);
            result.setMessage(message);
        }
        catch(DBException ex)
        {
            result.setSuccess(false);
            result.setMessage("Error: " + ex.getMessage());
            commit(false);
        }

        return result;
    }
    /*
    Find user to populate new person
    wipe person table by username
    post new user person, add associated familyIDs
    post user spouse person, add associated familyIDs
    Post events for user person
    Post events for user spouse
    functions handle events for everyone else
     */

    private void postUserPerson(User user) throws DBException
    {
        idGenerator = UUID.randomUUID();
        String[] userParentIDs = familyBuilder(generationParam - 1, currentYear - 25);

        Person userPerson = new Person(
                user.getPersonID(),
                username,
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                userParentIDs[0],
                userParentIDs[1],
                null

        );
        personDao.postPerson(userPerson);
        personsInserted++;

        Random randomGenerator = new Random();
        generateEvent(currentYear - 25, user.getPersonID(), "Birth");
    }


    private String[] familyBuilder(int generation, int year) throws DBException
    {
        String[] returnIDs = new String[2];
        String[] fatherIDs = new String[2];
        String[] motherIDs = new String[2];

        if(generation > 0)
        {
            fatherIDs = familyBuilder(generation - 1, year - 25);
            motherIDs = familyBuilder(generation - 1, year - 25);
        }

        idGenerator = UUID.randomUUID();
        String husbandID = idGenerator.toString();

        idGenerator = UUID.randomUUID();
        String wifeID = idGenerator.toString();

        returnIDs[0] = husbandID;
        returnIDs[1] = wifeID;

        Marriage(year, returnIDs);
        Person husband = maleBuilder(fatherIDs, returnIDs, year);
        Person wife = femaleBuilder(motherIDs, returnIDs, year);

        return returnIDs;
    }

    private Person maleBuilder(String[] parentIDs, String[] selfIDs, int year) throws DBException
    {
        Random randomGenerator = new Random();
        int mBound = maleNames.getData().length;
        int lBound = lastNames.getData().length;
        String fatherID = parentIDs[0];
        String motherID = parentIDs[1];

        String maleName = maleNames.getData()[randomGenerator.nextInt(mBound)];
        String lastName = lastNames.getData()[randomGenerator.nextInt(lBound)];

        Person self = new Person(
                selfIDs[0],
                username,
                maleName,
                lastName,
                "m",
                fatherID,
                motherID,
                selfIDs[1]
        );

        personDao.postPerson(self);
        personsInserted++;

        generateEvent(year - 24, selfIDs[0], "Birth");
        generateEvent(year + 26, selfIDs[0], "Death");

        return self;
    }

    private Person femaleBuilder(String[] parentIDs, String[] selfIDs, int year) throws DBException
    {
        Random randomGenerator = new Random();

        int fBound = femaleNames.getData().length;
        int lBound = lastNames.getData().length;
        String fatherID = parentIDs[0];
        String motherID = parentIDs[1];


        String femaleName = femaleNames.getData()[randomGenerator.nextInt(fBound)];
        String lastName = lastNames.getData()[randomGenerator.nextInt(lBound)];

        Person self = new Person(
                selfIDs[1],
                username,
                femaleName,
                lastName,
                "m",
                parentIDs[0],
                parentIDs[1],
                selfIDs[0]
        );

        personDao.postPerson(self);
        personsInserted++;


        generateEvent(year - 24, selfIDs[1], "Birth");
        generateEvent(year + 26, selfIDs[1], "Death");

        return self;
    }

    private void Marriage(int year, String[] IDs) throws DBException
    {
        Random randomGenerator = new Random();
        int locationBound = locations.getData().length;
        Location location = locations.getData()[randomGenerator.nextInt(locationBound)];

        idGenerator = UUID.randomUUID();
        String eventID = idGenerator.toString();

        Event marriageEvent = new Event(
                eventID,
                username,
                IDs[0],
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "Marriage",
                year
        );

        idGenerator = UUID.randomUUID();
        String eventID2 = idGenerator.toString();

        Event marriageEvent2 = new Event(
                eventID2,
                username,
                IDs[1],
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "Marriage",
                year
        );

        eventDao.postEvent(marriageEvent);
        eventDao.postEvent(marriageEvent2);
        eventsInserted++;
        eventsInserted++;

    }

    private void generateEvent(int year, String ID, String type) throws DBException
    {
        Random randomGenerator = new Random();
        int locationBound = locations.getData().length;
        Location location = locations.getData()[randomGenerator.nextInt(locationBound)];

        idGenerator = UUID.randomUUID();
        String eventID = idGenerator.toString();

        Event generatedEvent = new Event(
                eventID,
                username,
                ID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                type,
                year
        );

        eventDao.postEvent(generatedEvent);
        eventsInserted++;

    }

    private void initializeData() throws IOException
    {
        locations = new LocationData();
        maleNames = new PersonData();
        femaleNames = new PersonData();
        lastNames = new PersonData();

        StringBuilder filePath = new StringBuilder(Paths.get("").toAbsolutePath().toString());

        StringBuilder locationPath = new StringBuilder(filePath.toString());
        StringBuilder malePath = new StringBuilder(filePath.toString());
        StringBuilder femalePath = new StringBuilder(filePath.toString());
        StringBuilder lastPath = new StringBuilder(filePath.toString());

        File locationFile = new File(locationPath.append("/json/locations.json").toString());
        File maleFile = new File(malePath.append("/json/mnames.json").toString());
        File femaleFile = new File(femalePath.append("/json/fnames.json").toString());
        File lastFile = new File(lastPath.append("/json/snames.json").toString());
        InputStream in = new FileInputStream(locationFile);

        String jsonString = serializer.readRequestBody(in);
        locations = (LocationData) serializer.Serialize(jsonString, locations);

        in = new FileInputStream(maleFile);
        jsonString = serializer.readRequestBody(in);
        maleNames = (PersonData) serializer.Serialize(jsonString, maleNames);

        in = new FileInputStream(femaleFile);
        jsonString = serializer.readRequestBody(in);
        femaleNames = (PersonData) serializer.Serialize(jsonString, femaleNames);

        in = new FileInputStream(lastFile);
        jsonString = serializer.readRequestBody(in);
        lastNames = (PersonData) serializer.Serialize(jsonString, lastNames);

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
