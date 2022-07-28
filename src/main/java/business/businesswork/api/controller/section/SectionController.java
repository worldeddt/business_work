package business.businesswork.api.controller.section;

import business.businesswork.service.section.SectionService;
import business.businesswork.vo.AllSections;
import business.businesswork.vo.ModifySection;
import business.businesswork.vo.RegisterSection;
import business.businesswork.vo.ResponseSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired(required = false)
    private SectionService sectionService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterSection registerSection) throws Exception {
        sectionService.register(registerSection);
    }

    @RequestMapping(value =  "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam("sectionId") Long sectionId) throws Exception {
        sectionService.delete(sectionId);
    }

    @RequestMapping(value =  "/update", method = RequestMethod.POST)
    public void update(@RequestBody ModifySection modifySection) throws Exception {
        sectionService.update(modifySection);
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public ResponseSection findOne(@RequestParam(required = false, name = "sectionId") Long sectionId) throws Exception {
        return sectionService.findById(sectionId);
    }

    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public AllSections findAll(@RequestParam(required = false, name = "projectId") Long projectId) throws Exception {
        return sectionService.findAll(projectId);
    }

}
