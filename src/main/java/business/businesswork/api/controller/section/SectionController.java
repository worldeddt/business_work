package business.businesswork.api.controller.section;

import business.businesswork.service.section.SectionService;
import business.businesswork.vo.*;
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
    public CommonResponse register(@RequestBody RegisterSection registerSection) throws Exception {
        return sectionService.register(registerSection);
    }

    @RequestMapping(value =  "/delete", method = RequestMethod.POST)
    public CommonResponse delete(@RequestParam("sectionId") Long sectionId) throws Exception {
        return sectionService.delete(sectionId);
    }

    @RequestMapping(value =  "/update", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody ModifySection modifySection) throws Exception {
        return sectionService.update(modifySection);
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public ResponseSection findOne(@RequestParam(required = false, name = "sectionId") Long sectionId) throws Exception {
        return sectionService.findById(sectionId);
    }

    @RequestMapping(value = "/allByProjectId", method = {RequestMethod.POST})
    public AllSections findAllByProjectId(@RequestParam(required = false, name = "projectId") Integer projectId) throws Exception {
        return sectionService.findAllByProjectId(projectId);
    }
}
