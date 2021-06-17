package at.htl.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_HIKE")
public class Hike {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private Date dateOfJourney;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "V_GROUP_OF_HIKERS")
    private List<Hiker> groupOfHikers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "V_ROUTE")
    private Route route;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "V_HIKER")
    private Hiker hiker;

    public Hike() {
    }

    public Hike(Long id, Date dateOfJourney, List<Hiker> groupOfHikers, Route route, Hiker hiker) {
        this.id = id;
        this.dateOfJourney = dateOfJourney;
        this.groupOfHikers = groupOfHikers;
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

    public List<Hiker> getGroupOfHikers() {
        return groupOfHikers;
    }

    public void setGroupOfHikers(List<Hiker> groupOfHikers) {
        this.groupOfHikers = groupOfHikers;
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
                ", groupOfHikers=" + groupOfHikers +
                ", route=" + route +
                '}';
    }
}
