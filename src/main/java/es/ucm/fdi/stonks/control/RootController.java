package es.ucm.fdi.stonks.control;


import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Room;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RootController {

	private static final Logger log = LogManager.getLogger(AdminController.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    @GetMapping("/")
    public String index(Model model) {
        List<?> topUsers = entityManager
            .createNamedQuery("User.top")
            .setMaxResults(5)
            .getResultList();
        model.addAttribute("topUsers", topUsers);

        List<?> topRooms = entityManager
            .createNamedQuery("Room.top")
            .setMaxResults(5)
            .getResultList();
        model.addAttribute("topRooms", topRooms);

        return "index";
    }
    
    @GetMapping("/social")
    public String social(Model model) {
        return "social";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        return "login";
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


    @GetMapping("/users")
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

        List<?> user_rooms = entityManager
                .createNamedQuery("Room.byUser")
                .setParameter("user", user)
                .getResultList();
        model.addAttribute("user_rooms", user_rooms);

        return "u";

    }
}
