package es.ucm.fdi.stonks.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    public static final String ROOMS = "rooms";
    public static final String USERS = "users";
    public static final String _MENU = "_menu";
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

        model.addAttribute(ROOMS, rooms);
        model.addAttribute("menu_content",_menu);

        return "rooms";

    }

    @GetMapping("/r") // /s/idsala sala por dentro.
    public String room(Model model) {
        model.addAttribute("menu_content",_menu);
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
