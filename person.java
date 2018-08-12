
/*

Author   : Aung Aye Than 
Date     : 29th March 2018.
Filename : person.java
Version  : 1.0

*/


import java.io.IOException;//import package

import java.io.Serializable;//import package
import java.util.ArrayList;//import package


/**
 *
 * @author aungayethan
 */
public class person implements Serializable //class person
{
    private String firstName; //private string variable that is used to store data
    private String surnameBirth;//private string variable that is used to store data
    private String surnameMarriage;//private string variable that is used to store data
    private String gender;//private string variable that is used to store data
    private String lifeDescription;//private string variable that is used to store data
    private int addressStreetNo;//private int variable that is used to store data
    private String addressStreetName;//private string variable that is used to store data
    private String suburb;//private string variable that is used to store data
    private int postcode;//private int variable that is used to store data
    
    private String father;//private string variable that is used to store data
    private String mother;//private string variable that is used to store data
    private String children;//private string variable that is used to store data
    private String Spouse; //private string variable that is used to store data
    
    
    private person spouse;// person object to store spouse data
    private person fatherData;// person object to store father data
    private person motherData;// person object to store mother data
    
    public ArrayList<person> grandChildrenLists  = new ArrayList<person>(); //person arraylist that is used to store grand child data
    public ArrayList<person> childrenList = new ArrayList<person>();//person arraylists that is used to store children data
    public ArrayList<person> childSpouse = new ArrayList<person>();//person arraylists that is used to store children data
    
    public person()//default constructor
    {
        
    }
    
    public person( String firstName, String surnameBirth, String surnameMarriage, String gender, String lifeDesc, int addNo, String addStrName, String suburb, int postcode) //constructor with parameter
    {
        this.firstName = firstName;
        this.surnameBirth = surnameBirth;
        this.surnameMarriage = surnameMarriage;
        this.gender = gender;
        this.lifeDescription = lifeDesc;
        this.addressStreetNo= addNo;
        this.addressStreetName = addStrName;
        this.suburb = suburb;
        this.postcode = postcode;
       
    }
    
    
    //method which is used to store data
    public void createPerson(String firstName, String surnameBirth, String surnameMarriage, String gender, String lifeDesc, int addNo, String addStrName, String suburb, int postcode) throws IOException,NumberFormatException
    {
        this.firstName = firstName;//set the valuse for first name
        this.surnameBirth = surnameBirth;//set the valuse for first name
        this.surnameMarriage = surnameMarriage;//set the valuse for first name
        this.gender = gender;//set the valuse for first name
        this.lifeDescription = lifeDesc;//set the valuse for first name
        this.addressStreetNo= addNo;//set the valuse for first name
        this.addressStreetName = addStrName;//set the valuse for first name
        this.suburb = suburb;//set the valuse for first name
        this.postcode = postcode;//set the valuse for first name
        
    }
    
   
    
    
    
    
    //Setter to set the value for firstname
    public void setFirstname(String firstName)
    {
        this.firstName = firstName;
    }
    //setter to set the value for surname 
    public void setSurnameBirth (String surnameBirth) 
    {
        this.surnameBirth = surnameBirth;
    }
    //setter to set the value for marriage name
    public void setsurnameMarriage(String surnameMarriage)
    {
        this.surnameMarriage = surnameMarriage;
    }
    //setter to set the value for gender
    public void setGender (String gender)
    {
        this.gender = gender;
    }
    //setter to set the value for life description 
    public void setlifeDescription (String lifeDescription)
    {
        this.lifeDescription = lifeDescription;
    }
    
    //setter to set the value for street number
    public void setaddressStreetNo (int addressStreetNo)
    {
        this.addressStreetNo = addressStreetNo;
    }
    
    //setter to set the value for street name
    public void setaddressStreetName (String addressStreetName)
    {
        this.addressStreetName = addressStreetName;
    }
    
    //setter to set the value for suburb
    public void setsuburb (String suburb)
    {
        this.suburb = suburb;
    }
    
    //setter to set the value for postcode number
    public void setpostcode (int postcode)
    {
        this.postcode= postcode;
    }
    
    //setter to set the value for father name
    public void setfather (String father)
    {
        this.father = father;
    }
    
    //setter to set the value for mother name
    public void setmother (String mother)
    {
        this.mother = mother;
    }
    //setter to set the value for spouse name
    public void setSpouse(String Spouse)
    {
         this.Spouse = Spouse;
    }
    //setter to set the value for children name
    public void setChildren(String child)
    {
         this.children = child;
    }
     
    //getter to return the firstname
    public String getFirstname()
    {
        return firstName;
    }
    //Getter to return the surname
    public String getsurnameBirth()
    {
        return surnameBirth;
    }
    //Getter to return the marraige name data
    public String getsurnameMarriage()
    {
        return surnameMarriage;
    }
    //Getter to return the gender 
    public String getGender()
    {
        return gender;
    }
    
    //Getter to return the life description 
    public String getlifeDescription()
    {
        return lifeDescription;
    }
    
    //Getter to return the street number
    public int getaddressStreetNo()
    {
        return addressStreetNo;
    }
    
    //Getter to return the street name
    public String getaddressStreetName()
    {
        return addressStreetName;
    }
    
    //Getter to return the suburb data
    public String getsuburb()
    {
        return suburb;
    }
    
    //Getter to return the postcode 
    public int getpostcode()
    {
        return postcode;
    }
    
    //Getter to return the father name data
    public String getfather()
    {
        return father;
    }
    
    //Getter to return the mother name data
    public String getMother()
    {
        return mother;
    }
    
    //Getter to return the children name data
    public String getChildren()
    {
        return children;
    }
    
    //Getter to return the children object data
    public person getChildrens(int i)
    {
        return childrenList.get(i);
    }
    
    
    //Getter to return the size of children data
    public int getChildrenListSize()
    {
        return childrenList.size();
    }

 
    //Getter to return the spouse name
    public String getspouse()
    {
        return Spouse;
    } 
    
    // method to add spouse object
    public void addSpouseData(person spouse)
    {
        this.spouse = spouse;
    } 
    
    //method to add father object
    public void addFatherData(person fatherData)
    {
        this.fatherData = fatherData;
    }
    
    //method to add mother object
    public void addMotherData(person motherData)
    {
        this.motherData = motherData;
    }
    
    //method to add children object
     public void addChildren(person child)  
    {
        childrenList.add(child);
    }
     
     //method to add grandchildren object
    public void addGrandChildren(person grandChildren)
    {
        this.grandChildrenLists.add(grandChildren) ;
    }
    
    //method to add child-spouse object
    
    public void addChildSpouse (person childSpouse)
    {
        this.childSpouse.add(childSpouse);
    }
    
    
    //method to return the grandchildren object 
    public person getgrandChildren(int i)
    {
        return grandChildrenLists.get(i);
    }
    
    public person getchildSpouse(int i)
    {
        return childSpouse.get(i);
    }
    
    //Getter to return the size of children data
    public int getgrandChildrenListSize()
    {
        return grandChildrenLists.size();
    }
    
    public int getChildSpouseSize()
    {
        return childSpouse.size();
    }
    
    //Getter to return the father object
    public person getFatherData()
    {
        return fatherData;
    }
    //Getter to return the mother object
    public person getMotherData()
    {
        return motherData;
    }
    //Getter to return the spouse object
    public person getSpouseData()
    {
        return spouse;
    }
    //Getter to return the child spouse object
    
    
    
    //toString method to get the name of object
    public String toString()
    {
        return getFirstname();
    }
    
}