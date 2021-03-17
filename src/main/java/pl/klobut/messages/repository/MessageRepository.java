package pl.klobut.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.klobut.messages.entity.Message;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT hex(id), content FROM message", nativeQuery = true)
    public List<Message> findAllMessages();

    @Query(value = "SELECT id, content FROM message \n" +
            "ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
    public List<Message> getRandomTenMessages();
}
