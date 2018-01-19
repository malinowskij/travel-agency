package pl.net.malinowski.travelagency.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
public class Schedule implements Comparable<Schedule> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    private String description;

    @ManyToMany
    @JoinTable(name = "schedules_attractions", joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "attraction_id"))
    private Set<Attraction> attractions;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Schedule(Date date, String name, String description, Trip trip, Set<Attraction> attractions) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.trip = trip;
        this.attractions = attractions;
    }

    @Override
    public int compareTo(Schedule o) {
        return date.compareTo(o.date);
    }
}
