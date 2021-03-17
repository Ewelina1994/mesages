package pl.klobut.messages.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.klobut.messages.entity.Message;
import pl.klobut.messages.model.MessageModel;

import static org.junit.jupiter.api.Assertions.*;

public class MessageMapperTest {

    MessageMapper messageMapper;

    @BeforeEach
    public void before() {
        messageMapper = new MessageMapper();
    }

    @Test
    public void whenConvertMessageEntityToMessageModel_thenCorrect() {
        Message message = new Message();
        message.setContent("Hi my friends");

        MessageModel messageModel = messageMapper.convertToModel(message);
        assertEquals(message.getContent(), messageModel.getContent());
    }

    @Test
    public void whenConvertMessageModelToMessageEntity_thenCorrect() {
        MessageModel messageModel = new MessageModel();
        messageModel.setContent("Hello everyone");

        Message message = messageMapper.convertToEntity(messageModel);
        assertEquals(messageModel.getContent(), message.getContent());
    }

}
