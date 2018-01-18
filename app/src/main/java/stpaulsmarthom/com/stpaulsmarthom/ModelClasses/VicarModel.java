package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class VicarModel {

    String our_vicar;
    String image_url;
    String vicar_title;

    public VicarModel(String our_vicar, String image_url,String vicar_title) {
        this.our_vicar = our_vicar;
        this.image_url = image_url;
        this.vicar_title = vicar_title;
    }

    public String getVicar_title(){return vicar_title;}

    public void setVicar_title(String vicar_title){this.vicar_title = vicar_title;}

    public String getOur_vicar(){return our_vicar;}

    public void setOur_vicar(String our_vicar)
    {
        this.our_vicar = our_vicar;
    }

    public String getImage_url(){return image_url;}

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }
}
