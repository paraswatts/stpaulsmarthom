package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class ChildModel {

    private String childname;

    private String childinbaharian;

    private String sexchild;

    private String datebrithchild;

    private String bloodchild;

    private String studentchild;

    private String employechild;

    private String childsimage;

    private String employername;

    private String rollno;

    public ChildModel(String childname,
                      String childinbaharian ,
                      String sexchild,
                      String datebrithchild,
                      String bloodchild,
                      String studentchild,
                      String employechild,
                      String childsimage,
                      String employername,
                      String rollno
                             ) {
        this.childname = childname;
        this.childinbaharian = childinbaharian;
        this.sexchild = sexchild;
        this.datebrithchild = datebrithchild;
        this.bloodchild = bloodchild;
        this.studentchild = studentchild;
        this.employechild = employechild;
        this.childsimage = childsimage;
        this.employername = employername;
        this.rollno = rollno;

    }

    public String getRollno(){return rollno;}

    public void setRollno(String rollno){
        this.rollno = rollno;
    }

    public String getChildname ()
    {
        return childname;
    }

    public void setChildname (String childname)
    {
        this.childname = childname;
    }



    public String getChildinbaharian ()
    {
        return childinbaharian;
    }

    public void setChildinbaharian (String childinbaharian)
    {
        this.childinbaharian = childinbaharian;
    }


    public String getSexchild ()
    {
        return sexchild;
    }

    public void setSexchild (String sexchild)
    {
        this.sexchild = sexchild;
    }

    public String getDatebrithchild ()
    {
        return datebrithchild;
    }

    public void setDatebrithchild (String datebrithchild)
    {
        this.datebrithchild = datebrithchild;
    }

    public String getBloodchild ()
    {
        return bloodchild;
    }

    public void setBloodchild (String bloodchild)
    {
        this.bloodchild = bloodchild;
    }

    public String getStudentchild ()
    {
        return studentchild;
    }

    public void setStudentchild (String studentchild)
    {
        this.studentchild = studentchild;
    }

    public String getEmployechild ()
    {
        return employechild;
    }

    public void setEmployechild (String employechild)
    {
        this.employechild = employechild;
    }

    public String getChildsimage ()
    {
        return childsimage;
    }

    public void setChildsimage (String childsimage)
    {
        this.childsimage = childsimage;
    }

    public String getEmployername ()
    {
        return employername;
    }

    public void setEmployername (String employername)
    {
        this.employername = employername;
    }


}
