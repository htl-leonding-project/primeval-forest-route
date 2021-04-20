package at.htl.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Hike {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private Date dateOfJourney;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hike")
    private List<Hiker> groupOfHikers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hike")
    private List<Route> route;

    public Hike() {
    }

    public Hike(Long id, Date dateOfJourney, List<Hiker> groupOfHikers, List<Route> route) {
        this.id = id;
        this.dateOfJourney = dateOfJourney;
        this.groupOfHikers = groupOfHikers;
        this.route = route;
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

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
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
