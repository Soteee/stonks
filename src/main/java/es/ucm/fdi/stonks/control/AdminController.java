package es.ucm.fdi.stonks.control;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.stonks.LocalData;
import es.ucm.fdi.stonks.model.User;

/**
 * Admin-only controller
 * @author mfreire
 */
@Controller()
@RequestMapping("admin")
public class AdminController {
	
	private static final Logger log = LogManager.getLogger(AdminController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));

		List<?> users = entityManager.createNamedQuery("User.all").getResultList();
        model.addAttribute("users", users);

        List<?> rooms = entityManager.createNamedQuery("Room.all").getResultList();
        model.addAttribute("rooms", rooms);
		
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
