package twitter.location.program;
import java.util.Set;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.security.InvalidParameterException;

public class DatasetRetriever {
	private String edgesFilePath = null;
	private String homesFilePath = null;

	private GroundTruth gt = null;
	private SocialNetwork sn = null;

	private HashMap hashmapGroundTruth;
	private HashMap hashmapSocialNetwork;

	public DatasetRetriever(String edgesFilePath, String homesFilePath) {
		this.edgesFilePath=edgesFilePath;
		this.homesFilePath=homesFilePath;
	}
	public boolean read() {
		sn=new SocialNetwork();
		gt=new GroundTruth();

		try
		{
			BufferedReader brHomes=new BufferedReader(new FileReader(homesFilePath));
			String line;
			int count=0;
			int countNotShare=0;
                        while((line=brHomes.readLine())!=null)
                        {
				count++;
				if(line.length()<=8)
				{
					//System.out.print("line:");
	                                //System.out.println(line.length());
					continue;
				}
                                //System.out.println(line);
				String[] currentLine;
				//String[] currentLine=new String[10];
				//for(int i=0;i<10;i++)
				//{
				//	currentLine[i]="-1";
				//}
                                currentLine=line.split(",");
				int shareFlag=0;
				//System.out.println(Long.valueOf(currentLine[3].trim()));
				//System.out.println(Long.valueOf(currentLine[3].trim()));
				//System.out.println(currentLine.length);
				/*
				if(currentLine.length!=4)
				{
					System.out.print("alert:");
					System.out.print(count);
					System.out.print(" ");
					System.out.print(currentLine.length);
					System.out.print(" ");
					System.out.println(line.length());
				}
				*/
				if(Long.valueOf(currentLine[3].trim())==0)
				{
					//System.out.println(currentLine[3]);
					countNotShare++;
					shareFlag=1;
					//System.out.println(countNotShare);
				}
				//System.out.println(countNotShare);
				User currentUser=new User(Long.valueOf(currentLine[0].trim()),Double.valueOf(currentLine[1].trim()),Double.valueOf(currentLine[2].trim()),Long.valueOf(currentLine[3].trim()));
				sn.addUser(currentUser);
				GPSPoint currentPlace=new GPSPoint(Double.parseDouble(currentLine[1].trim()),Double.parseDouble(currentLine[2].trim()));
				gt.setHomeLocation(Long.valueOf(currentLine[0].trim()).longValue(),currentPlace);
                                //line.split(",");
                        }
			sn.setSize(count);
			sn.setNotShareSize(countNotShare);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}

		try
                {
                        //System.out.println(edgesFilePath);
                        BufferedReader brFriends=new BufferedReader(new FileReader(edgesFilePath));
                        String line;
			int countIsolated=0;
			int countNoShareFriends=0;
			long lastUser=0;
                        while((line=brFriends.readLine())!=null)
                        {
                                String[] currentLine;
				//System.out.print("line:");
				//System.out.println(line.length());
                                currentLine=line.split(",");
                                //currentUser=new User();

                                //System.out.println(line);

                                sn.setFriends(Long.valueOf(currentLine[0].trim()).longValue(),Long.valueOf(currentLine[1].trim()).longValue());
				
				User currentUser;
				if((currentUser=sn.getNodeById(Long.valueOf(currentLine[0].trim())))!=null)
				{
					/*
					if(currentUser==null)
					{
						System.out.println("No");
					}
					*/
					Set<User> friendsOfCurrentUser=new HashSet<User>();
					friendsOfCurrentUser=currentUser.getFriends();
					//long id1=currentUser.getId();
	
					int flagFriendShare=0;
					for(Iterator<User> it=friendsOfCurrentUser.iterator();it.hasNext();)
					{
						User tempFriend=it.next();
						if(tempFriend.isHomeLocationKnown())
						{
							flagFriendShare=1;
						}
					}
					if((flagFriendShare==0)&&(Long.valueOf(currentLine[0].trim())!=lastUser))
					{
						if(!currentUser.isHomeLocationKnown())
						{
							//System.out.print(currentUser.getId());
							//System.out.print(" ");
							//System.out.println(countNoShareFriends);
							countNoShareFriends++;
						}
					}
	
					flagFriendShare=0;
					
					if(Long.valueOf(currentLine[0].trim())-lastUser>=2)
					{
						for(long i=lastUser+1;i<Long.valueOf(currentLine[0]);i++)
						{
							User tempUser;
							if((tempUser=sn.getNodeById(i))!=null)
							{
								//System.out.print(i);
								//System.out.println(!tempUser.isHomeLocationKnown());
								if(!tempUser.isHomeLocationKnown())
								{
									//System.out.println(tempUser.getId());
									countIsolated++;
									countNoShareFriends++;
								}
							}
						}
					}
				
					lastUser=Long.valueOf(currentLine[0].trim());
                                	//line.split(",");
				}
                        }
			sn.setSizeIsolated(countIsolated);
			sn.setSizeNoShareFriends(countNoShareFriends);
                }
		catch(Throwable e)
                {
                        e.printStackTrace();
                }
		
		sn.setGroundTruth(gt);
		return true;
	}

	public SocialNetwork getSocialNetwork() {
		return sn;
	}

	public GroundTruth getGroundTruth() {
		if(gt==null)
		{
			throw new InvalidParameterException("GroundTruth is null!");
		}
		else
		{
			return gt;
		}
	}
}
