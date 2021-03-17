package pl.klobut.messages.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.klobut.messages.MessagesApplication;
import pl.klobut.messages.entity.Message;
import pl.klobut.messages.exception.ResourceNotFoundException;
import pl.klobut.messages.mapper.MessageMapper;
import pl.klobut.messages.model.MessageModel;
import pl.klobut.messages.service.MessageService;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    private MessageMapper messageMapper;

    private static final Logger logger = LogManager.getLogger(MessagesApplication.class);

    @Autowired
    public MessageController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping()
    public List<MessageModel> getAllMessage() {
        List<Message> messagesList = messageService.getMessagesList();

        List<MessageModel> messageMapperList = messageMapper.convertToModelList(messagesList);
        return messageMapperList;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Message> addNewMessage(@Valid @RequestBody MessageModel messageModel) {
        if (messageModel == null)
            logger.error("message is null!!!");
        else
            logger.info("{}", messageModel.toString());

        Message newMessage = messageMapper.convertToEntity(messageModel);
        HttpHeaders responseHeaders = new HttpHeaders();

        return new ResponseEntity<>(messageService.addMessage(newMessage), responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMessage(@Valid @RequestBody MessageModel messageModel, @PathVariable int id) throws ResourceNotFoundException {

        Message message = messageService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));

        message.setContent(messageModel.getContent());
        final Message updatedMessage = messageService.save(message);
        return ResponseEntity.ok(updatedMessage);
    }

    @GetMapping("/random/10")
    public ResponseEntity<List<Message>> getTenRandomMessages() {
        HttpHeaders responseHeaders = new HttpHeaders();
        List<Message> messagesList = messageService.getTenRandomMessages();
        return new ResponseEntity<List<Message>>(messagesList, responseHeaders, HttpStatus.OK);
    }

}
