package es.ucm.fdi.stonks.control;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Transferable;
import es.ucm.fdi.stonks.model.Message;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
@Transactional
public class ChatController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping(path = "/{id}", produces = "application/json")
    public List<Message.Transfer> getChat(@PathVariable long id,
                                        HttpSession session){
        Room room = entityManager.find(Room.class, id);
        
        return room.getReceived().stream().map(Transferable::toTransfer).collect(Collectors.toList());       
    }
}

