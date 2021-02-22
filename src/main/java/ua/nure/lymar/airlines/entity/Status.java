package ua.nure.lymar.airlines.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Status {
    INPROCESS("status.inprocess"),
    WAIT("status.wait"),
    DENY("status.deny");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public Integer getId(){
        return ordinal();
    }

    public String getStatus() {
        return status;
    }

    public static Optional<Status> fromStatus(String status) {
        return Arrays.stream(values())
                .filter(bl -> bl.status.equalsIgnoreCase(status))
                .findFirst();
    }

}
