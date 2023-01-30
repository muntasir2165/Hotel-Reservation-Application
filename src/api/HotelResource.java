package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource INSTANCE;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotelResource();
        }

        return INSTANCE;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(CustomerService.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        return reservationService.getCustomerReservation(CustomerService.getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms  = ReservationService.findRooms(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            availableRooms = ReservationService.findRooms(incrementDateByDays(checkInDate, 7), incrementDateByDays(checkOutDate, 7));
        }

        return availableRooms;
    }

    private static Date incrementDateByDays(Date date, int daysToIncrement) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysToIncrement);

        return cal.getTime();
    }
}
