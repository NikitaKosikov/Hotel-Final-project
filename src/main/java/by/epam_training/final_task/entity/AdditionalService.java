package by.epam_training.final_task.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AdditionalService implements Serializable {

    private static final long serialVersionUID = 1L;

    private int serviceId;  
    private String name;
    private int cost;
    private String urlPhoto;
    private Date dateOfService;

    public AdditionalService() {
    }

    public AdditionalService(int serviceId, String name, int cost, String urlPhoto) {
        this.serviceId = serviceId;
        this.name = name;
        this.cost = cost;
        this.urlPhoto = urlPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public Date getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(Date dateOfService) {
        this.dateOfService = dateOfService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalService that = (AdditionalService) o;
        return serviceId == that.serviceId &&
                Double.compare(that.cost, cost) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(urlPhoto, that.urlPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, name, cost, urlPhoto);
    }
}
