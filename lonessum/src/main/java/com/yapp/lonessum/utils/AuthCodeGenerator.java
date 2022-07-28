package com.yapp.lonessum.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Random;

@Getter
@Setter
@Component
public class AuthCodeGenerator {
    private int authCodeLength = 6;

    public String executeGenerate() {
        Random random = new Random(System.currentTimeMillis());

        int range = (int)Math.pow(10,authCodeLength);
        int trim = (int)Math.pow(10, authCodeLength-1);
        int result = random.nextInt(range)+trim;

        if(result>range){
            result = result - trim;
        }

        return String.valueOf(result);
    }
}
