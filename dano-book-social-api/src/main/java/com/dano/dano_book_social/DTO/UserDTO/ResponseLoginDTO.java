package com.dano.dano_book_social.DTO.UserDTO;

public record ResponseLoginDTO(
    String firstName,
    String lastName,
    String email,
    String token,
    String type,
    long expiration
) {

}
