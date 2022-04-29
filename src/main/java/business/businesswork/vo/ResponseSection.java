package business.businesswork.vo;

import business.businesswork.domain.Section;
import lombok.Data;

import java.util.List;

@Data
public class ResponseSection {
    public Integer result;
    public List<Section> sectionList;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }
}
