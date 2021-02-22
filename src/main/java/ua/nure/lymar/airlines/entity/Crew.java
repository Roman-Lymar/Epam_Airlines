package ua.nure.lymar.airlines.entity;

/**
 * JavaBean class of Crew entity
 */
public class Crew extends Entity {
	private Users user;
    private String name;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
