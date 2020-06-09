package twitter.location.program;
import java.util.Set;
import java.util.HashSet;
public class User
{   
    private long id; 
    private GPSPoint home; 
    private Set<User> friends;
    private double latitude;
    private double longitude;
    private long homeLocationKnown;
    private long homeLocationInferred;
    private GPSPoint homeInferred;
    private double latitudeInferred;
    private double longitudeInferred;

    public User(long id,double latitude,double longitude,long homeLocationKnown)
    {
	this.id=id;
	this.latitude=latitude;
	this.longitude=longitude;
	this.homeLocationKnown=homeLocationKnown;
	this.homeLocationInferred=0;
	friends=new HashSet<User>();
    }
    public long getId() 
    { 
	return id;
    }
    public GPSPoint getHomeLocation() 
    { 
	if(isHomeLocationKnown())
	{
		return new GPSPoint(latitude,longitude);
	}
	else
	{
		return null;
	}
    }
    public GPSPoint getHomeLocationInferred()
    {
	if(isHomeLocationInferred()==1)
	{
		return new GPSPoint(latitudeInferred,longitudeInferred);
	}
	else
	{
		return null;
	}
    }
    public void setInferredLocation(double latitudeInferred,double longitudeInferred)
    {
	this.latitudeInferred=latitudeInferred;
	this.longitudeInferred=longitudeInferred;
	this.homeInferred=new GPSPoint(latitudeInferred,longitudeInferred);
    }
    public void setHomeLocationInferred()
    {
	this.homeLocationInferred=1;
    }
    public long isHomeLocationInferred()
    {
	return this.homeLocationInferred;
    }
    public boolean isHomeLocationKnown() 
    { 
	if(homeLocationKnown==0)
	{
		return false;
	}
	else
	{
		return true;
	}
    }
    
    // Returns the set of friends of friends of this user.
    public Set<User> getFriendsOfFriends()
    {
	Set<User> friendsOfFriends = new HashSet<User>();
	
	for(User friend : getFriends())
	{
	    friendsOfFriends.addAll(friend.getFriends());
	}
	return friendsOfFriends;
    }

    public Set<User> getFriends()
    {
	return friends;
   }

    public void addFriend(User friend)
    {
	boolean friendExisted=friends.contains(friend);
	if(!friendExisted)
	{
		friends.add(friend);
	}
    }
}
