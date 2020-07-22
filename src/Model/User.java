package Model;

import java.util.Objects;

/**
 * A User
 */
public class User
{
    /**
     * A String containing the username
     */
    private String userName;
    /**
     * A String containing the password
     */
    private String password;
    /**
     * A String containing the email
     */
    private String email;
    /**
     * A String containing the first name
     */
    private String firstName;
    /**
     * A string containing the last name
     */
    private String lastName;
    /**
     * A string containing the gender
     */
    private String gender;
    /**
     * A String containing the person id associated with this user
     */
    private String personID;

    /**
     * A constructor to fill all fields instantly
     * @param userName
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     *
     */
    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID)
    {
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setPersonID(personID);
    }

    /**
     * A Default constructor
     */
    public User()
    {

    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUserName(), user.getUserName()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getGender(), user.getGender()) &&
                Objects.equals(getPersonID(), user.getPersonID());
    }

}
