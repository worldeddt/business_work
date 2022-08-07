package business.businesswork.vo;

import business.businesswork.domain.Section;
import business.businesswork.enumerate.ResponseStatus;
import lombok.Data;

import java.util.List;


@Data
public class AllSections {
    public Integer result = ResponseStatus.FAIL.getResultCode();
    public List<SectionVO> sectionList;
}
