package ua.nure.lymar.airlines.entity;

public enum Profession {
	PILOT("profession.pilot"),
    CO_PILOT("profession.co_pilot"),
    AIR_NAVIGATOR("profession.air_navigator"),
    RADIST("profession.radist"),
    CABIN_CREW("profession.cabin_crew");

    private final String name;

    Profession(String name) {
        this.name = name;
    }

    public Integer getId(){
        return ordinal();	
    }

    public String getName() {
        return name;
    }
}
