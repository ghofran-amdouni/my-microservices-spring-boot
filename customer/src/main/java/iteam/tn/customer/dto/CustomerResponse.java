package iteam.tn.customer.dto;

import iteam.tn.customer.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {
}
