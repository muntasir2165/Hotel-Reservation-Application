import ui.MainMenu;

public class HotelApplication {
    private static final MainMenu mainMenu = MainMenu.getInstance();

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Reservation Application");

        mainMenu.displayOptions();
    }
}