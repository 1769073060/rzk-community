package com.rzk.pojo;

import lombok.Data;

@Data
public class PhoneMessage {

    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Object watermark;


}
