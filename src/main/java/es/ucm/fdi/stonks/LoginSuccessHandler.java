package es.ucm.fdi.stonks;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import es.ucm.fdi.stonks.control.StaticMethods;
import es.ucm.fdi.stonks.model.User;

/**
 * Called when a user is first authenticated (via login).
 * Called from SecurityConfig; see https://stackoverflow.com/a/53353324
 * 
 * Adds a "u" variable to the session when a user is first authenticated.
 * Important: the user is retrieved from the database, but is not refreshed at each request. 
 * You should refresh the user's information if anything important changes; for example, after
 * updating the user's profile.
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired 
    private HttpSession session;
    
    @Autowired
    private EntityManager entityManager;    
    
	private static Logger log = LogManager.getLogger(LoginSuccessHandler.class);
	
    /**
     * Called whenever a user authenticates correctly.
     */
    @Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	    String username = ((org.springframework.security.core.userdetails.User)
			authentication.getPrincipal()).getUsername();
	    
	    // add a 'u' session variable, accessible from thymeleaf via ${session.u}
		User u = entityManager.createNamedQuery("User.byUsername", User.class)
			.setParameter("username", username)
			.getSingleResult();

		if (u.getEnabled() == 0){
			response.sendRedirect("/login");
		}

		session.setAttribute("u", u);
		
		// add a 'ws' session variable
		String ws = request.getRequestURL().toString()
				.replaceFirst("[^:]*", "ws")		// http[s]://... => ws://...
				.replaceFirst("/[^/]*$", "/ws");	// .../foo		 => .../ws
		session.setAttribute("ws", ws);

		// redirects to 'admin' or 'user/{id}', depending on the user
		String nextUrl = u.hasRole(User.Role.ADMIN) ? 
			"admin/" :
			"/";

		log.info("LOG IN: {} (id {}) -- session is {}, websocket is {} -- redirected to {}",
			u.getUsername(), u.getId(), session.getId(), ws, nextUrl);


		// Update symbol values if more than 24 hours have passed
		StaticMethods.refreshStockValues(entityManager);

		// Check if any room has expired and set winner if so
		StaticMethods.checkExpiredRooms(entityManager);

		// note that this is a 302, and will result in a new request
		response.sendRedirect(nextUrl);
	}
}
