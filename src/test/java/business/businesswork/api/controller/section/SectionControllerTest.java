package business.businesswork.api.controller.section;

import business.businesswork.enumerate.SectionStatus;
import business.businesswork.vo.ModifySection;
import business.businesswork.vo.RegisterSection;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register() throws Exception {
        Gson gson = new Gson();
        RegisterSection registerSection = new RegisterSection();
        registerSection.setDescription("첫 번째 섹션");
        registerSection.setTitle("section title two");
        registerSection.setProjectId(1L);
        registerSection.setSectionStatus(SectionStatus.ACTIVE);

        this.mockMvc.perform(
                        post("/section/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(registerSection)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(
                        post("/section/delete").param("sectionId", "7")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        Gson gson = new Gson();
        ModifySection modifySection = new ModifySection();
        modifySection.setIndex(4L);
        modifySection.setDescription("첫 번째 섹션");
        modifySection.setTitle("나투더플라");
        modifySection.setProjectId(1L);

        this.mockMvc.perform(
                        post("/section/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(modifySection)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findOne() throws Exception {
        this.mockMvc.perform(get("/section/").param("sectionId", "3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        this.mockMvc.perform(get("/section/all").param("projectId", "2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}