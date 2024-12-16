package com.parking.demo.Entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ChargesServiceImpl implements IChargesService{
    private static final Map<VehicleType, Double> rates = new HashMap<>();

    static{
        rates.put(VehicleType.CAR,10.0);
        rates.put(VehicleType.BIKE,5.0);
        rates.put(VehicleType.TRUCK,15.0);
    }
    @Override
    public double calculateCharges(VehicleType vehicleType, LocalDateTime entryTime, LocalDateTime exitTime) {
    Duration duration=Duration.between(entryTime,exitTime);
    long hours=duration.toHours();
    if(hours%60!=0 || hours==0){
        hours+=1;
    }
    return hours*rates.getOrDefault(vehicleType,10.0);

    }

}
