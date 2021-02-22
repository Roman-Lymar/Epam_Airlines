package ua.nure.lymar.airlines.entity;

/**
 * JavaBean class of Staff entity
 */
public class Staff extends Entity {
    private String name;
    private String surname;
    private Profession profession;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
