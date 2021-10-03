package com.example.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor // 모든 Args를 가진 생성자
@NoArgsConstructor // default 생성자
public class Greeting { // application.yml 변수 가져오는 방법2. @Value 사용
    
    @Value("${greeting.message}")
    private String message;
}
