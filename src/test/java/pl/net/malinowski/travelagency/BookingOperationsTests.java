package pl.net.malinowski.travelagency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingOperationsTests {

    @Autowired
    private BookingService bookingService;

    @Test
    public void countPeopleQuantity() {
        Assert.assertNotNull(bookingService.countReservationsByTripId(2L));
    }
}
