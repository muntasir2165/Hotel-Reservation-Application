package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation\n" + this.customer.getFirstName() + " " + this.customer.getLastName() + "\n" + "Room: " + this.room.getRoomNumber() + " - " + this.room.getRoomType() + " bed\n" + "Price: $" + this.room.getRoomPrice() + " price per night\n" + "Checkin Date: " + new SimpleDateFormat("E MMM dd yyyy").format(this.checkInDate) + "\n" + "Checkout Date: " + new SimpleDateFormat("E MMM dd yyyy").format(this.checkOutDate);
    }
}
