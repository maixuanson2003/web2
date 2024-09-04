package com.example.web2.DTO.response;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class actorResponse {
    private Integer id;
    private String full_name;
    private String phone_number;
    private String address;
    private String birthday;
    private String statusCard;

}
