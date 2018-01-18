package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class KaisthanaSamithiModel {

    String name;
    String phone;
    String designation;
    String memberType;
    String rollno;
    String image;


    public KaisthanaSamithiModel(String name, String phone , String designation,String memberType,String rollno,String image) {
        this.name = name;
        this.phone = phone;
        this.designation = designation;
        this.memberType = memberType;
        this.rollno = rollno;
        this.image = image;
    }

    public String getImage(){return  image;}
    public void setImage(String image){
        this.image = image;
    }
    public String getRollno(){return rollno;}
    public void setRollno(String rollno){this.rollno = rollno;}
    public String getMemberType()
    {
        return memberType;
    }

    public void setMemberType(String memberType)
    {
        this.memberType = memberType;
    }

    public String getName(){return name;}

    public void setName(String name)
    {
        this.name= name;
    }


    public String getPhone(){return phone;}

    public void setPhone(String phone)
    {
        this.phone= phone;
    }


    public String getDesignation(){return designation;}

    public void setDesignation(String designation)
    {
        this.designation= designation;
    }



}
