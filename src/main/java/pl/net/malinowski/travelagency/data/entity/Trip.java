package pl.net.malinowski.travelagency.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "destination_country_id", nullable = false)
    private Country destinationCountry;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @Future
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @NotNull
    @Column(name = "people_limit", nullable = false)
    private Integer peopleLimit;

    @DecimalMin("0.0")
    @Column(nullable = false, name = "trip_price")
    private BigDecimal tripPrice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "trips_schedules", joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Photo> photos;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "all_inclusive_available", nullable = false)
    private boolean allInclusiveAvailable;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "all_inclusive_price", nullable = false)
    private BigDecimal allInclusivePrice;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;

    @ManyToMany
    @JoinTable(name = "trips_features", joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> features;

    public Set<Schedule> getSchedules() {
        schedules = new TreeSet<>(schedules);
        return schedules;
    }
}
