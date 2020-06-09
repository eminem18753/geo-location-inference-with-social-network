package twitter.location.program;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SocialNetwork
{

    private Map<Long, User> nodes=null;
    private GroundTruth groundTruth=new GroundTruth();
    private long sizeOfNetwork;
    private long sizeNotShare;    
    private long sizeIsolated;
    private long sizeNoShareFriends;
    public SocialNetwork()
    {
	nodes=new HashMap<Long,User>();
    }
    // @return the number of users in the social network
    public long getSize()
    {
	return sizeOfNetwork;
    }
    public void setSize(long sizeOfNetwork)
    {
	this.sizeOfNetwork=sizeOfNetwork;
    }
    //
    public long getNotShareSize()
    {
	return sizeNotShare;
    }
    public void setNotShareSize(long sizeNotShare)
    {
	this.sizeNotShare=sizeNotShare;
    }
    public User getNodeById(long id)
    {
	User nodeById;
	if((nodeById=nodes.get(id))!=null)
	{
     	    return nodeById;
	}
	else
	{
	    return null;
	}
    }

    // @return an iterable collection of the {@link User}s of the social network
    public Iterable<User> getNodes()
    {
	List nodeList=new ArrayList<User>(nodes.values());
	return Collections.unmodifiableList(nodeList);
    }

    // Add user 'user' to the social network.
    public void addUser(User user)
    {
	nodes.put(user.getId(),user);
    }

    // Sets the friendship of two (existing) social network users (i.e., 'userId' and 'friendId')
    // @return true, if the friendship was set, false otherwise.
    public boolean setFriends(long userId, long friendId)
    {
	if(userId == friendId) { return false; }

	User user = (User)getNodeById(userId);
	User friend = (User)getNodeById(friendId);

	if(user == null || friend == null) { return false; }

	user.addFriend(friend);
	friend.addFriend(user);

	return true;
    }
    public void setSizeIsolated(long sizeIsolated)
    {
	this.sizeIsolated=sizeIsolated;
    }
    public long getSizeIsolated()
    {
	return this.sizeIsolated;
    }
    public void setSizeNoShareFriends(long sizeNoShareFriends)
    {
	this.sizeNoShareFriends=sizeNoShareFriends;
    }
    public long getSizeNoShareFriends()
    {
	return this.sizeNoShareFriends;
    }
    public void setGroundTruth(GroundTruth gt)
    {
	this.groundTruth=gt;
    }
    public GroundTruth getGroundTruth()
    {
	return this.groundTruth;
    }
}
