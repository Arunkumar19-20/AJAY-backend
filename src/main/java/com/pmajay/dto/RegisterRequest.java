package com.pmajay.dto;

import com.pmajay.model.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private User.Role role;
    private Long stateId;
}
