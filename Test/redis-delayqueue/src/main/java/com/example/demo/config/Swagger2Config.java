package com.example.demo.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi(){
        Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("com.example.demo.controller");
        Predicate<RequestHandler> selector2 = RequestHandlerSelectors.basePackage("com.example.demo.controller");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档 //单个配置controller
                //.apis(RequestHandlerSelectors.basePackage("com.cnczsq.mall.elephant.v1.controller"))
                //controller批量配 方式一
                // .apis(Predicates.or(selector1,selector2))
                // controller批量配方式二    指定所有controller的都实现的一个接口，比如@RestController
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                // controller批量配方式三    指定所有controller路径的父级
                //.apis(RequestHandlerSelectors.basePackage("com.cnczsq.mall.elephant"))
                .paths(PathSelectors.any())
                .build();
                //添加登录认证
//                .securitySchemes(securitySchemes()) //下面这两个不是必须
//                .securityContexts(securityContexts());
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2")
                .description("更多请关注http://www.hao123.com")
                .termsOfServiceUrl("http://www.baidu.com")
                .contact("zsq")
                .version("1.0")
                .build();
    }

    //end  简单配置到这里基本就够了

//    private List<ApiKey> securitySchemes() {
//        //设置请求头信息
//        List<ApiKey> result = new ArrayList<>();
//        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
//        result.add(apiKey);
//        return result;
//    }
//
//    private List<SecurityContext> securityContexts() {
//        //设置需要登录认证的路径
//        List<SecurityContext> result = new ArrayList<>();
//        result.add(getContextByPath("/brand/.*"));
//        return result;
//    }
//
//    private SecurityContext getContextByPath(String pathRegex){
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex(pathRegex))
//                .build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        List<SecurityReference> result = new ArrayList<>();
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        result.add(new SecurityReference("Authorization", authorizationScopes));
//        return result;
//    }
}
