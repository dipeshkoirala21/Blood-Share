package anmol.com.shareblood.Models;

/**
 * Created by sukrit on 20/2/18.
 */

public class Required {

    String userName;
    String userAddress;
    String userHospital;
    String userContactNumber;
    String userBloodGroup;
    String userEmailAddress;
    String userUnitsBlood;
    String userStartDate;
    String userEndDate;
    String userPassword;

    public Required() {
    }

    public Required(String userName, String userAddress, String userHospital, String userContactNumber, String userBloodGroup, String userEmailAddress, String userUnitsBlood, String userStartDate, String userEndDate, String userPassword) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userHospital = userHospital;
        this.userContactNumber = userContactNumber;
        this.userBloodGroup = userBloodGroup;
        this.userEmailAddress = userEmailAddress;
        this.userUnitsBlood = userUnitsBlood;
        this.userStartDate = userStartDate;
        this.userEndDate = userEndDate;
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserStartDate() {
        return userStartDate;
    }

    public void setUserStartDate(String userStartDate) {
        this.userStartDate = userStartDate;
    }

    public String getUserEndDate() {
        return userEndDate;
    }

    public void setUserEndDate(String userEndDate) {
        this.userEndDate = userEndDate;
    }

    public String getUserUnitsBlood() {
        return userUnitsBlood;
    }

    public void setUserUnitsBlood(String userUnitsBlood) {
        this.userUnitsBlood = userUnitsBlood;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserHospital() {
        return userHospital;
    }

    public void setUserHospital(String userHospital) {
        this.userHospital = userHospital;
    }

    public String getUserContactNumber() {
        return userContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }

    public String getUserBloodGroup() {
        return userBloodGroup;
    }

    public void setUserBloodGroup(String userBloodGroup) {
        this.userBloodGroup = userBloodGroup;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }
}
