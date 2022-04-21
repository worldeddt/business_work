package business.businesswork.api.controller.section;

import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.service.section.SectionService;
import business.businesswork.vo.ModifySection;
import business.businesswork.vo.RegisterSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired(required = false)
    private SectionService sectionService;

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

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public Optional<Section> findOne(@RequestParam(required = false, name = "sectiondId") Long sectionId) throws Exception {
        return sectionService.findById(sectionId);
    }

    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public List<Section> findAll() throws Exception {
        return sectionService.findAll();
    }

}
