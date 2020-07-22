package ReqRes;

/**
 * Fill request
 */
public class FillRequest
{
    /**
     * String containing the username
     */
    private String userName;
    /**
     * int containing how many generations to fill
     */
    private int generations;
    /**
     * Initializes all fields instantly
     * @param userName
     * @param generations
     */
    public FillRequest(String userName, int generations)
    {
        setUserName(userName);
        setGenerations(generations);
    }

    /**
     * Default constructor
     */
    public FillRequest()
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

    public int getGenerations()
    {
        return generations;
    }

    public void setGenerations(int generations)
    {
        this.generations = generations;
    }
}
