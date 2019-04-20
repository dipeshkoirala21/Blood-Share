package anmol.com.shareblood.Models;

/**
 * Created by sukrit on 24/2/18.
 */

public class ReceivedFrom {

    String donorId,receivingDate,hospitalAddress,receivedBloodGroup,receivedUnits;

    public ReceivedFrom() {
    }

    public ReceivedFrom(String donorId, String receivingDate, String hospitalAddress, String receivedBloodGroup, String receivedUnits) {
        this.donorId = donorId;
        this.receivingDate = receivingDate;
        this.hospitalAddress = hospitalAddress;
        this.receivedBloodGroup = receivedBloodGroup;
        this.receivedUnits = receivedUnits;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(String receivingDate) {
        this.receivingDate = receivingDate;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getReceivedBloodGroup() {
        return receivedBloodGroup;
    }

    public void setReceivedBloodGroup(String receivedBloodGroup) {
        this.receivedBloodGroup = receivedBloodGroup;
    }

    public String getReceivedUnits() {
        return receivedUnits;
    }

    public void setReceivedUnits(String receivedUnits) {
        this.receivedUnits = receivedUnits;
    }
}
