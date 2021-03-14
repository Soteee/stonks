package es.ucm.fdi.stonks.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

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
        "Ayuda",
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

    @GetMapping("/")
    public String index(Model model) {
        String[] topUsers = {users[0], users[1]};
        String[] topRooms = {rooms[0], rooms[1]};

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
        return "admin";
    }
    /*
    @GetMapping("/u/{id}") // /user/uid
    public String user(@PathVariable int id, Model model) { // comentar esto <-
        
        return "admin";
    }
    */
}
