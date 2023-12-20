package models.ResponseModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.RequestModel.LoginRequest;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    public String token;
}
