package anmol.com.shareblood.Models;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sukrit on 24/2/18.
 */

public class UserDetails
{
    String name,email,address,hospitalAddress,bloodGroup,password,deviceToken,donationCount,
        receivedCount,donatedTo,receivedFrom,image;
        ArrayList<Map<String,Request>> requests;

    public UserDetails() {
    }

    public UserDetails(String name, String email, String address,
                       String hospitalAddress, String bloodGroup,
                       String password, String deviceToken,
                       String donationCount, String receivedCount,
                       String donatedTo, String receivedFrom,
                       ArrayList<Map<String, Request>> requests, String image) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.hospitalAddress = hospitalAddress;
        this.bloodGroup = bloodGroup;
        this.password = password;
        this.deviceToken = deviceToken;
        this.donationCount = donationCount;
        this.receivedCount = receivedCount;
        this.donatedTo = donatedTo;
        this.receivedFrom = receivedFrom;
        this.requests = requests;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDonationCount() {
        return donationCount;
    }

    public void setDonationCount(String donationCount) {
        this.donationCount = donationCount;
    }

    public String getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(String receivedCount) {
        this.receivedCount = receivedCount;
    }

    public String getDonatedTo() {
        return donatedTo;
    }

    public void setDonatedTo(String donatedTo) {
        this.donatedTo = donatedTo;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(String receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public ArrayList<Map<String, Request>> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Map<String, Request>> requests) {
        this.requests = requests;
    }
}
