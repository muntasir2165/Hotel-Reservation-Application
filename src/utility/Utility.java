package utility;

import model.IRoom;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

public class Utility {
    public static boolean isDateInputValid(String date) {
        // Found the following email regex at: https://stackoverflow.com/questions/20231539/java-check-the-date-format-of-current-string-is-according-to-required-format-or#:~:text=DateFormat%20formatter%20%3D%20new%20SimpleDateFormat(%22,in%20different%20format%20or%20invalid.%20%7D
        final Pattern pattern =
                Pattern.compile("^\"([0-9]{2})/([0-9]{2})/([0-9]{4})\"$");
        return pattern.matcher(date).matches();
    }

    public static Date stringToDate(String dateString) {
        Date date;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = formatter.parse(dateString);
        } catch (Exception e) {
            return null;
        }

        return date;
    }

    public static boolean isEmailValid(String email) {
        // Found the following email regex at: https://stackoverflow.com/questions/8204680/java-regex-email
        final Pattern pattern =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    public static boolean isRoomAvailableForReservation(Collection<IRoom> rooms, String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isRoomNumberDuplicate(Collection<IRoom> rooms, String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isRoomPriceValid(String roomPriceString) {
        try {
            Double.parseDouble(roomPriceString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isRoomTypeValid(String roomType) {
        return roomType.equals("1") || roomType.equals("2");
    }
}
