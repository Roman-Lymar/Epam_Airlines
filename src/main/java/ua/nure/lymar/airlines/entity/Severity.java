package ua.nure.lymar.airlines.entity;

public class Severity extends Entity {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    @Override
    public String toString() {
        return "id = " + getId() + ", " + getDescription();
    }

}
