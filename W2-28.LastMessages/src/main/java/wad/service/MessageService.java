package wad.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.Message;
import wad.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> list() {
        
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC, "messageDate");
        Page<Message> messagePage = messageRepository.findAll(pageable);
        return messagePage.getContent();

    }

    @Transactional
    public void addMessage(Message message) {
        messageRepository.save(message);
    }
}
