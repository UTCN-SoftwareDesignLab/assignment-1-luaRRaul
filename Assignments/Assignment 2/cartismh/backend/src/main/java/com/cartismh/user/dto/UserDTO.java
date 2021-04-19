package com.cartismh.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
}
