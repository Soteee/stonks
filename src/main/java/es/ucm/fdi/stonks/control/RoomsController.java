package es.ucm.fdi.stonks.control;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Symbol;
import es.ucm.fdi.stonks.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("r")
public class RoomsController{
    @Autowired
    private EntityManager entityManager;

    @GetMapping("")   // lista de salas
    public String rooms(Model model, HttpSession session) {
        List<Room> user_rooms = entityManager
                                .createNamedQuery("Room.byUser", Room.class)
                                .setParameter("user", entityManager.find(User.class,((User)session.getAttribute("u")).getId()))
                                .getResultList();
        model.addAttribute("user_rooms", user_rooms);

        List<Room> topRooms = entityManager
                                .createNamedQuery("Room.top",Room.class)
                                .setMaxResults(10)
                                .getResultList();
        model.addAttribute("topRooms", topRooms);

        return "rooms";
    }

    @GetMapping("/{id}") // /s/idsala sala por dentro.
    public String room(@PathVariable long id, Model model, HttpSession session) {
        User user = entityManager.find(User.class,((User)session.getAttribute("u")).getId());

        List<Room> user_rooms = entityManager
                                .createNamedQuery("Room.byUser", Room.class)
                                .setParameter("user", user)
                                .getResultList();
        model.addAttribute("user_rooms", user_rooms);

        Room room = entityManager.find(Room.class, id);
        model.addAttribute("room", room);

        List<Symbol> lastSymbols = entityManager
                                .createNamedQuery("Symbol.lastsByRoom", Symbol.class)
                                .setParameter("room", room)
                                .getResultList();
        model.addAttribute("symbols", lastSymbols);

        List<Symbol> allSymbols = entityManager
                                    .createNamedQuery("Symbol.allByRoom", Symbol.class)
                                    .setParameter("room", room)
                                    .getResultList();
        model.addAttribute("roomStocks", StaticMethods.symbolsToStocks(allSymbols));

        try {
            Membership membership = entityManager
                                    .createNamedQuery("Membership.byUserAndRoom", Membership.class)
                                    .setParameter("user", user)
                                    .setParameter("room", room)
                                    .getSingleResult();
            model.addAttribute("membership", membership);
        } catch (Exception e) {
            // El usuario no pertenece a la sala (no pasa nada)
        }

        return "r";
    }

    @GetMapping("/createRoom")
    public String createRoom(Model model) {

        List<Symbol> symbols = entityManager
                        .createNamedQuery("Symbol.lasts", Symbol.class)
                        .getResultList();
        model.addAttribute("symbols", symbols);

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
            @RequestParam String[] symbols,
            HttpServletResponse response,
            HttpSession session) throws Exception{

        Room newRoom = new Room();
        User room_admin = entityManager.find(User.class,((User)session.getAttribute("u")).getId());
        Membership adminMember = new Membership();
        ArrayList<Membership> memberList = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();

        List<Symbol> symbolList = new ArrayList<>();
        for (int i = 0; i < symbols.length; ++i) {
            symbolList.addAll(entityManager
                                .createNamedQuery("Symbol.allByName", Symbol.class)
                                .setParameter("name", symbols[i])
                                .getResultList());
        }

        adminMember.setUser(room_admin);
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
        newRoom.setAdmin(room_admin);
        newRoom.setCreationDate(currentDate);
        newRoom.setExpirationDate(LocalDate.parse(expirationDate));
        newRoom.setMemberList(memberList);
        newRoom.setFinished(false);
        entityManager.persist(newRoom);

        for(Symbol symbol : symbolList){
            symbol.getRooms().add(newRoom);
            entityManager.persist(symbol);
        }

        response.sendRedirect("/r/");
    }

    

    @PostMapping("/joinRoom")
    @Transactional
    public void joinRoom(@RequestParam long room_id,
                        HttpServletResponse response,
                        HttpSession session) throws Exception{

        User user = entityManager.find(User.class,((User)session.getAttribute("u")).getId());
        Room room = entityManager.find(Room.class, room_id);

        List<Membership> previousMembers = entityManager
                                            .createNamedQuery("Membership.byUserAndRoom", Membership.class)
                                            .setParameter("user", user)
                                            .setParameter("room", room)
                                            .getResultList();

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
        else{ // TODO devolver mensaje de error con parámetro GET?
            response.sendError(400);
        }
    }
}