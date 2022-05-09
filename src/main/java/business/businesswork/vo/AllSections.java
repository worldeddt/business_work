package business.businesswork.vo;

import business.businesswork.domain.Section;
import lombok.Data;

import java.util.List;


@Data
public class AllSections {
    public Integer result;
    public List<Section> sectionList;
}
