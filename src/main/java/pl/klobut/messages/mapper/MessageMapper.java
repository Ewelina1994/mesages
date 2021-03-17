package pl.klobut.messages.mapper;

import org.springframework.stereotype.Component;
import pl.klobut.messages.entity.Message;
import pl.klobut.messages.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageMapper {
    public MessageModel convertToModel(Message message) {
        MessageModel messageModel = new MessageModel();
        messageModel.setId(message.getId());
        messageModel.setContent(message.getContent());
        return messageModel;
    }

    public Message convertToEntity(MessageModel message) {
        Message messageEntity = new Message();
        messageEntity.setContent(message.getContent());
        return messageEntity;
    }

    public List<MessageModel> convertToModelList(List<Message> messageList) {
        List<MessageModel> messageModelList = new ArrayList<>();
        for (Message m : messageList) {
            messageModelList.add(convertToModel(m));
        }
        return messageModelList;
    }

    public List<Message> convertToEntityList(List<MessageModel> messageModelListList) {
        List<Message> messageList = new ArrayList<>();
        for (MessageModel m : messageModelListList) {
            messageList.add(convertToEntity(m));
        }
        return messageList;
    }
}
