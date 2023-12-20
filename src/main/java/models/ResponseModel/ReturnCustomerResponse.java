package models.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.RequestModel.AdditionalParametersRequest;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCustomerResponse {
    public String customerId;
    public String name;
    public String status;
    public Long phone;
    public AdditionalParametersRequest additionalParameters;
    public String pd;
}
