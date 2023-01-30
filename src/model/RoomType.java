package model;

public enum RoomType {
    SINGLE {
        @Override
        public String toString() {
            return "Single";
        }
    }, DOUBLE {
        @Override
        public String toString() {
            return "Double";
        }
    };
}
