package com.project.happyhouse.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
		basePackages = "com.project.happyhouse.dao" // 어떠한 데이터베이스에 접근하는 인터페이스를 만들면 스프링이 대신 impl을 만들어준다. -> 우리가 만들 필요 없어 -> MyBatis에서 책임지겠다.
)
// @Configuration: 이 자바 클래스가 예전 xml 설정을 대신할 수 있다. -> 환경설정하는 클래스에서 사용
// @MapperScan: basePackages: impl을 없애고 기존에 사용한 impl을 사용할 수 있게한다. -> interface방식으로 사용 가능 -> dao에서 @안해도 된다.(여기서 관리해줌)
public class DatabaseConfig {}