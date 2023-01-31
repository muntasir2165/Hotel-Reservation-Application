package ui;

import api.AdminResource;
import model.*;
import utility.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static AdminMenu INSTANCE;
    private final AdminResource adminResource = AdminResource.getInstance();
    private Scanner userInput = new Scanner(System.in);

    public static AdminMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdminMenu();
        }

        return INSTANCE;
    }

    public void displayOptions() {
        boolean goBackToMainMenu = false;


        String selectedOption;

        do {
            System.out.println("Admin Menu");
            System.out.println("----------------------------------------------");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("----------------------------------------------");
            System.out.println("Please select a number for the menu option");

            selectedOption = userInput.nextLine();

            switch (selectedOption) {
                case "1":
                    displayAllCustomers();
                    break;
                case "2":
                    displayAllRooms();
                    break;
                case "3":
                    displayAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    goBackToMainMenu = true;
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        } while (!goBackToMainMenu);
    }

    private void displayAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Sorry! There is no customer in the application at the moment.");
        } else {
            System.out.println("Hotel Customers");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("Sorry! There is no room in the application at the moment.");
        } else {
            System.out.println("Hotel Rooms");
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    private void displayAllReservations() {
        Collection<Reservation> reservations = adminResource.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("Sorry! There is no reservation in the application at the moment.");
        } else {
            System.out.println("Hotel Reservations");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private IRoom getRoomAttributes(Collection<IRoom> newRooms) {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        String roomNumber;
        boolean isRoomNumberDuplicate;
        boolean isRoomNumberEmpty;
        do {
            System.out.println("Enter room number");
            roomNumber = userInput.nextLine();
            isRoomNumberDuplicate = Utility.isRoomNumberDuplicate(rooms, roomNumber) || Utility.isRoomNumberDuplicate(newRooms, roomNumber);
            isRoomNumberEmpty = roomNumber.isBlank();

            if (isRoomNumberDuplicate) {
                System.out.println("The room number you entered exists already.");
            }
            else if (isRoomNumberEmpty) {
                System.out.println("Room number cannot be an empty string.");
            }
        } while (isRoomNumberDuplicate || isRoomNumberEmpty);

        String roomPriceString;
        do {
            System.out.println("Enter price per night");
            roomPriceString = userInput.nextLine();
        } while (!Utility.isRoomPriceValid(roomPriceString));
        Double roomPrice = Double.parseDouble(roomPriceString);

        String roomTypeString;
        do {
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            roomTypeString = userInput.nextLine();
        } while (!Utility.isRoomTypeValid(roomTypeString));
        RoomType roomType = roomTypeString.equals("1") ? RoomType.SINGLE : RoomType.DOUBLE;

        IRoom room = roomPrice == 0 ? new FreeRoom(roomNumber, roomType) : new Room(roomNumber, roomPrice, roomType);

        return room;

    }

    private void addARoom() {
        Collection<IRoom> newRooms = new ArrayList<IRoom>();

        String shouldAddAnotherRoom = "y";
        do {
            if (shouldAddAnotherRoom.toLowerCase().equals("y")) {
                IRoom room = getRoomAttributes(newRooms);
                newRooms.add(room);
            }

            if (shouldAddAnotherRoom.toLowerCase().equals("y")) {
                System.out.println("Would you like to add another room y/n");
            } else if (shouldAddAnotherRoom.toLowerCase().equals("n")) {
                break;
            } else {
                System.out.println("Please enter Y (Yes) or N (No)");
            }
            shouldAddAnotherRoom = userInput.nextLine();
        } while (!shouldAddAnotherRoom.toLowerCase().equals("n"));

        adminResource.addRoom((List<IRoom>) newRooms);
    }
}
