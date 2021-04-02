package es.ucm.fdi.stonks.control;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Controller
public class RootController {

	private static final Logger log = LogManager.getLogger(AdminController.class);

    @Autowired
    private EntityManager entityManager;

    public static final String _MENU = "_menu";
    public static final String MENU_CONTENT = "menu_content";
    public static final String ROOM_CONTENT = "room_content";
    public static final String FEATURED_ROOMS = "featured_rooms";
    public static final String LOG_IN = "logIn";

    String [] _menu = {
        "Ayuda",
        "Salas",
        "Perfil",
        "Amigos",
        "Contacto"

    };

    String[] featuredRooms = {
        "Cryptoroom",
        "GameStop strikes back",
        "Mercadeo y lo que surja"
    };

    @Transactional
    @GetMapping("/")
    public String index(Model model) {
        // Top 5 users y top 3 salas
        List<?> topUsers = entityManager.
            createQuery("SELECT u FROM Membership m "
                        + "INNER JOIN User u ON m.user = u.id "
                        + "GROUP BY m.user "
                        + "ORDER BY sum(m.balance) DESC")
                        .setMaxResults(5).getResultList();
        List<?> topRooms = entityManager.
            createQuery("SELECT r  FROM Membership m "
                        + "INNER JOIN Room r ON m.room = r.id "
                        + "GROUP BY m.room "
                        + "ORDER BY sum(m.balance) DESC")
                        .setMaxResults(3).getResultList();

        model.addAttribute("topUsers", topUsers);
        model.addAttribute("topRooms", topRooms);

        return "index";
    }

    @GetMapping("/admin")   // admin panel
    public String admin(Model model) {
        List<?> users = entityManager.createQuery("SELECT u FROM User u").getResultList();
        List<?> rooms = entityManager.createQuery("SELECT r FROM Room r").getResultList();

        model.addAttribute("users", users);
        model.addAttribute("rooms", rooms);
        return "admin";
    }

    @GetMapping("/rooms")   // lista de salas
    public String rooms(Model model) {
        List<?> users = entityManager.createQuery("select u from User u").getResultList();
        List<?> rooms = entityManager.createQuery("SELECT r FROM Room r").getResultList();
        List<?> topRooms = entityManager.
            createQuery("SELECT r  FROM Membership m "
                        + "INNER JOIN Room r ON m.room = r.id "
                        + "GROUP BY m.room "
                        + "ORDER BY sum(m.balance) DESC")
                        .setMaxResults(3).getResultList();

        model.addAttribute(MENU_CONTENT, _menu);
        model.addAttribute("rooms", rooms);
        model.addAttribute("users", users);
        model.addAttribute("topRooms", topRooms);

        return "rooms";

    }

    @GetMapping("/r/{id}") // /s/idsala sala por dentro.
    public String room(@PathVariable long id, Model model) {
        model.addAttribute(MENU_CONTENT,_menu);

        List<?> rooms = entityManager.createQuery("SELECT r FROM Room r").getResultList();
        model.addAttribute("rooms",rooms);

        Room room = entityManager.find(Room.class, id);
        List<?> users_inroom = entityManager.createNamedQuery("userInRoom").setParameter("room_id", room).getResultList();
        model.addAttribute("users_inroom", users_inroom);

        return "r";
    }
    
    @GetMapping("/social")
    public String social(Model model) {
        return "social";
    }

    @GetMapping("/logIn")
    public String logIn(Model model) {
        return "logIn";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    @Transactional
    public String postRegister(
                        @RequestParam String username, 
                        @RequestParam String mail,
                        @RequestParam String name,
                        @RequestParam String firstName,
                        @RequestParam String lastName,
                        @RequestParam String password,
                        Model model){
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setMail(mail);
        newUser.setName(name);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        entityManager.persist(newUser);

        return "logIn";
    }

    /*
    @GetMapping("/u/{id}") // /user/uid
    public String user(@PathVariable int id, Model model) { // comentar esto <-
        
        return "admin";
    }
    */
}
