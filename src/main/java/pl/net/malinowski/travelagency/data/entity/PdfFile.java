package pl.net.malinowski.travelagency.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pdf_file")
@Getter @Setter @NoArgsConstructor
public class PdfFile {

    @Id
    @GeneratedValue
    private Long id;

    private String path;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy mm:hh:ss")
    private Date created;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
