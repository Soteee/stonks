package es.ucm.fdi.stonks.control;

import es.ucm.fdi.stonks.model.Market;
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

import com.google.common.collect.Maps.EntryTransformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Member;
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

    @GetMapping("/rooms")   // lista de salas
    public String rooms(Model model, HttpSession session) {
        List<?> user_rooms = entityManager
                .createNamedQuery("Room.byUser")
                .setParameter("user", session.getAttribute("u"))
                .getResultList();
        model.addAttribute("user_rooms", user_rooms);

        List<?> topRooms = entityManager
                .createNamedQuery("Room.top")
                .setMaxResults(10)
                .getResultList();
        model.addAttribute("topRooms", topRooms);

        return "rooms";
    }

    @GetMapping("/r/{id}") // /s/idsala sala por dentro.
    public String room(@PathVariable long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("u");

        List<?> user_rooms = entityManager
                .createNamedQuery("Room.byUser")
                .setParameter("user", user)
                .getResultList();
        model.addAttribute("user_rooms", user_rooms);

        Room room = entityManager.find(Room.class, id);
        model.addAttribute("room", room);

        List<?> users_inroom = entityManager
                    .createNamedQuery("User.inRoom")
                    .setParameter("room", room)
                    .getResultList();
        model.addAttribute("users_inroom", users_inroom);

        if (users_inroom.contains(user)){
            Membership membership = (Membership) entityManager
                        .createNamedQuery("Membership.byUserAndRoom")
                        .setParameter("user", user)
                        .setParameter("room", room)
                        .getSingleResult();
            model.addAttribute("membership", membership);

            List<?> positions = entityManager
                        .createNamedQuery("Position.byMembership")
                        .setParameter("membership", membership)
                        .getResultList();
            model.addAttribute("positions", positions);
        }

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
        newUser.setPassword(encoder.encode(password));
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
            @RequestParam String expirationDate,
            @RequestParam String marketName,
            HttpServletResponse response,
            HttpSession session) throws Exception{

        Room newRoom = new Room();
        Market market= new Market();
        User room_admin = (User) session.getAttribute("u");
        Membership adminMember = new Membership();
        ArrayList<Membership> memberList = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();



        adminMember.setUser(room_admin);
        adminMember.setRoom(newRoom);
        adminMember.setBalance(startBalance);
        adminMember.setJoinDate(currentDate);
        entityManager.persist(adminMember);

        memberList.add(adminMember);
           
        market.setName(marketName);
        newRoom.setMarket(market);
        newRoom.setName(name);
        newRoom.setPublic(isPublic);
        newRoom.setWeeklyCash(weeklyCash);
        newRoom.setMaxUsers(maxUsers);
        newRoom.setStartBalance(startBalance);
        newRoom.setCash2Win(cash2Win);
        newRoom.setAdmin(room_admin);
        newRoom.setCreationDate(currentDate);
        newRoom.setExpirationDate(LocalDate.parse(expirationDate));
        newRoom.setMemberList(memberList);
        entityManager.persist(newRoom);

        response.sendRedirect("/rooms");
    }

    @PostMapping("/joinRoom")
    @Transactional
    public void joinRoom(@RequestParam long room_id,
                        HttpServletResponse response,
                        HttpSession session) throws Exception{

        User user = (User) session.getAttribute("u");
        Room room = entityManager.find(Room.class, room_id);

        List<?> previousMembers = entityManager.createNamedQuery("Membership.byUserAndRoom")
                            .setParameter("user", user)
                            .setParameter("room", room).getResultList();

        // Checks if user is already in the room and if room is full
        if (previousMembers.size() == 0 &&
            room.getMaxUsers() > room.getMemberList().size()){

            Membership membership = new Membership();

            membership.setUser(user);
            membership.setRoom(room);
            membership.setBalance(room.getStartBalance());
            membership.setJoinDate(LocalDateTime.now());
            entityManager.persist(membership);

            room.getMemberList().add(membership);

            response.sendRedirect("/r/" + room_id);
        }
        else{
            response.sendError(400);
        }
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
