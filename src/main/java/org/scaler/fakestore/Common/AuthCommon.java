package org.scaler.fakestore.Common;

import org.scaler.fakestore.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommon {

    private RestTemplate restTemplate;

    AuthCommon(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }


    public UserDTO validate(String token){
        ResponseEntity<UserDTO> response = restTemplate.getForEntity("http://localhost:8080/user/validate/"+token, UserDTO.class);

        if(!response.hasBody()){
            //throw new RuntimeException("Invalid Token");
            return null;
        }
        return response.getBody();
    }
}
