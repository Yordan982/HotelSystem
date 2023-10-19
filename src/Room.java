import java.util.List;

public class Room {
    String type;
    double price;
    int capacity;
    int squareMetres;

    public Room(String type, double price, int capacity, int squareMetres) {
        this.type = type;
        this.price = price;
        this.capacity = capacity;
        this.squareMetres = squareMetres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSquareMetres() {
        return squareMetres;
    }

    public void setSquareMetres(int squareMetres) {
        this.squareMetres = squareMetres;
    }

    @Override
    public String toString() {
        return String.format("%s, %.2f, %d, %d", this.getType(), this.getPrice(), this.getCapacity(), this.getSquareMetres());
    }
}