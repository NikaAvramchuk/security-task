package com.example.securitytask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CachedValue {
    int attempts;
    LocalDateTime blockedTimestamp;
}
