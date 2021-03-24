package es.ucm.fdi.stonks.control;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class RootController {

	private static final Logger log = LogManager.getLogger(AdminController.class);

    @Autowired
    private EntityManager entityManager;

    public static final String ROOMS = "rooms";
    public static final String USERS = "users";
    public static final String _MENU = "_menu";
    public static final String MENU_CONTENT = "menu_content";
    public static final String ROOM_CONTENT = "room_content";
    public static final String FEATURED_ROOMS = "featured_rooms";
    String [] _menu = {
        "Ayuda",
        "Salas",
        "Perfil",
        "Amigos",
        "Contacto"

    };
    String[] users = {
        "John Doe", 
        "Jane Doe",
        "Alice",
        "Bob"
    };
    String[] rooms = {
        "Viva Hacienda :D",
        "Me gusta pagar impuestos",
        "Las carreteras de Alexelcapo",
        "A Andorra hemos de ir"
    };
    String[] featuredRooms = {
        "Cryptoroom",
        "GameStop strikes back",
        "Mercadeo y lo que surja"
    };

    @Transactional
    @GetMapping("/")
    public String index(Model model) {
        String[] topUsers = {users[0], users[1]};
        String[] topRooms = {rooms[0], rooms[1]};
        User user = entityManager.find(User.class, 1L);

        log.info("El primer usuario es {}", user);

        Room room = new Room();
        Membership member = new Membership();

        model.addAttribute("topUsers", topUsers);
        model.addAttribute("topRooms", topRooms);

        room.setName("Salita");
        member.setRoom(room);
        member.setUser(user);

      
        entityManager.persist(user);
        entityManager.persist(room);
        entityManager.persist(member);

        entityManager.flush();

        return "index";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        return "menu";
    }
    
    @GetMapping("/admin")   // admin panel
    public String admin(Model model) {
        model.addAttribute(USERS, users);
        model.addAttribute(ROOMS, rooms);
        return "admin";
    }

    @GetMapping("/rooms")   // lista de salas
    public String rooms(Model model) {

        model.addAttribute(MENU_CONTENT, _menu);
        model.addAttribute(ROOM_CONTENT, rooms);
        model.addAttribute(USERS, users);
        model.addAttribute(FEATURED_ROOMS, featuredRooms);

        return "rooms";

    }

    @GetMapping("/r") // /s/idsala sala por dentro.
    public String room(Model model) {
        model.addAttribute(MENU_CONTENT,_menu);
        model.addAttribute(ROOM_CONTENT,rooms);
        model.addAttribute(USERS, users);
        return "r";
    }
    
    @GetMapping("/social")
    public String social(Model model) {
        return "social";
    }
    /*
    @GetMapping("/u/{id}") // /user/uid
    public String user(@PathVariable int id, Model model) { // comentar esto <-
        
        return "admin";
    }
    */
}
