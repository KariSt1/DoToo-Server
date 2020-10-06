package is.hi.hbv501g.dotoo.DoToo.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date startDate;
    private Date endDate;
    private String title;
    private String category;
    private String color;
}
