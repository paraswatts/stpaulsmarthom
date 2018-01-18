package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by moody on 24/12/17.
 */

public class ParishBulletinModel {

    String name;
    String image_path;

    String file_path;

    public ParishBulletinModel(String name, String image_path ,String file_path) {
        this.name = name;
        this.image_path = image_path;
        this.file_path = file_path;

    }

    public String getImage_path(){return image_path;}
    public void setImage_path(String image_path)
    {
        this.image_path = image_path;
    }
    public String getName(){return name;}

    public void setName(String name)
    {
        this.name= name;
    }


    public String getFile_path(){return file_path;}

    public void setFile_path(String file_path)
    {
        this.file_path= file_path;
    }



}
