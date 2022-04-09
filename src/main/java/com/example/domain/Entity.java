package com.example.domain;

import java.util.UUID;

public interface Entity {
    public UUID getId();

    public void setId(UUID entityId);

    public int entityId = 0;
}
