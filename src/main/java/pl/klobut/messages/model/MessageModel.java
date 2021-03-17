package pl.klobut.messages.model;


import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MessageModel {

    private Integer id;
    @NotEmpty(message = "Please enter content")
    @NotBlank(message = "Please enter content")
    @NotNull
    private String content;

    public MessageModel() {
    }

    public MessageModel(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
