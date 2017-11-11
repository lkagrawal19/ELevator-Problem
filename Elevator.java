import java.util.*;

class Person
{
   private int source;
   private int destination;
   private int waittime;
   private int traveltime;
   private int arrivalTime;
   private int personId;
   private int processingtime;

    public int getProcessingtime() {
        return processingtime;
    }

    public void setProcessingtime(int processingtime) {
        this.processingtime = processingtime;
    }
   Person(int source, int destination, int arrivalTime,int personId)
    {
        this.source  = source;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.personId = personId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getWaittime() {
        return waittime;
    }

    public void setWaittime(int waittime) {
        this.waittime = waittime;
    }

    public int getPersonId() {
        return personId;
    }

    public int getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(int traveltime) {
        this.traveltime = traveltime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
   
   
}

class Lift
{
    private int floor; // current floor of list
    private int direction;  // 0 for idle state , 1 for going Up, 2 for going down
    private ArrayList<Person> persons = new ArrayList<>();  // contains the persons in the lift in certain time
    private ArrayList<Integer> stop = new ArrayList<>();  // contain floor at which lift can stop
    private int StoppageTime=0;  //time eclapse in indle state
    private int movingtoServe=0; //if Lift is empty and a request from other floor then its value will be 1, once the service has been made it will be 0
    
     Lift(int floor , int direction)
    {
       this.floor = floor;
       this.direction = direction;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
   
    public ArrayList<Integer> getStop() {
        return stop;
    }

    public void setStop(ArrayList<Integer> stop) {
        this.stop = stop;
    }
   
    int getFloor()
    {
        return floor;
    }
    
    int getDirection()
    {
      return direction;
    }
    
    void addPerson(Person person)
    {
      
       persons.add(person);
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public int getStoppageTime() {
        return StoppageTime;
    }

    public void setStoppageTime(int StoppageTime) {
        this.StoppageTime = StoppageTime;
    }

    public int getMovingtoServe() {
        return movingtoServe;
    }

    public void setMovingtoServe(int movingtoServe) {
        this.movingtoServe = movingtoServe;
    }

    
    void removePerson(Person p)
    {
       persons.remove(p);
    }
}

class Algo
{
    // Function to partition the the floors into sectors
    void createPartition(int nol,int nof, HashMap<Integer,Lift> lift)
    {
      final int quotient = nof/ nol;
      final int remainder = nof % nol;
      int [] results = new int[nol];
      for( int i = 0; i < nol; i++ ) 
        {
            results[i] = i < remainder ? quotient + 1 : quotient;
        }
      int floorcount=0;
      for(int i=0;i<results.length;i++)
        {
            int temp=results[i];
            for(int j=0;j<temp;j++)
            {   
                Lift temp3=lift.get(i);
                ArrayList<Integer> temp4=temp3.getStop();
                temp4.add(floorcount);
                floorcount++;
            }
        }
        for(int i =1;i<nol;i++)
        { 
          Lift temp3=lift.get(i);
          ArrayList<Integer> temp4=temp3.getStop();
          temp4.add(0);
        }
    }
    
 // It will create a HashMap to store the information of persons on every floor   
 HashMap<Integer,ArrayList<Person>> storePersonCallFloorWise(int arr[][],int nop)
    {
      HashMap<Integer,ArrayList<Person>> a1= new HashMap<>();
      for(int i=0;i<nop;i++)
      {
         if(a1.containsKey(arr[i][0]))
         {  
             ArrayList<Person> temp = a1.get(arr[i][0]);
             Person temp2 = new Person(arr[i][0],arr[i][1],arr[i][2],i); 
             temp.add(temp2);
             a1.put(arr[i][0],temp);
             
         }
         else
         {
             ArrayList<Person> temp = new ArrayList<>();
             Person temp2 = new Person(arr[i][0],arr[i][1],arr[i][2],i);
             temp.add(temp2);
             a1.put(arr[i][0],temp);
             
         }
      }  
      return a1;
    }
    
    public static void main(String args[])
    {
      Scanner sc = new Scanner(System.in);
      int nof = sc.nextInt(); // Number of Floor
      int nol = sc.nextInt();  // Number of Lifts
      int nop = sc.nextInt();  // Number of Persons
      int part = (int)nol*30/100; //30% of lift will serve all the floors, rest 70% will serve specific floors
      if(part==0)
      {
        part=1;
      }
      nol=nol-part;
      
      ArrayList<Person> logger = new ArrayList<>();
      int arr[][]=new int[nop][3]; // array taking input as source,destination and arrival time
      for(int i=0;i<nop;i++)
      {
         arr[i][0] = sc.nextInt();
         arr[i][1] = sc.nextInt();
         arr[i][2] = sc.nextInt();
      }
      int maxTime =86400;// equals 1 Day
      HashMap<Integer,Lift> lift = new HashMap<>();     //Key as Lift Number Value  as Lift             
        
      for(int i=0;i<nol;i++)
      {
          lift.put(i,new Lift(0,0));
      }  
      
     Algo a = new Algo();  // Object Creation
     a.createPartition(nol, nof, lift); //calling create Partition method
     HashMap<Integer,ArrayList<Person>> a1 =a.storePersonCallFloorWise(arr, nop); //creating Hashmap with key as floor number value as array of persons who raise a request from that floor
    //----------------------Will put left over 30% lift stoppage floors---------------------
     for(int i=nol;i<nol+part;i++)
     {
        Lift l = new Lift(0,0);
        ArrayList<Integer> temp=l.getStop();
        for(int j=0;j<nof;j++)
        {
          temp.add(j);
        }
        lift.put(i,l);
     }
    //-------------------------------------------------------- 
      
     nol=nol+part;
     
     //Printing the floors on which a lift stops
     for(int i=0;i<nol;i++)
     {
       System.out.println("Lift "+(i+1)+" will stop on "+lift.get(i).getStop());
     }
     //-----------------------------------
      int time =0;// initializing time as zero
      while(!a1.isEmpty() || time <maxTime)
      {     
           /*--------------------------------------------PickUp the person-------------------------------------------------------------*/
          //The person whose time is less than the current time and if any lift is available to process them, then that lift will pick the person
          if(!a1.isEmpty())
          {
          for(int i=0;i<nof;i++) 
          {
               if(a1.containsKey(i)) //if a1 contains that particular floor
                {
                 ArrayList<Person> temp = a1.get(i); // then iterate through the arraylist
                 Iterator<Person> t = temp.iterator();
                 {
                  while(t.hasNext())
                    {
                     Person x = (Person)t.next();        
                     if(x.getArrivalTime()<=time)
                     {
                         int source = x.getSource();
                         int destination = x.getDestination(); 
                         for(int k=0;k<nol;k++)
                         {
                             if((lift.get(k).getStop().contains(source) && lift.get(k).getStop().contains(destination)))
                            {   
                                   
                              if(lift.get(k).getDirection()==0)
                              {
                               
                                  if(lift.get(k).getFloor()==source)
                                  {
                                       if(source>destination)
                                       {
                                         lift.get(k).setDirection(2);
                                       }
                                       else
                                       {
                                           lift.get(k).setDirection(1);
                                       }
                                       lift.get(k).setStoppageTime(5);
                                       
                                       lift.get(k).addPerson(x);
                                       
                                       
                                       x.setWaittime(time - x.getArrivalTime()); 
                                       x.setProcessingtime(time);
                                       logger.add(x);
                                       t.remove();
                                       break;
                                  }
                                  else if(source>lift.get(k).getFloor())
                                  {
                                     
                                   
                                    lift.get(k).setDirection(1);
                                    lift.get(k).setMovingtoServe(1);
                                    break;
                                  }
                                  else
                                  {
                                    lift.get(k).setDirection(2);
                                    lift.get(k).setMovingtoServe(1);
                                    break;
                                  }
                                  
                              }
                              if(lift.get(k).getDirection()==1)
                              {
                                 if(lift.get(k).getFloor()==source)
                                 { 
                                   if(source < destination)
                                   {
                                      lift.get(k).addPerson(x);
                                      x.setWaittime(time - x.getArrivalTime());
                                      x.setProcessingtime(time);
                                      logger.add(x);
                                      
                                      t.remove();
                                      if(lift.get(k).getStoppageTime()==0)
                                      {
                                        lift.get(k).setStoppageTime(5);
                                      }
                                      if(lift.get(k).getMovingtoServe()==1)
                                      {
                                        lift.get(k).setMovingtoServe(0);
                                      }
                                      break;
                                   }
                                   else if(source>destination && lift.get(k).getPersons().isEmpty())
                                   {
                                      lift.get(k).addPerson(x);
                                      
                                      lift.get(k).setDirection(2);
                                      x.setWaittime(time - x.getArrivalTime());
                                      x.setProcessingtime(time);
                                       logger.add(x);
                                      
                                      t.remove();
                                      if(lift.get(k).getStoppageTime()==0)
                                      {                             
                                        lift.get(k).setStoppageTime(5);
                                      }
                                      if(lift.get(k).getMovingtoServe()==1)
                                      {
                                        lift.get(k).setMovingtoServe(0);
                                      }
                                      break;  
                                   }
                                 }
                              }
                               if(lift.get(k).getDirection()==2)
                              {
                                 
                                 if(lift.get(k).getFloor()==source)
                                 {
                                   if(source > destination)
                                   {
                                      lift.get(k).addPerson(x);
                                      
                                      x.setWaittime(time - x.getArrivalTime()); 
                                      x.setProcessingtime(time);
                                       logger.add(x);
                                     
                                      t.remove();
                                      if(lift.get(k).getStoppageTime()==0 && source!=lift.get(k).getFloor())
                                      {
                                        lift.get(k).setStoppageTime(5);
                                      }
                                      if(lift.get(k).getMovingtoServe()==1)
                                      {
                                        lift.get(k).setMovingtoServe(0);
                                      }
                                      break;
                                   }
                                    else if(source>destination && lift.get(k).getPersons().isEmpty())
                                   {
                                      lift.get(k).addPerson(x);
                                      
                                      x.setWaittime(time - x.getArrivalTime());
                                      x.setProcessingtime(time);
                                       logger.add(x);
                                      lift.get(k).setDirection(1);
                                      
                                      t.remove();
                                      if(lift.get(k).getStoppageTime()==0 && source!=lift.get(k).getFloor())
                                      {
                                        lift.get(k).setStoppageTime(5);
                                      }
                                      if(lift.get(k).getMovingtoServe()==1)
                                      {
                                        lift.get(k).setMovingtoServe(0);
                                      }
                                      break;  
                                   }
                                   
                                 }
                              }
                              
                            }
                              
                         }
                 
                         }
                     }
                 }
                if(a1.get(i).isEmpty())
                 {
                    a1.remove(i);
                 }
                }
          }
               
              }  
           /*--------------------------------------------End of code PickUp the person------------------------------------------*/
          /* --------------------------------------------Code for drop of persons-----------------------------------------------*/
                for(int k=0;k<nol;k++)
                {
                   
                   ArrayList<Person> p = lift.get(k).getPersons();
                    Iterator<Person> t = p.iterator();
                    {
                    while(t.hasNext())
                      {
                       Person x = (Person)t.next();
                       if(x.getDestination()==lift.get(k).getFloor())
                       {
                           if(lift.get(k).getStoppageTime()==0)
                           {
                              x.setTraveltime(time - x.getProcessingtime());
                              t.remove();
                              lift.get(k).setStoppageTime(5);
                           }
                           else
                           {
                               x.setTraveltime(time - x.getProcessingtime());
                               t.remove();
                           }
                       }
                     }
                  }
               }
             //End code for drop
               for(int k=0;k<nol;k++)
                {
                      if(lift.get(k).getPersons().isEmpty()) 
                    {
                      if(lift.get(k).getMovingtoServe()!=1)
                      {
                         lift.get(k).setDirection(0);
                      }
                     
                    }
                   if(lift.get(k).getDirection()==0 && lift.get(k).getFloor()>=0 && lift.get(k).getFloor()<=nof)
                    {
                      if(lift.get(k).getStoppageTime()>0)
                      {
                        lift.get(k).setStoppageTime(lift.get(k).getStoppageTime()-1);
                      }
                    }
                   // System.out.println(lift.get(k).floor);                  
                    if(lift.get(k).getDirection()==1 && lift.get(k).getFloor()>=0 && lift.get(k).getFloor()<=nof)
                    {
                     if(lift.get(k).getStoppageTime()>0)
                      {
                        lift.get(k).setStoppageTime(lift.get(k).getStoppageTime()-1);
                      }
                      else
                      {
                        lift.get(k).setFloor(lift.get(k).getFloor()+1);
                      }
                    }
                    if(lift.get(k).getDirection()==2 && lift.get(k).getFloor()>0 && lift.get(k).getFloor()<=nof)
                    {
                     if(lift.get(k).getStoppageTime()>0)
                      {
                        lift.get(k).setStoppageTime(lift.get(k).getStoppageTime()-1);
                      }
                      else
                      {
                        lift.get(k).setFloor(lift.get(k).getFloor()-1);
                      }
                    }
                }
               time++; 
                }
      
      
              System.out.println("------------------------------------Person in the order they picked Up----------------------------");
               for(int k=0;k<nop;k++)
                {
                    logger.get(k).getWaittime();
                    System.out.println("------------------------------------------------------------------------------------------");
                    int val =logger.get(k).getPersonId()+1;
                    System.out.println("Person ID :"+val);
                    System.out.println("Waiting time ="+logger.get(k).getWaittime());
                    System.out.println("Travel time ="+logger.get(k).getTraveltime());
                   
                }     
    }          
}
