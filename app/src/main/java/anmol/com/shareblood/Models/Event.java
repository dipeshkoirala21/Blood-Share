package anmol.com.shareblood.Models;

public class Event {
    String description;
    String name;
    String location;
    String date;
    String time;
    String uid;
    String contact;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public Event() {}

    public Event(String description, String name, String location, String date, String time, String uid, String contact, String organizer) {
        this.description = description;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.uid = uid;
        this.contact = contact;
    }




}
