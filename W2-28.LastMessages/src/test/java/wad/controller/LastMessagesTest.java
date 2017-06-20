package wad.controller;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import wad.domain.Message;
import wad.repository.MessageRepository;
import wad.service.MessageService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Points("28")
public class LastMessagesTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void returnsTenMessages() throws Exception {
        messageRepository.deleteAll();

        for (int i = 0; i < 20; i++) {
            Message msg = new Message();
            String text = UUID.randomUUID().toString().substring(0, 6);
            msg.setMessage(text);

            messageService.addMessage(msg);
        }

        assertEquals("Make sure that the list method of MessageService returns only ten messages.", 10, messageService.list().size());
    }

    @Test
    public void returnsTenLatestMessages() throws Exception {
        messageRepository.deleteAll();

        Set<String> textContent = new HashSet<>();
        for (int i = 20; i > 0; i--) {
            Message msg = new Message();
            String text = UUID.randomUUID().toString().substring(0, 6);
            msg.setMessage(text);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_YEAR, i + 1);

            msg.setMessageDate(cal.getTime());

            if (i > 10) {
                textContent.add(text);
            }

            messageService.addMessage(msg);
        }

        List<Message> messages = messageService.list();
        assertEquals("Make sure that the list method of MessageService returns only ten messages.", 10, messages.size());

        for (Message message : messages) {
            textContent.remove(message.getMessage());
        }

        assertTrue("Make sure that the messages are ordered by date; the newest messages should be shown.", textContent.isEmpty());
    }

}
