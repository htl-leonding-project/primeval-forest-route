package at.htl.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Hike {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOfJourney;

<<<<<<< HEAD
    @ManyToOne(cascade = CascadeType.ALL)
    private Route route;

    @ManyToOne(cascade = CascadeType.ALL)
=======
    @ManyToOne
    @Cascade(CascadeType.ALL)
    private Route route;

    @ManyToOne
    @Cascade(CascadeType.ALL)
>>>>>>> main
    private Hiker hiker;

    public Hike() {
    }

    public Hike(Long id, Date dateOfJourney, Route route, Hiker hiker) {
        this.id = id;
        this.dateOfJourney = dateOfJourney;
        this.route = route;
        this.hiker = hiker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(Date dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Hiker getHiker() {
        return hiker;
    }

    public void setHiker(Hiker hiker) {
        this.hiker = hiker;
    }

    @Override
    public String toString() {
        return "Hike{" +
                "id=" + id +
                ", dateOfJourney=" + dateOfJourney +
<<<<<<< HEAD
                ", route=" + route +
=======
>>>>>>> main
                '}';
    }
}
