package business.businesswork.api.controller.project;

import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.vo.CommonResponse;
import business.businesswork.vo.ModifyProject;
import business.businesswork.vo.RegistProject;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.ws.Response;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register() throws Exception {
        Gson gson = new Gson();
        RegistProject registProject = new RegistProject();
        registProject.setTitle("첫번째 프로젝트");
        registProject.setDescription("첫번째 첫번째");

        this.mockMvc.perform(
                post("/project/register")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(registProject)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProject() throws Exception {
        Gson gson = new Gson();
        this.mockMvc.perform(
                post("/project/delete").param("projectId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                        .andExpect(content().json("{'result' : 1, 'message': ''}"));
    }

    @Test
    public void findOne() throws Exception {
        this.mockMvc.perform(post("/project/template").param("projectId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        this.mockMvc.perform(post("/project/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        Gson gson = new Gson();
        ModifyProject modifyProject = new ModifyProject();
        modifyProject.setIndex(2L);
        modifyProject.setTitle("첫 번째 프로젝트");
        modifyProject.setDescription("천재노창");

        this.mockMvc.perform(
                        post("/project/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(modifyProject)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test() throws Exception  {
        this.mockMvc.perform(
                post("/test")
        ).andDo(print());
    }
}