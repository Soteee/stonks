package es.ucm.fdi.stonks.control;

import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Symbol;
import es.ucm.fdi.stonks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

@Controller
public class RootController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    @GetMapping("/")
    public String index(Model model) {
        List<User> topUsers = entityManager
                            .createNamedQuery("User.top", User.class)
                            .setMaxResults(5)
                            .getResultList();
        model.addAttribute("topUsers", topUsers);

        List<Room> topRooms = entityManager
                            .createNamedQuery("Room.top", Room.class)
                            .setMaxResults(5)
                            .getResultList();
        model.addAttribute("topRooms", topRooms);


        List<Symbol> symbols = entityManager
                                .createNamedQuery("Symbol.all", Symbol.class)
                                .getResultList();
        model.addAttribute("stocks", StaticMethods.symbolsToStocks(symbols));

        return "index";
    }
    
    @GetMapping("/social")
    public String social(Model model) {
        return "social";
    }

    @GetMapping("/login")
    public String getLogin(Model model, @RequestParam(required = false) String error) {
        if (error != null){
            model.addAttribute("error", "El usuario y la contraseña no coinciden o el usuario está desactivado");
        }

        return "login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    @Transactional
    public String postRegister(@RequestParam String username, 
                                @RequestParam String mail,
                                @RequestParam String name,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String password) {
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setMail(mail);
        newUser.setName(name);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(encoder.encode(password));
        newUser.setEnabled((byte) 1);
        newUser.setRoles("USER");
        entityManager.persist(newUser);

        return "login";
    }


    @GetMapping("/u")
    public String user(Model model) {
        List<?> topUsers = entityManager
            .createNamedQuery("User.top")
            .setMaxResults(10)
            .getResultList();
        model.addAttribute("topUsers", topUsers);

        return "users";
    }

    @GetMapping("/u/{id}") // /user/uid
    public String user(@PathVariable long id, Model model) {

        User user = entityManager.find(User.class, id);
        model.addAttribute("user", user);

        List<Room> user_rooms = entityManager
                                .createNamedQuery("Room.byUser", Room.class)
                                .setParameter("user", user)
                                .getResultList();
        model.addAttribute("user_rooms", user_rooms);

        return "u";

    }
}
