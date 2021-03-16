package com.terry.economicsimulator.model.resource;

import lombok.Getter;
import lombok.NonNull;

public abstract class AResource implements IResource {
    @Getter
    @NonNull
    private int amount;
    @Getter
    @NonNull
    private String name;
    
}
