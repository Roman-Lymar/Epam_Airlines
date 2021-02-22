package ua.nure.lymar.airlines.entity;

public enum FlightStatus {
    NEW("flightstatus.new"),
    OPENED("flightstatus.opened"),
    CLOSED("flightstatus.closed"),
    CANCELED("flightstatus.canceled");

    private final String name;

    FlightStatus(String name){
        this.name = name;
    }

    public Integer getId(){
        return ordinal();
    }

    public String getName() {
        return name;
    }
}
