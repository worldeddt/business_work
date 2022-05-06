package business.businesswork.service.task;

import business.businesswork.vo.RegistProject;
import business.businesswork.vo.RegisterTask;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void register() throws Exception {
        Gson gson = new Gson();
        RegisterTask registerTask = new RegisterTask();
        registerTask.setDescription("dddd");
        registerTask.setTitle("dddd");
        registerTask.setSectionId(1L);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/task/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(registerTask)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }
}