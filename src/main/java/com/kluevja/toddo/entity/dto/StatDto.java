package com.kluevja.toddo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service @Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {
    private String name;
    private String value;
}
