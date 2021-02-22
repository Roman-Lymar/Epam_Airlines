package ua.nure.lymar.airlines.entity;

public enum Role {
    ADMIN("role.admin"),
    DISPETCHER("role.dispetcher");

    private final String name;

    Role(String name){
        this.name = name;
    }

    public Integer getId(){
        return ordinal();
    }

    public String getName() {
        return name;
    }
}

