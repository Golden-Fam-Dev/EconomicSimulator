package com.terry.economicsimulator.model.Distance;

import com.terry.economicsimulator.model.resource.IResource;

import lombok.Getter;
import lombok.NonNull;

public class Distance {
    @Getter
    @NonNull
    private IResource locationA;
    @Getter
    @NonNull
    private IResource locationB;
    @Getter
    @NonNull
    private int distance;
    
}
