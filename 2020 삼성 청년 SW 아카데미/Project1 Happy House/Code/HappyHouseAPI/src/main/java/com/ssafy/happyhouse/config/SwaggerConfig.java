package com.project.happyhouse.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig { // 다른 사용자가 볼 수 있는 분서

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("projectHappyHouseBoard")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.project.happyhouse.controller")) // Swagger가 어디를 대상으로 볼지 결정
				.paths(PathSelectors.ant("/api/**")) // swagger대상은 /api로 시작한다.
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("project API")
				.description("project API Reference for Developers")
				.termsOfServiceUrl("https://edu.project.com")
				.license("project License")
				.licenseUrl("project@project.com").version("1.0").build();
	}

}


