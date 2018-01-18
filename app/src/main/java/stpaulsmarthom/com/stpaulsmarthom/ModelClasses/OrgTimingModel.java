package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class OrgTimingModel {

    String name;
    String phone;
    String timing_number;
    String member_type;

    public OrgTimingModel(String name, String phone,String timing_number,String member_type ) {
        this.name = name;
        this.phone = phone;
        this.timing_number = timing_number;
        this.member_type = member_type;
    }


    public String getTiming_number(){return timing_number;}

    public  void setTiming_number(String timing_number){this.timing_number = timing_number;}

    public  String getMember_type(){return member_type;}

    public void setMember_type(String member_type){this.member_type = member_type;}

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


}
