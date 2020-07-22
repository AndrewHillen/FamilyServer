package DataAccess;
import java.sql.*;


public class Database
{
    private Connection conn;

    public Connection openConnection() throws DBException
    {
        try
        {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyServerDB.sqlite";

            conn = DriverManager.getConnection(CONNECTION_URL);

            conn.setAutoCommit(false);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new DBException("Connection to database failed");
            //Throw another exception like they did
        }
        return conn;
    }

    public Connection getConnection() throws DBException
    {
        if(conn == null)
        {
            return openConnection();
        }
        else
        {
            return conn;
        }
    }

    public void closeConnection(boolean commit) throws DBException
    {
        try
        {
            if(commit)
            {
                conn.commit();
            }
            else
            {
                conn.rollback();
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new DBException("Failed to close connection to database");
            //Maybe throw something like they did
        }
    }

    //Clear tables function? Probably handle in individual DAOs
}
