package ui;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;
import utility.Utility;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static MainMenu INSTANCE;
    private final AdminMenu adminMenu = AdminMenu.getInstance();
    private final AdminResource adminResource = AdminResource.getInstance();
    private final HotelResource hotelResource = HotelResource.getInstance();
    private Scanner userInput = new Scanner(System.in);

    public static MainMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainMenu();
        }

        return INSTANCE;
    }

    public void displayOptions() {
        String selectedOption;

        do {
            System.out.println("----------------------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("----------------------------------------------");
            System.out.println("Please select a number for the menu option");

            selectedOption = userInput.nextLine();

            switch (selectedOption) {
                case "1":
                    reserveRoom();
                    break;
                case "2":
                    displayReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    adminMenu.displayOptions();
                    break;
                case "5":
                    System.out.println("Exiting the app...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        } while (!selectedOption.equals("5"));
    }

    private void reserveRoom() {
        Date checkInDate;
        do {
            System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
            String checkInDateString = userInput.nextLine();
            checkInDate = Utility.stringToDate(checkInDateString);
        } while (checkInDate == null);

        Date checkOutDate;
        do {
            System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/21/2020");
            String checkOutDateString = userInput.nextLine();
            checkOutDate = Utility.stringToDate(checkOutDateString);
        } while (checkOutDate == null);

        System.out.println("Rooms available for reservations:");
        Collection<IRoom> rooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (rooms.isEmpty()){
            System.out.println("No room is currently available for reservation");
            return;
        } else{
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }

        String doesUserHaveAccount;
        do {
            System.out.println("Do you have an account with us? y/n");
            doesUserHaveAccount = userInput.nextLine();
        } while (!doesUserHaveAccount.toLowerCase().equals("y") && !doesUserHaveAccount.toLowerCase().equals("n"));

        if (doesUserHaveAccount.toLowerCase().equals("n")) {
            System.out.println("Please use the menu option no. 3 to create an account and then try reserving a room");
        } else {
            String customerEmail;
            boolean isEmailValid = false;
            do {
                System.out.println("Enter Email (format: name@domain.com)");
                customerEmail = userInput.nextLine();
                if (!Utility.isEmailValid(customerEmail)) {
                    System.out.println("Invalid email format");
                } else if (adminResource.getCustomer(customerEmail) != null) {
                    isEmailValid = true;
                }
            } while (!isEmailValid);

            String roomNumber;
            IRoom room;
            boolean isRoomNumberValid = false;
            do {
                System.out.println("What room number would you like to reserve?");
                roomNumber = userInput.nextLine();
                room = hotelResource.getRoom(roomNumber);
                if (room == null) {
                    System.out.println("Invalid room number");
                } else if (!Utility.isRoomAvailableForReservation(rooms, roomNumber)) {
                    System.out.println("The room number your entered is not available for reservation.");
                }else {
                    isRoomNumberValid = true;
                }
            } while (!isRoomNumberValid);

            Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
            System.out.println(reservation);
        }
    }

    private void displayReservations() {
        String customerEmail;
        do {
            System.out.println("Please enter your email address to see reservations");
            customerEmail = userInput.nextLine();
        } while (!Utility.isEmailValid(customerEmail));

        Collection<Reservation> reservations = hotelResource.getCustomerReservations(customerEmail);

        if (reservations.isEmpty()) {
            System.out.println("Sorry! You don't have any reservation with us at the moment.");
        } else {
            System.out.println("Your Reservation(s)");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private void createAccount() {
        String customerEmail;
        boolean isEmailValid = false;
        do {
            System.out.println("Enter Email (format: name@domain.com)");
            customerEmail = userInput.nextLine();
            if (!Utility.isEmailValid(customerEmail)) {
                System.out.println("Invalid email format");
            } else if (adminResource.getCustomer(customerEmail) != null) {
                System.out.println("Email is already in use");
            } else {
                isEmailValid = true;
            }
        } while (!isEmailValid);

        String firstName;
        do {
            System.out.println("Enter First Name");
            firstName = userInput.nextLine();
        } while (firstName.isBlank());

        String lastName;
        do {
            System.out.println("Enter Last Name");
            lastName = userInput.nextLine();
        } while (lastName.isBlank());

        HotelResource.createACustomer(customerEmail, firstName, lastName);

        System.out.println("Yay! You now have an account in the app!");
    }

}
