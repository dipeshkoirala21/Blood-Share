package anmol.com.shareblood.Models;

/**
 * Created by sukrit on 24/2/18.
 */

public class DonatedTo
{
    String receiverId,donatingDate,hospitalAddress,donatedBlood,donatedUnits;

    public DonatedTo() {
    }

    public DonatedTo(String receiverId, String donatingDate, String hospitalAddress, String donatedBlood, String donatedUnits) {
        this.receiverId = receiverId;
        this.donatingDate = donatingDate;
        this.hospitalAddress = hospitalAddress;
        this.donatedBlood = donatedBlood;
        this.donatedUnits = donatedUnits;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getDonatingDate() {
        return donatingDate;
    }

    public void setDonatingDate(String donatingDate) {
        this.donatingDate = donatingDate;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getDonatedBlood() {
        return donatedBlood;
    }

    public void setDonatedBlood(String donatedBlood) {
        this.donatedBlood = donatedBlood;
    }

    public String getDonatedUnits() {
        return donatedUnits;
    }

    public void setDonatedUnits(String donatedUnits) {
        this.donatedUnits = donatedUnits;
    }

}