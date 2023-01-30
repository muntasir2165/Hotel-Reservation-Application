import ui.AdminMenu;
import ui.MainMenu;

import java.util.Scanner;

public class HotelApplication {
    private static MainMenu mainMenu = MainMenu.getInstance();

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Reservation Application");

        mainMenu.displayOptions();
    }
}