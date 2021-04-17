package by.epam_training.final_task.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ReservationDate implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date orderDate;
    private Date arrivalDate;
    private Date departureDate;

    public ReservationDate() {
    }

    public ReservationDate(Date orderDate, Date arrivalDate, Date departureDate) {
        this.orderDate = orderDate;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDate that = (ReservationDate) o;
        return Objects.equals(orderDate, that.orderDate) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(departureDate, that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, arrivalDate, departureDate);
    }
}
