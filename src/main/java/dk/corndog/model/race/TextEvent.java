package dk.corndog.model.race;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class TextEvent {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMP_SEQ")
    @SequenceGenerator(name="EMP_SEQ", sequenceName="EMP_SEQ", allocationSize=100)
	public long id;
	
	public String raceUUID;
	
	public long eventTime;
	
	@Lob 
	@Column(length=1024)
	public String eventText;
	
	public TextEvent() {
		// TODO Auto-generated constructor stub
	}

	public TextEvent(String raceUUID, long eventTime, String eventText) {
		super();
		this.raceUUID = raceUUID;
		this.eventTime = eventTime;
		this.eventText = eventText;
	}
	
}
