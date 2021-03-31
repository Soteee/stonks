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
        /*

        Estas queries no funcionan por jpa pero sí directamente en la aplicación web de h2.
        Las dejo aquí por si alguien se atreve.
        Son para sacar los usuarios y salas con más dinero.

        List<String> topUsers = entityManager.
            createQuery("SELECT User.username FROM Membership "
                        + "INNER JOIN User ON Membership.user_id = User.id "
                        + "GROUP BY user_id "
                        + "ORDER BY sum(balance) DESC")
                            .getResultList();
        List<String> topRooms = entityManager.
            createQuery("SELECT Room.name  FROM Membership "
                        + "INNER JOIN Room ON Membership.room_id = Room.id "
                        + "GROUP BY room_id "
                        + "ORDER BY sum(balance) DESC")
                            .getResultList();
        */
        List<String> topUsers = entityManager.createQuery("SELECT username FROM User").getResultList();
        List<String> topRooms = entityManager.createQuery("SELECT name FROM Room").getResultList();


        model.addAttribute("topUsers", topUsers);
        model.addAttribute("topRooms", topRooms);

        return "index";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        return "menu";
    }
    
    @GetMapping("/admin")   // admin panel
    public String admin(Model model) {
        List<String> users = entityManager.createQuery("SELECT username FROM User").getResultList();
        List<String> rooms = entityManager.createQuery("SELECT name FROM Room").getResultList();

        model.addAttribute("users", users);
        model.addAttribute("rooms", rooms);
        return "admin";
    }

    @GetMapping("/rooms")   // lista de salas
    public String rooms(Model model) {
        List<String> users = entityManager.createQuery("select username from User").getResultList();
        List<String> rooms = entityManager.createQuery("SELECT name FROM Room").getResultList();

        model.addAttribute(MENU_CONTENT, _menu);
        model.addAttribute("rooms", rooms);
        model.addAttribute("users", users);
        model.addAttribute(FEATURED_ROOMS, featuredRooms);

        return "rooms";

    }

    @GetMapping("/r") // /s/idsala sala por dentro.
    public String room(Model model) {
        List<String> users = entityManager.createQuery("select username from User").getResultList();
        List<String> rooms = entityManager.createQuery("SELECT name FROM Room").getResultList();

        model.addAttribute(MENU_CONTENT,_menu);
        model.addAttribute("rooms",rooms);
        model.addAttribute("users", users);

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
    public String register(Model model) {
        return "register";
    }
    /*
    @GetMapping("/u/{id}") // /user/uid
    public String user(@PathVariable int id, Model model) { // comentar esto <-
        
        return "admin";
    }
    */
}
