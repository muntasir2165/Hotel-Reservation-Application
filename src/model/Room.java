package model;

import java.util.Objects;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double roomPrice;
    private final RoomType roomType;
    private final boolean isFree;

    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
        this.isFree = roomPrice == 0.0;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + " " + roomType + " bed " + "Room Price: " + roomPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return isFree == room.isFree && roomNumber.equals(room.roomNumber) && roomPrice.equals(room.roomPrice) && roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomPrice, roomType);
    }
}
