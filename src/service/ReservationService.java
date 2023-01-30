package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class ReservationService {
    private static ReservationService INSTANCE;
    private static final Collection<Reservation> reservations = new ArrayList<Reservation>();
    private static final Collection<IRoom> rooms = new ArrayList<IRoom>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }

        return INSTANCE;
    }

    public static void addRoom(IRoom room) {
        rooms.add(room);
    }

    public static IRoom getARoom(String roomId) {
        IRoom result = null;
        for (IRoom room: rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                result =  room;
                break;
            }
        }

        return Objects.isNull(result) ? null : result;
    }

    public static Collection<IRoom> getAllRooms() {
        return rooms;
    }


    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);

        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkoutDate){
        Collection<IRoom> availableRooms = new ArrayList<IRoom>();
        Collection<IRoom> unavailableRooms = new ArrayList<IRoom>();

        // Find rooms in current reservations which will be available for the specified check in and check out dates
        for (Reservation reservation: reservations) {
           IRoom room = reservation.getRoom();
           Date currentRoomCheckInDate = reservation.getCheckInDate();
           Date currentRoomCheckOutDate = reservation.getCheckOutDate();

           if (checkInDate.after(checkInDate) && checkoutDate.after(currentRoomCheckOutDate)) {
               availableRooms.add(room);
           } else {
               unavailableRooms.add(room);
           }
        }

        // Find rooms that are currently not reserved
        for (IRoom room : rooms ) {
            if (!availableRooms.contains(room) && unavailableRooms.contains(room) ) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public static Collection<Reservation> getCustomerReservation(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<Reservation>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }

        return customerReservations;
    }

    public static Collection<Reservation> getAllReservations() {
        return reservations;
    }
}
