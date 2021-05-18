package es.ucm.fdi.stonks.control;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Transferable;
import es.ucm.fdi.stonks.model.User;
import es.ucm.fdi.stonks.model.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
@Transactional
public class ChatController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
	private SimpMessagingTemplate messagingTemplate;

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public List<Message.Transfer> getChat(@PathVariable long id){
        Room room = entityManager.find(Room.class, id);
        
        return room.getReceived().stream().map(Transferable::toTransfer).collect(Collectors.toList());       
    }

    @PostMapping("/{id}")
	@ResponseBody
	@Transactional
    public String sendMsg(@PathVariable long id,
                            @RequestBody JsonNode o,
                            HttpSession session) throws JsonProcessingException{
        String text = o.get("message").asText();
        Room room = entityManager.find(Room.class, id);
        User user = entityManager.find(User.class,((User)session.getAttribute("u")).getId());

        Message newMessage = new Message();
        newMessage.setRoom(room);
        newMessage.setUser(user);
        newMessage.setText(text);
        newMessage.setDateSent(LocalDateTime.now());
        entityManager.persist(newMessage);

        room.getReceived().add(newMessage);
        user.getSent().add(newMessage);
        
        entityManager.flush(); // to get Id before commit

        // construye json
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.put("from", user.getUsername());
		rootNode.put("text", text);
		rootNode.put("id", newMessage.getId());
		String json = mapper.writeValueAsString(rootNode);

		messagingTemplate.convertAndSend("/user/"+user.getUsername()+"/queue/updates", json);
		return "{\"result\": \"message sent.\"}";
    }
}

