package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

/**
 * Created by Paras-Android on 27-12-2017.
 */

public class SectionHeaderKais implements Section<KaisthanaSamithiModel>, Comparable<SectionHeaderKais> {

    List<KaisthanaSamithiModel> childList;
    String sectionText;
    int index;

    public SectionHeaderKais(List<KaisthanaSamithiModel> childList, String sectionText, int index) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.index = index;
    }

    @Override
    public List<KaisthanaSamithiModel> getChildItems() {
        return childList;
    }

    public String getSectionText() {
        return sectionText;
    }

    @Override
    public int compareTo(SectionHeaderKais another) {
        if (this.index > another.index) {
            return -1;
        } else {
            return 1;
        }
    }
}