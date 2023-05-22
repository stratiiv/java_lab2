package com.example.lab2;

public class Car {
    private String number;
    private String brand;
    private String model;
    private int year;
    private String ownerName;
    private String ownerAddress;

    public Car(String number, String brand, String model, int year, String ownerName, String ownerAddress) {
        this.number = number;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
    }

    // Getters and setters for the fields

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    @Override
    public String toString() {
        return "Car{" +
                "number='" + number + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAddress='" + ownerAddress + '\'' +
                '}';
    }
}

