package es.ucm.fdi.stonks.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A user; can be an Admin, a User, or a Moderator
 *
 * Users can log in and send each other messages.
 *
 * @author mfreire
 * 
 *         An authorized user of the system.
 */
@Entity
@Data
@NoArgsConstructor
@NamedQueries({ @NamedQuery(name = "User.all",
							query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.byUsername", query = "SELECT u FROM User u "
				+ "WHERE u.username = :username AND u.enabled = 1"),
		@NamedQuery(name = "User.hasUsername", query = "SELECT COUNT(u) " + "FROM User u "
				+ "WHERE u.username = :username"),
		@NamedQuery(name = "User.top", query = "SELECT u FROM Membership m " + "INNER JOIN User u ON m.user = u.id "
				+ "GROUP BY m.user " + "ORDER BY sum(m.balance) DESC"),
		@NamedQuery(name = "User.inRoom", query = "SELECT u FROM Membership m " + "INNER JOIN User u ON m.user = u.id "
				+ "WHERE m.room = :room " + "ORDER BY m.balance DESC"),
		@NamedQuery(name = "User.following", query = "SELECT u.following FROM User u " + "WHERE u = :user"),
		@NamedQuery(name = "User.followers", query = "SELECT u FROM User u " + "JOIN u.following f "
				+ "WHERE f = :user"),
		@NamedQuery(name="User.bySearch",
            query="SELECT u FROM User u WHERE u.username LIKE CONCAT('%',CONCAT(:name,'%'))")})
public class User implements Transferable<User.Transfer> {

	public enum Role {
		USER, // used for logged-in, non-priviledged users
		ADMIN, // used for maximum priviledged users
		MODERATOR // remove or add roles as needed
	}

	// do not change these fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** username for login purposes; must be unique */
	@Column(nullable = false, unique = true)
	private String username;
	/**
	 * encoded password; use setPassword(SecurityConfig.encode(plaintextPassword))
	 * to encode it
	 */
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String roles; // split by ',' to separate roles
	private byte enabled;

	// application-specific fields
	private String name;
	private String firstName;
	private String lastName;
	private String mail;

	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "winner")
	private List<Room> wonRooms;

	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Message> sent = new ArrayList<>();

	/** salas de las que soy socio */
	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Membership> memberList = new ArrayList<>();

	@ManyToMany
	private List<User> following = new ArrayList<>();

	// utility methods

	/**
	 * Checks whether this user has a given role.
	 * 
	 * @param role to check
	 * @return true iff this user has that role.
	 */
	public boolean hasRole(Role role) {
		String roleName = role.name();
		return Arrays.stream(roles.split(",")).anyMatch(r -> r.equals(roleName));
	}

	@Getter
	@AllArgsConstructor
	public static class Transfer {
		private long id;
		private String username;
		private int totalSent;
	}

	@Override
	public Transfer toTransfer() {
		return new Transfer(id, username, sent.size());
	}

	@Override
	public String toString() {
		return toTransfer().toString();
	}

	@Override
	public boolean equals(Object object) {
		User u = (User) object;

		if (u.getId() == this.id) {
			return true;
		} else {
			return false;
		}
	}
}
