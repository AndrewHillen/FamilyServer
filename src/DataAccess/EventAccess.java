package DataAccess;
import java.sql.*;
import java.util.ArrayList;
import Model.Event;

/**
 * Event DAO
 */
public class EventAccess
{
    /**
     * The sql.Connection to the DB
     */
    private final Connection conn;

    /**
     * Initializes the instance of the DAO with the connection
     * @param conn The connection
     */
    public EventAccess(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Posts an Event to the DB
     * @param e The Event to post
     */
    public void postEvent(Event e) throws DBException
    {
        String sqlString = "INSERT INTO event (id, user_username, person_id, " +
                "latitude, longitude, country, city, eventType, year) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, e.getId());
            sqlStatement.setString(2, e.getUserName());
            sqlStatement.setString(3, e.getPersonID());
            sqlStatement.setString(4, Float.toString(e.getLatitude()));
            sqlStatement.setString(5, Float.toString(e.getLongitude()));
            sqlStatement.setString(6, e.getCountry());
            sqlStatement.setString(7, e.getCity());
            sqlStatement.setString(8, e.getEventType());
            sqlStatement.setString(9, Integer.toString(e.getYear()));

            sqlStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            throw new DBException("Unable to insert event");
        }
        catch(NullPointerException ex)
        {
            throw new DBException("Unable to insert event");
        }
    }


    /**
     * Deletes all Event elements associated with the supplied username from the DB
     * @param userName
     */
    public void deleteByUsername(String userName) throws DBException
    {
        String sqlString = "DELETE FROM event WHERE user_username = ?";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, userName);
            sqlStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            throw new DBException("Error while deleting events by username");
        }
    }

    /**
     * Finds the Event with the supplied EventID
     * @param ID EventID
     * @return An Event matching the EventID
     */
    public Event findEventByID(String ID) throws DBException
    {
        String sqlString = "SELECT * FROM event WHERE id = ?";
        ResultSet result = null;

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, ID);
            result = sqlStatement.executeQuery();

            if(result.next())
            {
                Event returnEvent = new Event(
                        result.getString("id"),
                        result.getString("user_username"),
                        result.getString("person_id"),
                        result.getFloat("latitude"),
                        result.getFloat("longitude"),
                        result.getString("country"),
                        result.getString("city"),
                        result.getString("eventType"),
                        result.getInt("year")
                );
                return returnEvent;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new DBException("Error while finding event");
        }
        finally
        {
            if(result != null)
            {
                try
                {
                    result.close();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Finds the array of Events associated with the supplied username
     * @param userName
     * @return An Array of Events
     */
    public Event[] findEventsByUsername(String userName) throws DBException
    {
        ArrayList<Event> events = new ArrayList<>();
        ResultSet result = null;
        String sqlString = "SELECT * FROM event WHERE user_username = ?";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, userName);
            result = sqlStatement.executeQuery();

            if(!result.next())
            {
                return null;
            }
            do
            {
                Event event = new Event(
                        result.getString("id"),
                        result.getString("user_username"),
                        result.getString("person_id"),
                        result.getFloat("latitude"),
                        result.getFloat("longitude"),
                        result.getString("country"),
                        result.getString("city"),
                        result.getString("eventType"),
                        result.getInt("year")
                );

                events.add(event);

            } while(result.next());

            return events.toArray(new Event[events.size()]);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new DBException("Error while finding person");
        }
        finally
        {
            if(result != null)
            {
                try
                {
                    result.close();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        }


    }

    /**
     * Clears the database tables for this DAO
     */
    public void clear() throws DBException
    {
        String sqlString = "DELETE FROM event";

        try(Statement statement = conn.createStatement())
        {
            statement.executeUpdate(sqlString);
        }
        catch (SQLException ex)
        {
            throw new DBException("Error while clearing events");
        }
    }
}
