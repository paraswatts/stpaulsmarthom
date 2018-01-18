package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class OrgDetailModel {

    String name;
    String phone;
    String designation;
    String orgNumber;
    String memberType;

    public OrgDetailModel(String name, String phone , String designation,String orgNumber,String memberType) {
        this.name = name;
        this.phone = phone;
        this.designation = designation;
        this.orgNumber = orgNumber;
        this.memberType = memberType;
    }


    public String getMemberType()
    {
        return memberType;
    }

    public void setMemberType(String memberType)
    {
        this.memberType = memberType;
    }
    public String getOrgNumber(){return orgNumber;}

    public void setOrgNumber(String orgNumber)
    {
        this.orgNumber= orgNumber;
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
