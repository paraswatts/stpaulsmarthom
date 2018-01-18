package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class ContactUsModel {

    String contactPhone;
    String contactEmail;
    String contactAddress;

    String contactImage;


    public String getContactPhone(){return contactPhone;}

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail(){return contactEmail;}

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getContactAddress(){return contactAddress;}

    public void setContactAddress(String contactAddress)
    {
        this.contactAddress = contactAddress ;
    }

    public String getContactImage(){return contactImage;}

    public void setContactImage(String contactImage)
    {
        this.contactImage = contactImage ;
    }


}
