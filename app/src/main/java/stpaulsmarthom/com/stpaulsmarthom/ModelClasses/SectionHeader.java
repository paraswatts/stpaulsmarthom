package stpaulsmarthom.com.stpaulsmarthom.ModelClasses;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

/**
 * Created by Paras-Android on 27-12-2017.
 */

public class SectionHeader implements Section<OrgDetailModel>, Comparable<SectionHeader> {

    List<OrgDetailModel> childList;
    String sectionText;
    int index;

    public SectionHeader(List<OrgDetailModel> childList, String sectionText, int index) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.index = index;
    }

    @Override
    public List<OrgDetailModel> getChildItems() {
        return childList;
    }

    public String getSectionText() {
        return sectionText;
    }

    @Override
    public int compareTo(SectionHeader another) {
        if (this.index > another.index) {
            return -1;
        } else {
            return 1;
        }
    }
}