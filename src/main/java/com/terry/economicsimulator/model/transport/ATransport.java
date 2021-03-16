package com.terry.economicsimulator.model.transport;

import com.terry.economicsimulator.model.fuel.AFuel;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ATransport {
    @Getter
    @NonNull
    private int maxSpeed;
    @Getter
    @NonNull
    private int aveSpeed;
    @Getter
    @NonNull
    private AFuel fuelType;
    @Getter
    @NonNull
    private int fullFuelAmount;
    @Getter
    @NonNull
    private int milesPerGallonMax;
    @Getter
    @NonNull
    private int milesPerGallonAve;
    @Getter
    @NonNull
    private String name;

    
}
