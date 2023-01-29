package entity;

import java.util.Date;

public class bike {

    private int idBike;

    private String bikeSerialNumber;
    private String deposit;

    private String bikeType;
    private String isLooked;

    private String pin;



    public bike(int idBike, String bikeSerialNumber,String bikeType, String deposit,  String pin, String isLooked) {
        this.idBike = idBike;
        this.bikeSerialNumber = bikeSerialNumber;
        this.bikeType = bikeType;
        this.deposit = deposit;
        this.pin = pin;
        this.isLooked = isLooked;

    }
    public bike(){

    }

    public int getIdBike() {
        return idBike;
    }

    public void setIdBike(int idBike) {
        this.idBike = idBike;
    }

    public String getBikeSerialNumber() {
        return bikeSerialNumber;
    }

    public void setBikeSerialNumber(String bikeSerialNumber) {
        this.bikeSerialNumber = bikeSerialNumber;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }

    public String getIsLooked() {
        return isLooked;
    }

    public void setIsLooked(String isLooked) {
        this.isLooked = isLooked;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
