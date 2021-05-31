package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * A message that users can send each other.
 *
 * @author mfreire
 */
@Entity
@Data
public class Message implements Transferable<Message.Transfer> {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Room room;
	private String text;
	
	private LocalDateTime dateSent;
	
	/**
	 * Objeto para persistir a/de JSON
	 * @author mfreire
	 */
    @Getter
    @AllArgsConstructor
	public static class Transfer {
		private String from;
		private String sent;
		private String text;
		long id;
		public Transfer(Message m) {
			this.from = m.getUser().getUsername();
			this.sent = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(m.getDateSent());
			this.text = m.getText();
			this.id = m.getId();
		}
	}

	@Override
	public Transfer toTransfer() {
		return new Transfer(user.getUsername(), 
			DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateSent),text, id
        );
    }
}
