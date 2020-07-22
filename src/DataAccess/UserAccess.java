package DataAccess;
import java.sql.*;
import Model.User;

/**
 * User DAO
 */
public class UserAccess
{
    /**
     * The sql.Connection to the database
     */
    private final Connection conn;

    /**
     * Initializes the instance of the DAO with the connection
     * @param conn the connection
     */
    public UserAccess(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Posts a User to the DB. May throw an exception to catch later?
     * @param u The user to be posted to the DB
     */
    public void postUser(User u) throws DBException
    {
        String sqlString = "INSERT INTO user (username, password, email, firstName, " +
                "lastName, gender, person_id) VALUES(?,?,?,?,?,?,?)";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, u.getUserName());
            sqlStatement.setString(2, u.getPassword());
            sqlStatement.setString(3, u.getEmail());
            sqlStatement.setString(4, u.getFirstName());
            sqlStatement.setString(5, u.getLastName());
            sqlStatement.setString(6, u.getGender());
            sqlStatement.setString(7, u.getPersonID());

            sqlStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            throw new DBException("Unable to insert User");
        }
    }


    /**
     * Queries the DB for a user with the supplied username and password. May throw an exception?
     * @param userName
     * @param password
     * @return The user if found
     */
    public User verifyUser(String userName, String password) throws DBException
    {
        User user;
        ResultSet result = null;
        String sqlString = "SELECT * FROM user WHERE (username = ? AND password = ?)";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, userName);
            sqlStatement.setString(2, password);
            result = sqlStatement.executeQuery();

            if(result.next())
            {
                user = new User(
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("person_id"));
                return user;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new DBException("Error while finding user");
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

    public User findUserInfoByUsername(String userName) throws DBException
    {
        User user;
        ResultSet result = null;
        String sqlString = "SELECT * FROM user WHERE username = ?";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, userName);
            result = sqlStatement.executeQuery();

            if(result.next())
            {
                user = new User(
                        result.getString("username"),
                        null,
                        null,
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("person_id"));
                return user;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new DBException("Error while finding user");
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
     * Clears the database tables for this DAO
     */
    public void clear() throws DBException
    {
        String sqlString = "DELETE FROM user";
        try(Statement statement = conn.createStatement())
        {
            statement.executeUpdate(sqlString);
        }
        catch(SQLException ex)
        {
            throw new DBException("Error while clearing user table");
        }


    }


}
