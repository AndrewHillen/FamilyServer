package Model;
import java.sql.*;
import java.util.Objects;

/**
 * An AuthToken
 */
public class AuthToken
{
    /**
     * A String containing the token
     */
    private String token;
    /**
     * A String containing the username associated with this token
     */
    private String userName;
    /**
     * An sql.Date containing the timestamp of this token
     */
    private Date date;

    /**
     * Constructor to instantly fill all fields
     * @param token
     * @param userName
     * @param date
     */
    public AuthToken(String token, String userName, Date date)
    {
        setToken(token);
        setUserName(userName);
        setDate(date);
    }

    /**
     * Default constructor
     */

    public AuthToken()
    {

    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }


    //Compares everything but the timestamp
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(getToken(), authToken.getToken()) &&
                Objects.equals(getUserName(), authToken.getUserName());
    }
}
