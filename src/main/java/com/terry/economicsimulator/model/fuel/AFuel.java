package com.terry.economicsimulator.model.fuel;

import lombok.Getter;
import lombok.NonNull;

public abstract class AFuel {
    @Getter
    @NonNull
    private int cost;
    @Getter
    @NonNull
    private String name;
    
}
