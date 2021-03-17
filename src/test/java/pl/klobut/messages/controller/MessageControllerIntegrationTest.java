package pl.klobut.messages.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.klobut.messages.entity.Message;
import pl.klobut.messages.model.MessageModel;
import pl.klobut.messages.service.MessageService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageService messageService;

    @Test
    public void shouldReturnAllMessages() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/message"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        MessageModel[] messageModels = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageModel[].class);

        Assertions.assertEquals("Hey this is Varun", messageModels[0].getContent());
        Assertions.assertEquals(14, messageModels.length);
    }

    @Test
    public void testUpdateMessage() throws Exception {
        Message updateMessage = new Message("change");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/message/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateMessage))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MessageModel messageModels = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageModel.class);
        Integer idMessage = (Integer) messageModels.getId();

        Optional<Message> findMessage = messageService.findById(idMessage);

        assertThat(findMessage.get().getContent()).isEqualTo(updateMessage.getContent());
    }

    @Test
    public void getRandomMessagesShouldReturnTenMessages() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/message/random/10"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        MessageModel[] messageModels = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageModel[].class);

        Assertions.assertEquals(10, messageModels.length);
    }

    @Test
    public void getRandomMessagesShouldNotHaveDuplicate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/message/random/10"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        MessageModel[] messageModels = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageModel[].class);
        List<MessageModel> messageModelList = Arrays.asList(messageModels);
        //alphabetical sorting by content
        messageModelList.stream()
                .sorted((object1, object2) -> object1.getContent().compareTo(object2.getContent()));
        boolean isLIstHasDuplicate = false;
        //checking if there are duplicates in the sorted list
        for (int i = 0; i < messageModelList.size() - 1; i++) {
            if (messageModelList.get(i).equals(messageModelList.get(i + 1))) {
                isLIstHasDuplicate = true;
                break;
            }
        }
        Assertions.assertFalse(isLIstHasDuplicate);
    }
}
