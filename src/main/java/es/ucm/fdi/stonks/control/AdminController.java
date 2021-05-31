package es.ucm.fdi.stonks.control;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.User;

/**
 * Admin-only controller
 * @author mfreire
 */
@Controller()
@RequestMapping("admin")
public class AdminController {
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));

		int numUsers = entityManager
							.createNamedQuery("User.all", User.class)
							.getResultList()
							.size();
        model.addAttribute("numUsers", numUsers);

        int numRooms = entityManager
							.createNamedQuery("Room.all", Room.class)
							.getResultList()
							.size();
        model.addAttribute("numRooms", numRooms);
		
		Double totalBalance = entityManager
								.createNamedQuery("Membership.getAllBalances", Double.class)
								.getSingleResult();
		model.addAttribute("totalBalance", String.format("%.2f",totalBalance));

		return "admin";
	}

	@PostMapping("/toggleUser")
	@Transactional
	@ResponseBody
	public String delUser(Model model,	
							@RequestBody JsonNode o, 
							HttpServletResponse response) throws IOException {
		User target = entityManager.find(User.class, o.get("id").asLong());
		
		if (target == null){
			response.sendError(400);
		}

		if (target.getEnabled() == 1) {
			// disable user
			target.setEnabled((byte)0); 
		} else {
			// enable user
			target.setEnabled((byte)1);
		}

		return "{\"result\": \"success\"}";
	}
}
