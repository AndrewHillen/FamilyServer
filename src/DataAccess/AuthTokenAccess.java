package DataAccess;
import java.sql.*;
import Model.AuthToken;

/**
 * AuthToken DAO
 */
public class AuthTokenAccess
{

    /**
     * The sql.Connection to the DB
     */
    private final Connection conn;

    /**
     * Initializes the instance of DAO with the connection
     * @param conn the connection
     */

    public AuthTokenAccess(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Posts an AuthToken to the DB
     * @param t The AuthToken to post
     */
    public void postAuthToken(AuthToken t) throws DBException
    {
        String sqlString = "INSERT INTO authToken (token, username) " +
                "VALUES(?,?)";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, t.getToken());
            sqlStatement.setString(2, t.getUserName());

            sqlStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            throw new DBException("Error inserting authToken");
        }
    }

    /**
     * Finds the Username associated with the supplied token
     * @param token
     * @return An AuthToken
     */

    //TODO: Timestamp defaults to null, must be changed (Not really for this project
    public AuthToken findAuthTokenByToken(String token) throws DBException
    {
        String sqlString = "SELECT * FROM authToken WHERE token = ?";
        ResultSet result = null;

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, token);
            result = sqlStatement.executeQuery();

            if(result.next())
            {
                AuthToken returnToken = new AuthToken(
                        result.getString("token"),
                        result.getString("username"),
                        null

                );
                return returnToken;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new DBException("Error finding authToken");
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
        String sqlString = "DELETE FROM authToken";
        try(Statement statement = conn.createStatement())
        {
            statement.executeUpdate(sqlString);
        }
        catch(SQLException ex)
        {
            throw new DBException("Error while clearing authToken table");
        }
    }
}
