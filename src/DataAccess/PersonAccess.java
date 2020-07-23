package DataAccess;

import Model.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * Person DAO
 */
public class PersonAccess
{
    /**
     * The sql.Connection to the DB
     */
    private final Connection conn;

    /**
     * Initializes the instance of the DAO with the connection
     * @param conn The connection
     */
    public PersonAccess(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Posts a Person to the DB
     * @param p The Person to post
     */
    public void postPerson(Person p) throws DBException
    {

        String sqlString = "INSERT INTO person (id, user_username, firstName, " +
                "lastName, gender, father_id, mother_id, spouse_id) VALUES(?,?,?,?,?,?,?,?)";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, p.getId());
            sqlStatement.setString(2, p.getUserName());
            sqlStatement.setString(3, p.getFirstName());
            sqlStatement.setString(4, p.getLastName());
            sqlStatement.setString(5, p.getGender());
            sqlStatement.setString(6, p.getFatherID());
            sqlStatement.setString(7, p.getMotherID());
            sqlStatement.setString(8, p.getSpouseID());

            sqlStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            throw new DBException("Error Unable to insert Person");
        }
    }

    //Deletes all elements associated with a user

    /**
     * Deletes all Person elements associated with the supplied username. May throw an exception?
     * @param userName
     */
    public void deleteByUsername(String userName) throws DBException
    {
        String sqlString = "DELETE FROM person where user_username = ?";
        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, userName);
            sqlStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            throw new DBException("Error while clearing persons by user");
        }
    }

    /**
     * Finds the person associated with the supplied personID. May throw an exception?
     * @param ID The personID
     * @return A person matching the ID
     */
    public Person findPersonByID(String ID) throws DBException
    {
        Person person;
        ResultSet result = null;
        String sqlString = "SELECT * FROM person WHERE id = ?";

        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, ID);
            result = sqlStatement.executeQuery();

            if(result.next())
            {
                person = new Person(
                        result.getString("id"),
                        result.getString("user_username"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("father_id"),
                        result.getString("mother_id"),
                        result.getString("spouse_id"));
                return person;
            }
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


        return null;
    }

    /**
     * Finds an array of Persons associated with the supplied username. May throw an exception?
     * @param userName
     * @return An array of Persons
     */
    public Person[] findPersonsByUsername(String userName) throws DBException
    {
        ArrayList<Person> persons = new ArrayList<>();
        ResultSet result = null;
        String sqlString = "SELECT * FROM person WHERE user_username = ?";

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
                Person person = new Person();

                person = new Person(
                        result.getString("id"),
                        result.getString("user_username"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("father_id"),
                        result.getString("mother_id"),
                        result.getString("spouse_id"));
                persons.add(person);
            } while(result.next());
            return persons.toArray(new Person[persons.size()]);
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


        //return null;
    }

    public void addFamilyByID(String personID, String[] familyIDs) throws DBException
    {
        String sqlString = "UPDATE PERSON " +
                "SET father_id = ?, " +
                "mother_id = ?, " +
                "spouse_id = ? " +
                "WHERE id = ?";
        try(PreparedStatement sqlStatement = conn.prepareStatement(sqlString))
        {
            sqlStatement.setString(1, familyIDs[0]);
            sqlStatement.setString(2, familyIDs[1]);
            sqlStatement.setString(3, familyIDs[2]);
            sqlStatement.setString(4, personID);

            sqlStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            throw new DBException("Error while adding family to user person");
        }
    }

    /**
     * Clears the database tables for this DAO
     */
    public void clear() throws DBException
    {
        String sqlString = "DELETE FROM person";
        try(Statement statement = conn.createStatement())
        {
            statement.executeUpdate(sqlString);
        }
        catch(SQLException ex)
        {
            throw new DBException("Error while clearing person table");
        }

    }
}
