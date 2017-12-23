package pl.net.malinowski.travelagency.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Getter @Setter @NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false)
    private Integer peopleQuantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Column(name = "all_inclusive", nullable = false)
    private boolean allInclusive;

    public Booking(User customer, Trip trip, Integer peopleQuantity, boolean allInclusive) {
        this.customer = customer;
        this.trip = trip;
        this.peopleQuantity = peopleQuantity;
        this.allInclusive = allInclusive;
    }
}
