package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class AboutUsModel {

    String aboutUsContent;
    String aboutUsImage;
    String churchTimeEnglish;
    String churchTimeMalayalam;

    public String getChurchTimeEnglish(){return churchTimeEnglish;}
    public void setChurchTimeEnglish(String churchTimeEnglish){this.churchTimeEnglish = churchTimeEnglish;}

    public String getChurchTimeMalayalam(){return churchTimeMalayalam;}
    public void setChurchTimeMalayalam(String churchTimeMalayalam){this.churchTimeMalayalam = churchTimeMalayalam;}


    public String getAboutUsImage(){return aboutUsImage;}

    public void setAboutUsImage(String aboutUsImage){
        this.aboutUsImage = aboutUsImage;
    }

    public String getAboutUsContent(){return aboutUsContent;}

    public void setAboutUsContent(String aboutUsContent)
    {
        this.aboutUsContent= aboutUsContent;
    }



}
