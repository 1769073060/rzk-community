package com.rzk.pojo.wxserver;

import lombok.Data;

@Data
public class Token {
    private String accessToken;
    private int expiresIn;
}
