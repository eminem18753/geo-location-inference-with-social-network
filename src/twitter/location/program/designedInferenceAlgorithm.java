package twitter.location.program;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class designedInferenceAlgorithm extends InferenceAlgorithm
{
    public designedInferenceAlgorithm(SocialNetwork network) { super(network); }
    
    public Inference_GPS_Point inferHomeLocation(long userId)
    {
	User userToInfer=this.getNetwork().getNodeById(userId);
	double latitude=0;
	double longitude=0;
        double geography_x=0;
	double geography_y=0;
	double geography_z=0;
	double earth_radius_km=6371;
	double RADS_IN_DEGREE=Math.PI/180;
	double count=0;
	double withinRadius=50;
	//if we know the location, we don't need to infer
	if(userToInfer.isHomeLocationKnown())
	{
		GPSPoint inferredGPS=userToInfer.getHomeLocation();
		Inference_GPS_Point result=new Inference_GPS_Point(inferredGPS);
		return result;
	}
	//we infer the location
	else
	{
		User userForStarting=null;
		User userForStartingTemp=null;
		Set<User> friends=userToInfer.getFriends();
		Set<User> friendsOfFriends=userToInfer.getFriendsOfFriends();
		Set<User> friendsToShare=new HashSet<>();
		//get all the friends from a user
		for(Iterator<User> it=friends.iterator();it.hasNext();)
		{
			User userNow=it.next();
			if(userNow.isHomeLocationKnown())//add infer
			{
				userForStarting=userNow;
				break;
				//friendsToShare.add(userNow);
			}
			else if(userNow.isHomeLocationInferred()==1)
			{
				userForStartingTemp=userNow;
			}
		}
		if(userForStarting==null&&userForStartingTemp!=null)
		{
			userForStarting=userForStartingTemp;
		}

		if(userForStarting==null)
		{
			User userStartTemp=null;
			for(Iterator<User> it=friendsOfFriends.iterator();it.hasNext();)
			{
				User userNow=it.next();
				if(userNow.isHomeLocationKnown())//add inferred
				{
					userForStarting=userNow;
					break;
				}
				else if(userNow.isHomeLocationInferred()==1)
				{
					userStartTemp=userNow;
				}
			}
			if(userForStarting==null&&userStartTemp!=null)
			{
				userForStarting=userStartTemp;
			}
			for(Iterator<User> it=friendsOfFriends.iterator();it.hasNext();)
			{
				User userNow=it.next();
				//if(userForStarting==null)
				//System.out.println("no");

				if(userNow.isHomeLocationKnown()||userNow.isHomeLocationInferred()==1)//add inferred
				{
					if(userNow.isHomeLocationKnown())
					{
						if(userForStarting.getHomeLocation()!=null)
						{
							if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
							{
								friendsToShare.add(userNow);
							}
						}
						else
						{
							if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                        {
                                                                friendsToShare.add(userNow);
                                                        }
						}
					}
					else
					{
						//if(userForStarting==null)
						//System.out.println("get it");
						if(userForStarting.getHomeLocation()!=null)
						{
							if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
 	                                                {
	                                                       friendsToShare.add(userNow);
	                                                }
						}
						else
						{
							if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                        {
                                                               friendsToShare.add(userNow);
                                                        }
						}
					}
				}
			}
			if(friendsToShare.size()<=8&&userForStarting!=null)
			{
				Set<User> userMore=userForStarting.getFriends();
				for(Iterator<User> it=userMore.iterator();it.hasNext();)
				{
					User userNow=it.next();
					if(userNow.isHomeLocationKnown()||userNow.isHomeLocationInferred()==1)//add inferred
					{
						if(userNow.isHomeLocationKnown())
						{
							if(userForStarting.getHomeLocation()!=null)
							{
								if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
								{
									friendsToShare.add(userNow);
								}
							}
							else
							{
								if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                                {
                                                                        friendsToShare.add(userNow);
                                                                }
							}
						}
						else
						{
							if(userForStarting.getHomeLocation()!=null)
							{
								if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
								{
									friendsToShare.add(userNow);
								}
							}
							else
							{
								if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                                {
                                                                        friendsToShare.add(userNow);
                                                                }
							}
						}
					}
				}
			}
		}
		else
		{
			for(Iterator<User> it=friends.iterator();it.hasNext();)
			{
				User userNow=it.next();
				if(userNow.isHomeLocationKnown()||userNow.isHomeLocationInferred()==1)//add inferred
				{
					if(userNow.isHomeLocationKnown())
					{
						if(userForStarting.getHomeLocation()!=null)
						{
							if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
							{
								friendsToShare.add(userNow);
							}
						}
						else
						{
							if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                        {
                                                                friendsToShare.add(userNow);
                                                        }
						}
					}
					else
					{
						if(userForStarting.getHomeLocation()!=null)
						{
							if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
							{
								friendsToShare.add(userNow);
							}
						}
						else
						{
							if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                        {
                                                                friendsToShare.add(userNow);
                                                        }
						}
					}
				}
			}
			for(Iterator<User> it=friendsOfFriends.iterator();it.hasNext();)
			{
				User userNow=it.next();
				if(userNow.isHomeLocationKnown()||userNow.isHomeLocationInferred()==1)//add inferred
				{
					if(userNow.isHomeLocationKnown())
					{
						if(userForStarting.getHomeLocation()!=null)
						{
							if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
							{
								friendsToShare.add(userNow);
							}
						}
						else
						{
							if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                        {
                                                                friendsToShare.add(userNow);
                                                        }
						}
					}
					else
					{
						if(userForStarting.getHomeLocation()!=null)
						{
							if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
							{
								friendsToShare.add(userNow);
							}
						}
						else
						{
							if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                        {
                                                                friendsToShare.add(userNow);
                                                        }
						}
					}
				}
			}
			if(friendsToShare.size()<=9)
                        {
                                Set<User> userMore=userForStarting.getFriends();
                                for(Iterator<User> it=userMore.iterator();it.hasNext();)
                                {
                                        User userNow=it.next();
                                        if(userNow.isHomeLocationKnown()||userNow.isHomeLocationInferred()==1)//add inferred
                                        {
						if(userNow.isHomeLocationKnown())
						{
							if(userForStarting.getHomeLocation()!=null)
							{
		                                                if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
		                                                {
		                                                        friendsToShare.add(userNow);
		                                                }
							}
							else
							{
								if(userNow.getHomeLocation().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                                {
                                                                        friendsToShare.add(userNow);
                                                                }
							}
						}
						else
						{
							if(userForStarting.getHomeLocation()!=null)
							{
								if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocation())<=withinRadius)
								{
									friendsToShare.add(userNow);
								}
							}
							else
							{
								if(userNow.getHomeLocationInferred().distanceTo(userForStarting.getHomeLocationInferred())<=withinRadius)
                                                                {
                                                                        friendsToShare.add(userNow);
                                                                }
							}
						}
                                        }
                                }
                        }
			

		}
		/*
		if(friendsToShare.size()==0)
		{
			Set<User> moreFriends=userToInfer.getFriendsOfFriends();
			//friendsToShare=new HashSet<>();
			for(Iterator<User> it=moreFriends.iterator();it.hasNext();)
                	{
                       		User userNow=it.next();
                        	if(userNow.isHomeLocationKnown())
                       		{
                        	       friendsToShare.add(userNow);
                        	}
                	}
		}
		*/

		//if friendsToShare.size()!=0
		//get all the friends that share



		//all users

		
		for(Iterator<User> it=friendsToShare.iterator();it.hasNext();)
		{
			
			/*
			User userTemp=it.next();
			GPSPoint friendGPS=userTemp.getHomeLocation();
			latitude=latitude+friendGPS.latitude;
			longitude=longitude+friendGPS.longitude;
			*/
			User userTemp=it.next();

			double latitudeNow;
			double longitudeNow;
			//double latitudeNow=userTemp.getHomeLocation().getLatitude();
			//double longitudeNow=userTemp.getHomeLocation().getLongitude();
		
			if(userTemp.isHomeLocationKnown())
			{
				latitudeNow=userTemp.getHomeLocation().getLatitude();
				longitudeNow=userTemp.getHomeLocation().getLongitude();
			}
			else
			{
				latitudeNow=userTemp.getHomeLocationInferred().getLatitude();
				longitudeNow=userTemp.getHomeLocationInferred().getLongitude();
			}
			geography_x+=Math.cos(latitudeNow)*Math.cos(longitudeNow);
			geography_y+=Math.cos(latitudeNow)*Math.sin(longitudeNow);
			geography_z+=Math.sin(latitudeNow);

			count++;
		}
		geography_x/=count;
		geography_y/=count;
		geography_z/=count;

		latitude=Math.atan2(geography_z,Math.sqrt(geography_x*geography_x+geography_y*geography_y));
		longitude=Math.atan2(geography_y,geography_x);

		latitude/=RADS_IN_DEGREE;
		longitude/=RADS_IN_DEGREE;
		//latitude=latitude/count;
		//longitude=longitude/count;


		//get the geographic center
		GPSPoint inferredLocation=new GPSPoint(latitude,longitude);
		Inference_GPS_Point geometricCenter=new Inference_GPS_Point(inferredLocation);
		/*
		if(count==1||count==2)
		{
			return geometricCenter;
		}
		*/

		//all users



		//start
		/*method2
		Set<User> smallCluster=new HashSet<>();
		Set<User> resultCluster=new HashSet<>();
		int countClose=0;
		int max=0;
		for(Iterator<User> it=friendsToShare.iterator();it.hasNext();)
		{
			User userTemp=it.next();
			GPSPoint friendGPS=userTemp.getHomeLocation();
			countClose=0;
			for(Iterator<User> innerIt=friendsToShare.iterator();innerIt.hasNext();)
			{
				User userInnerTemp=innerIt.next();
				GPSPoint innerGPS=userInnerTemp.getHomeLocation();
				if(friendGPS.distanceTo(innerGPS)<=50)
				{
					countClose++;
					smallCluster.add(userInnerTemp);
				}
				if(countClose>max)
				{
					max=countClose;
					resultCluster=smallCluster;
				}
			}
		}

		count=0;
		latitude=0;
		longitude=0;
		for(Iterator<User> it=resultCluster.iterator();it.hasNext();)
                {
                        User userTemp=it.next();
                        GPSPoint friendGPS=userTemp.getHomeLocation();
                        latitude=latitude+friendGPS.latitude;
                        longitude=longitude+friendGPS.longitude;
                        count++;
                }
                latitude=latitude/count;
                longitude=longitude/count;
                //get the geographic center
                GPSPoint inferredLocation=new GPSPoint(latitude,longitude);
                Inference_GPS_Point geometricCenter=new Inference_GPS_Point(inferredLocation);
		method2*/


		//System.out.println(userId);
		/*
                if(count==1||count==2)
                {
                        return geometricCenter;
                }
		*/


		//end
		/*
		double closestLatitude=-10000;
		double closestLongitude=-10000;
		GPSPoint GPSClosest=new GPSPoint(closestLatitude,closestLongitude);

		for(Iterator<User> it=friendsToShare.iterator();it.hasNext();)
		{
			User userTemp=it.next();
			GPSPoint GPSTemp=userTemp.getHomeLocation();
			//double latitudeTemp=GPSTemp.latitude;
			//double longitudeTemp=GPSTemp.longitude;
			if(GPSTemp.distanceTo(inferredLocation)<=GPSClosest.distanceTo(inferredLocation))
			{
				GPSClosest.latitude=GPSTemp.latitude;
				GPSClosest.longitude=GPSTemp.longitude;
			}
		}
		*/

		/*method1 58%
		double farthestLatitude=0;
                double farthestLongitude=0;

		GPSPoint GPSFarthest=new GPSPoint(farthestLatitude,farthestLongitude);

		int countFar=0;
		int loopIndex=(int)count;

		//System.out.println(count);
		while(loopIndex>=count-1)
		{
			//int tempId=-1;
			User farthest=null;
	                for(Iterator<User> it=friendsToShare.iterator();it.hasNext();)
	                {
				countFar++;
				
	                        User userTemp=it.next();
	                        GPSPoint GPSTemp=userTemp.getHomeLocation();
	                        //double latitudeTemp=GPSTemp.latitude;
	                        //double longitudeTemp=GPSTemp.longitude;
				if(countFar==1)
	                        {
	                                GPSFarthest.latitude=GPSTemp.latitude;
	                                GPSFarthest.longitude=GPSTemp.longitude;
	                        }
	                        if(GPSTemp.distanceTo(inferredLocation)>=GPSFarthest.distanceTo(inferredLocation))
	                        {
	                                GPSFarthest.latitude=GPSTemp.latitude;
	                                GPSFarthest.longitude=GPSTemp.longitude;
					//tempId=userTemp.getId();
					farthest=userTemp;
	                        }
			}
			friendsToShare.remove(farthest);
			loopIndex--; 
               }

		double finalLatitude=0;
		double finalLongitude=0;
		for(Iterator<User> it=friendsToShare.iterator();it.hasNext();)
                {
                        User userTemp=it.next();
                        GPSPoint friendGPS=userTemp.getHomeLocation();
                        finalLatitude=finalLatitude+friendGPS.latitude;
                        finalLongitude=finalLongitude+friendGPS.longitude;
                        count++;
                }
                finalLatitude=finalLatitude/count;
                finalLongitude=finalLongitude/count;
                //get the geographic center
                GPSPoint finalInferredLocation=new GPSPoint(finalLatitude,finalLongitude);
                Inference_GPS_Point finalGeometricCenter=new Inference_GPS_Point(finalInferredLocation);
		method1*/

		//visualizer map
		
		//Visualizer seeMap=Visualizer.getInstance();
		//seeMap.drawFriendsMap("friends"+userId+".html",userToInfer,this.getNetwork().getGroundTruth());
		
		//visualizer map
		//return finalGeometricCenter;
		userToInfer.setHomeLocationInferred();
		userToInfer.setInferredLocation(latitude,longitude);
		return geometricCenter;
	}
    }
}
