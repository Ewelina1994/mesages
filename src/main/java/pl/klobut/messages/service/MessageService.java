package pl.klobut.messages.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klobut.messages.entity.Message;
import pl.klobut.messages.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesList() {
        //  PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        return messageRepository.findAll();
    }

    public List<Message> getTenRandomMessages() {
        return messageRepository.getRandomTenMessages();
    }

    public Optional<Message> findById(int id) {
        return messageRepository.findById(id);
    }

    public Message save(Message messageEntity) {
        return messageRepository.save(messageEntity);
    }
}
