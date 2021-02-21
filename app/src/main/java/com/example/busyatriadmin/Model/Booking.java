package com.example.busyatriadmin.Model;

public class Booking {
    private String BusNumber;
    private String BusName;
    private String Price;
    private String Departure;
    private String Destination;
    private String TravelDate;
    private String ContactNumber;
    private String Name;
    private String Status;

    public Booking() {
    }

    public Booking(String status,String name, String departure, String destination, String travelDate, String contactNumber, String busNumber, String busName, String price) {
        Name = name;
        Departure = departure;
        Destination = destination;
        TravelDate = travelDate;
        ContactNumber = contactNumber;
        BusName = busName;
        BusNumber = busNumber;
        Price = price;
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String busNumber) {
        BusNumber = busNumber;
    }

    public String getBusName() {
        return BusName;
    }

    public void setBusName(String busName) {
        BusName = busName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        this.Departure = departure;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        this.Destination = destination;
    }

    public String getTravelDate() {
        return TravelDate;
    }

    public void setTravelDate(String travelDate) {
        TravelDate = travelDate;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}
