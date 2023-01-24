package entity;

import java.util.Date;

public class bike {

    private int idBike;

    private String bikeSerialNumber;
    private int deposit;

    private String bikeType;
    private String isLooked;

    private String pin;



    public bike(int idBike, String bikeSerialNumber,String bikeType, int deposit,  String pin, String isLooked) {
        this.idBike = idBike;
        this.bikeSerialNumber = bikeSerialNumber;
        this.bikeType = bikeType;
        this.deposit = deposit;
        this.pin = pin;
        this.isLooked = isLooked;

    }
}
