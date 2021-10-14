package com.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int customerId;
    private String customerFirstName;
    private String customerLastName;
    private String mobileNumber;
    private String customerEmail;
    private String accountNumber;
    private List<Address> address;

    private void setMobileNumber(String mobileNumber){

        if (mobileNumber.isEmpty()){
            this.mobileNumber ="-1";
        }

        if (mobileNumber.length() == 11){
            if (mobileNumber.charAt(0) == '-' || mobileNumber.charAt(0) != '0' || mobileNumber.charAt(1) != '1'){
                this.mobileNumber ="-1";
            }else {
                this.mobileNumber = mobileNumber;
            }
        }else {
            this.mobileNumber ="-1";
        }

    }
}
