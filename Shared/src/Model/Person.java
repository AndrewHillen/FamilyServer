package Model;

import java.util.Objects;

/**
 * A Person
 */
public class Person
{
    /**
     * A String containing the username associated with this person
     */
    private String associatedUsername;
    /**
     * A String containing the first name
     */
    private String firstName;
    /**
     * A String containing the last name
     */
    private String lastName;
    /**
     * A string containing the gender
     */
    private String gender;
    /**
     * A String containing the person ID
     */
    private String personID;
    /**
     * A String containing the person ID of the father of this person
     */
    private String fatherID;
    /**
     * A String containing the person ID of the mother of this person
     */
    private String motherID;
    /**
     * A String containing the person ID of the spouse of this person
     */
    private String spouseID;

    /**
     * A constructor to fill all fields instantly
     * @param id
     * @param userName
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public Person(String id, String userName, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID)
    {
        this.personID = id;
        this.associatedUsername = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Default constructor
     */
    public Person()
    {

    }

    public String getId()
    {
        return personID;
    }

    public void setId(String id)
    {
        this.personID = id;
    }

    public String getUserName()
    {
        return associatedUsername;
    }

    public void setUserName(String userName)
    {
        this.associatedUsername = userName;
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

    public String getFatherID()
    {
        return fatherID;
    }

    public void setFatherID(String fatherID)
    {
        this.fatherID = fatherID;
    }

    public String getMotherID()
    {
        return motherID;
    }

    public void setMotherID(String motherID)
    {
        this.motherID = motherID;
    }

    public String getSpouseID()
    {
        return spouseID;
    }

    public void setSpouseID(String spouseID)
    {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId()) &&
                Objects.equals(getUserName(), person.getUserName()) &&
                Objects.equals(getFirstName(), person.getFirstName()) &&
                Objects.equals(getLastName(), person.getLastName()) &&
                Objects.equals(getGender(), person.getGender()) &&
                Objects.equals(getFatherID(), person.getFatherID()) &&
                Objects.equals(getMotherID(), person.getMotherID()) &&
                Objects.equals(getSpouseID(), person.getSpouseID());
    }

}