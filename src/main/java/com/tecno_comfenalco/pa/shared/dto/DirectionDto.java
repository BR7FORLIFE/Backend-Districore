package com.tecno_comfenalco.pa.shared.dto;

public record DirectionDto(
                String street,
                String neighborhood,
                String number,
                String city,
                String department,
                String country,
                String postalCode,
                String formattedAddress,
                String placeId,
                Double latitude,
                Double longitude) {

}
