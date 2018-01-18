package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class BishopModel {

    String name;
    String phone;
    String address;
    String email;
    String about;
    String image;
    String dob;

    public BishopModel(String name, String phone ,String address, String email,String image,String dob) {
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.dob = dob;

    }
    public String getDob(){return  dob;}

    public void setDob(String dob){this.dob = dob;}

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


    public String getAddress(){return address;}

    public void setAddress(String address)
    {
        this.address= address;
    }


    public String getEmail(){return email;}

    public void setEmail(String email)
    {
        this.email= email;
    }


    public String getAbout(){return about;}

    public void setAbout(String about)
    {
        this.about= about;
    }


    public String getImage_url(){return image;}

    public void setImage_url(String image)
    {
        this.image = image;
    }
}
