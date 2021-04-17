package by.epam_training.final_task.entity;


import java.io.Serializable;
import java.util.Objects;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private int roomId;
    private int numberOfBeds;
    private String apartmentClass;
    private int cost;
    private String urlPhoto;

    public Room() {
    }

    public Room(int roomId, int numberOfBeds, String apartmentClass, int cost, String urlPhoto) {
        this.roomId = roomId;
        this.numberOfBeds = numberOfBeds;
        this.apartmentClass = apartmentClass;
        this.cost = cost;
        this.urlPhoto = urlPhoto;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public String getApartmentClass() {
        return apartmentClass;
    }

    public void setApartmentClass(String apartmentClass) {
        this.apartmentClass = apartmentClass;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomId == room.roomId &&
                numberOfBeds == room.numberOfBeds &&
                cost == room.cost &&
                Objects.equals(apartmentClass, room.apartmentClass) &&
                Objects.equals(urlPhoto, room.urlPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, numberOfBeds, apartmentClass, cost, urlPhoto);
    }
}
