package ua.nure.lymar.airlines.entity;


import java.util.Date;

public class Message extends Entity {

    private Integer dispatcherId;
    private String messageText;
    private Severity severity;
    private Date issuedDate;
    private String dispatcherName;
    private String severityDescription;
    private Status status;

    public void setSeverityDescription(String severityDescription) {
        this.severityDescription = severityDescription;
    }

    public Integer getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(Integer dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getDispatcherName() {
        return dispatcherName;
    }

    public void setDispatcherName(String dispatcherName) {
        this.dispatcherName = dispatcherName;
    }

    public String getSeverityDescription() {
        return severityDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "dispatcher = " + getDispatcherId() + ", severity = " + getSeverity() + ", " + getMessageText() + ", " + getIssuedDate();
    }

}
