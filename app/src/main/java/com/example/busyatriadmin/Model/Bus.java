package com.example.busyatriadmin.Model;

public class Bus {
    private String BusName,Image,Departure,Destination,BusNumber;
    private String Price,Contact;
    public Bus(){

    }

    public Bus(String busName,String image){
        this.BusName=busName;
        this.Image=image;
    }

    public Bus(String busName, String image, String departure, String destination, String busNumber, String price,String contact) {
        this.BusName = busName;
        this.Image = image;
        this.Departure = departure;
        this.Destination = destination;
        this.Price = price;
        this.Contact = contact;
        this.BusNumber = busNumber;
    }
    public Bus(String busName, String image, String departure, String destination, String busNumber) {
        this.BusName = busName;
        this.Image = image;
        this.Departure = departure;
        this.Destination = destination;
        this.BusNumber = busNumber;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String busNumber) {
        BusNumber = busNumber;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getBusName() {
        return BusName;
    }

    public void setBusName(String busName) {
        this.BusName = busName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        Departure = departure;
    }

    public String getDestination()
    {
        return Destination;
    }

    public void setDestination(String destination)
    {
        this.Destination = destination;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price)
    {
        this.Price = price;
    }
}
