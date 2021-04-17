package by.epam_training.final_task.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable{
    private static final long serialVersionUID = 1L;

    private int orderId;
    private int userId;
    private Date orderDate;
    private Date arrivalDate;
    private Date departureDate;
    private int cost;
    private String status;

    public Order() {
    }

    public Order(int orderId, int userId, Date orderDate, Date arrivalDate, Date departureDate, int cost, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.cost = cost;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order orderRoom = (Order) o;
        return orderId == orderRoom.orderId &&
                userId == orderRoom.userId &&
                cost == orderRoom.cost &&
                Objects.equals(orderDate, orderRoom.orderDate) &&
                Objects.equals(arrivalDate, orderRoom.arrivalDate) &&
                Objects.equals(departureDate, orderRoom.departureDate) &&
                Objects.equals(status, orderRoom.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, orderDate, arrivalDate, departureDate, cost, status);
    }

}
