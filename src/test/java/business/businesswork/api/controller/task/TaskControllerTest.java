package business.businesswork.api.controller.task;

import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.vo.ModifyTask;
import business.businesswork.vo.RegisterTask;
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
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void register() throws Exception {
//        Gson gson = new Gson();
//        RegisterTask registerTask = new RegisterTask();
//        registerTask.setDescription("할일1");
//        registerTask.setTitle("안녕 첫 번째 일이야");
//        registerTask.setSectionId(1L);
//
//        this.mockMvc.perform(
//                        post("/task/register")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(gson.toJson(registerTask)))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void update() throws Exception {
//        Gson gson = new Gson();
//        ModifyTask modifyTask = new ModifyTask();
//        modifyTask.setIndex(1L);
//        modifyTask.setTitle("업데이트 데스크");
//        modifyTask.setDescription("업데이트");
//        modifyTask.setSectionId(1L);
//        modifyTask.setStatus(TaskStatusType.DOING);
//
//        this.mockMvc.perform(post("/task/update").contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(modifyTask)))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void delete() throws Exception {
//        this.mockMvc.perform(post("/task/delete").param("taskIndex", "1"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void findById() throws Exception {
//        this.mockMvc.perform(get("/task/").param("taskIndex", "1"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}