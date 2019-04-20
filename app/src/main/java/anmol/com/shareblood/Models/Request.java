package anmol.com.shareblood.Models;

/**
 * Created by sukrit on 24/2/18.
 */

public class Request {
    String requestedBloodUnits,receiverBloodGroup,endDate,completed,gender,bloodFor;

    public Request() {
    }

    public Request(String requestedBloodUnits, String receiverBloodGroup, String endDate, String completed,
                   String gender, String bloodFor) {
        this.requestedBloodUnits = requestedBloodUnits;
        this.receiverBloodGroup = receiverBloodGroup;
        this.endDate = endDate;
        this.completed = completed;
        this.gender = gender;
        this.bloodFor = bloodFor;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodFor() {
        return bloodFor;
    }

    public void setBloodFor(String bloodFor) {
        this.bloodFor = bloodFor;
    }

    public String getRequestedBloodUnits() {
        return requestedBloodUnits;
    }

    public void setRequestedBloodUnits(String requestedBloodUnits) {
        this.requestedBloodUnits = requestedBloodUnits;
    }

    public String getReceiverBloodGroup() {
        return receiverBloodGroup;
    }

    public void setReceiverBloodGroup(String receiverBloodGroup) {
        this.receiverBloodGroup = receiverBloodGroup;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
