package ReqRes;

import Model.User;
import Model.Person;
import Model.Event;

import java.util.Arrays;

/**
 * Load request
 */
public class LoadRequest
{
    /**
     * An array of User to load
     */
    private User[] users;
    /**
     * An array of Person to load
     */
    private Person[] persons;
    /**
     * An array of Event to load
     */
    private Event[] events;

    /**
     * Initializes all fields
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events)
    {
        setUsers(users);
        setPersons(persons);
        setEvents(events);
    }

    /**
     * Default constructor
     */
    public LoadRequest()
    {

    }

    public User[] getUsers()
    {
        return users;
    }

    public void setUsers(User[] users)
    {
        this.users = users;
    }

    public Person[] getPersons()
    {
        return persons;
    }

    public void setPersons(Person[] persons)
    {
        this.persons = persons;
    }

    public Event[] getEvents()
    {
        return events;
    }

    public void setEvents(Event[] events)
    {
        this.events = events;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadRequest that = (LoadRequest) o;
        return Arrays.equals(getUsers(), that.getUsers()) &&
                Arrays.equals(getPersons(), that.getPersons()) &&
                Arrays.equals(getEvents(), that.getEvents());
    }

}
