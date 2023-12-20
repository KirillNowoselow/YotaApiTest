package models.RequestModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    public String name;
    public Long phone;
    public AdditionalParametersRequest additionalParameters;

}
