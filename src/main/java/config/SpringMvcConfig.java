package config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import controller.LoginInterceptor;
import controller.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pojo.convert.StringToDateConverter;
import pojo.foramt.DateFormatter;
import pojo.foramt.FormatterRegisterFactory;

import java.io.IOException;
import java.util.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"controller", "dao", "exception", "pojo"})
public class SpringMvcConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>(2);
        mediaTypes.add(MediaType.valueOf("text/html;charset=UTF-8"));
        mediaTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(fastJsonHttpMessageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/user/**");

        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/user/**");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(new InternalResourceViewResolver("/pages/html/", ".jsp"));
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**")
                .addResourceLocations("/pages/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
        registry.addConverter(new StringToDateConverter());
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver multipartResolver= new  CommonsMultipartResolver();
        multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/uploads"));
        multipartResolver.setMaxUploadSize(2097152);
        multipartResolver.setMaxInMemorySize(0);
        return multipartResolver;
    }
}
