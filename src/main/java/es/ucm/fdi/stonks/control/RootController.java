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
            createNamedQuery("User.top").
            setMaxResults(5).
            getResultList();
        List<?> topRooms = entityManager.
            createNamedQuery("Room.top").
            setMaxResults(3).
            getResultList();

        model.addAttribute("topUsers", topUsers);
        model.addAttribute("topRooms", topRooms);

        return "index";
    }

    @GetMapping("/admin")   // admin panel
    public String admin(Model model) {
        List<?> users = entityManager.createNamedQuery("User.all").getResultList();
        List<?> rooms = entityManager.createNamedQuery("Room.all").getResultList();

        model.addAttribute("users", users);
        model.addAttribute("rooms", rooms);
        return "admin";
    }

    @GetMapping("/rooms")   // lista de salas
    public String rooms(Model model) {
        List<?> user_rooms = entityManager.
                createNamedQuery("Room.all").   // Hay que cambiar esto por las salas del usuario en la sesión
                getResultList();
        List<?> topRooms = entityManager.
                createNamedQuery("Room.top").
                setMaxResults(3).
                getResultList();

        model.addAttribute("user_rooms", user_rooms);
        model.addAttribute("topRooms", topRooms);

        return "rooms";

    }

    @GetMapping("/r/{id}") // /s/idsala sala por dentro.
    public String room(@PathVariable long id, Model model) {
        List<?> rooms = entityManager.createNamedQuery("Room.all").getResultList();
        model.addAttribute("rooms",rooms);

        Room room = entityManager.find(Room.class, id);
        model.addAttribute("room", room);

        List<?> users_inroom = entityManager.
                    createNamedQuery("Membership.userInRoom").
                    setParameter("room_id", room).
                    getResultList();

        model.addAttribute("users_inroom", users_inroom);

        return "r";
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
        newUser.setPassword(password);
        newUser.setEnabled((byte) 1);
        newUser.setRoles("USER");
        entityManager.persist(newUser);

        return "login";
    }

    @GetMapping("/createRoom")
    public String createRoom(Model model) {
        return "createRoom";
    }

    @PostMapping("/createRoom")
    @Transactional
    public void postCreateRoom(
            @RequestParam String name,
            @RequestParam boolean isPublic,
            @RequestParam int weeklyCash,
            @RequestParam int maxUsers,
            @RequestParam int startBalance,
            @RequestParam int cash2Win,
            @RequestParam long adminID,
            @RequestParam String expirationDate,
            HttpServletResponse response) throws Exception{

        Room newRoom = new Room();
        User admin = entityManager.find(User.class, adminID);
        Membership adminMember = new Membership();
        ArrayList<Membership> memberList = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();

        adminMember.setUser(admin);
        adminMember.setRoom(newRoom);
        adminMember.setBalance(startBalance);
        adminMember.setJoinDate(currentDate);
        entityManager.persist(adminMember);

        memberList.add(adminMember);

        newRoom.setName(name);
        newRoom.setPublic(isPublic);
        newRoom.setWeeklyCash(weeklyCash);
        newRoom.setMaxUsers(maxUsers);
        newRoom.setStartBalance(startBalance);
        newRoom.setCash2Win(cash2Win);
        newRoom.setAdmin(admin);
        newRoom.setCreationDate(currentDate);
        newRoom.setExpirationDate(LocalDate.parse(expirationDate));
        newRoom.setMemberList(memberList);
        entityManager.persist(newRoom);

        response.sendRedirect("/rooms");
    }

    @GetMapping("/users")
    public String user(Model model) {
        List<?> topUsers = entityManager.
            createNamedQuery("User.top").
            setMaxResults(5).
            getResultList();

        model.addAttribute("topUsers", topUsers);

        return "users";
    }

    @GetMapping("/u/{id}") // /user/uid
    public String user(@PathVariable long id, Model model) {
        User user = entityManager.find(User.class, id);

        model.addAttribute("user", user);

        return "u";
    }
}
