package Services;
import ReqRes.LoadRequest;
import ReqRes.LoadResult;
import Model.User;
import Model.Person;
import Model.Event;
import DataAccess.UserAccess;
import DataAccess.PersonAccess;
import DataAccess.EventAccess;

/**
 * The Load service
 */
public class LoadService
{
    /**
     * Default constructor
     */
    public LoadService()
    {

    }

    /**
     * Uses DAO objects to load information into DB
     * @param r The LoadRequest containing information
     * @return A LoadResult
     */
    public LoadResult load(LoadRequest r)
    {
        return null;
    }
}
