package es.ucm.fdi.stonks.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootController {

    public static final String ROOMS = "rooms";
    public static final String USERS = "users";
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

    @GetMapping("/")                        // peticion a `/`
    public String index(Model model) {
        return "index";                      // vista resultante
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        return "menu";
    }
    
    @GetMapping("/admin")   // admin panel
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/rooms")   // lista de salas
    public String rooms(Model model) {

        model.addAttribute(ROOMS, rooms);

        return "rooms";

    }

    @GetMapping("/r/") // /s/idsala sala por dentro.
    public String room(Model model) {
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
