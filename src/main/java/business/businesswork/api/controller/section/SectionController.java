package business.businesswork.api.controller.section;

import business.businesswork.config.CommonConfig;
import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.service.section.SectionService;
import business.businesswork.util.KakaoApi;
import business.businesswork.vo.ModifySection;
import business.businesswork.vo.RegisterSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterSection registerSection) throws Exception {
        sectionService.register(registerSection);
    }

    @ResponseBody
    @RequestMapping(value =  "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam("sectionIndex") String index) throws Exception {
        sectionService.delete(index);
    }

    @RequestMapping(value =  "/update", method = RequestMethod.POST)
    public void update(@RequestBody ModifySection modifySection) throws Exception {
        sectionService.update(modifySection);
    }
}
