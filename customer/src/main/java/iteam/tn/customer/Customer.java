package iteam.tn.customer;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Customer {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
}
