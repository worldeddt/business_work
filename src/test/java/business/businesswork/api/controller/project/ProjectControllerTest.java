package business.businesswork.api.controller.project;

import business.businesswork.vo.RegistProject;
import com.google.gson.Gson;
import com.sun.xml.internal.ws.encoding.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        registProject.setTitle("첫 번째 프로젝트");
        registProject.setDescription("");

        this.mockMvc.perform(
                post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(registProject)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}