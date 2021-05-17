package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * A message that users can send each other.
 *
 * @author mfreire
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Message.countUnread",
				query="SELECT COUNT(m) FROM Message m "
					+ "WHERE m.room.id = :room AND m.dateRead = null"),	
})
@Data
public class Message implements Transferable<Message.Transfer> {
	
	private static Logger log = LogManager.getLogger(Message.class);	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Room room;
	private String text;
	
	private LocalDateTime dateSent;
	private LocalDateTime dateRead;
	
	/**
	 * Objeto para persistir a/de JSON
	 * @author mfreire
	 */
    @Getter
    @AllArgsConstructor
	public static class Transfer {
		private String from;
		private String to;
		private String sent;
		private String received;
		private String text;
		long id;
		public Transfer(Message m) {
			this.from = m.getUser().getUsername();
			this.to = String.valueOf(m.getRoom().getId());
			this.sent = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(m.getDateSent());
			this.received = m.getDateRead() == null ?
					null : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(m.getDateRead());
			this.text = m.getText();
			this.id = m.getId();
		}
	}

	@Override
	public Transfer toTransfer() {
		return new Transfer(user.getUsername(), String.valueOf(room.getId()), 
			DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateSent),
			dateRead == null ? null : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateRead),
			text, id
        );
    }
}
