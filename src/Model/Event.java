package Model;

import java.util.Objects;

/**
 * An event
 */
public class Event
{
    /**
     * A String containing the event ID
     */
    private String eventID;
    /**
     * A String containing the username associated with this event
     */
    private String associatedUsername;
    /**
     * A String containing the personID associated with this event
     */
    private String personID;
    /**
     * A float containing the latitude where the event took place
     */
    private Float latitude;
    /**
     * A float containing the longitude of where the event took place
     */
    private Float longitude;
    /**
     * A String containing the country the event took place
     */
    private String country;
    /**
     * A String containing the city the event took place
     */
    private String city;
    /**
     * A String containing a bried description of the event
     */
    private String eventType;
    /**
     * An int containing the year the event took place
     */
    private Integer year;

    /**
     * A constructor that instantly fills all fields
     * @param id
     * @param userName
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String id, String userName, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year)
    {
        this.eventID = id;
        this.associatedUsername = userName;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Default constructor
     */
    public Event()
    {

    }

    public String getId()
    {
        return eventID;
    }

    public void setId(String id)
    {
        this.eventID = id;
    }

    public String getUserName()
    {
        return associatedUsername;
    }

    public void setUserName(String userName)
    {
        this.associatedUsername = userName;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Float.compare(event.getLatitude(), getLatitude()) == 0 &&
                Float.compare(event.getLongitude(), getLongitude()) == 0 &&
                getYear() == event.getYear() &&
                Objects.equals(getId(), event.getId()) &&
                Objects.equals(getUserName(), event.getUserName()) &&
                Objects.equals(getPersonID(), event.getPersonID()) &&
                Objects.equals(getCountry(), event.getCountry()) &&
                Objects.equals(getCity(), event.getCity()) &&
                Objects.equals(getEventType(), event.getEventType());
    }

}
