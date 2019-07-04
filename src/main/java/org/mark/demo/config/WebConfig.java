package org.mark.demo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mark.demo.core.aop.CheckInterceptor;
import org.mark.demo.core.aop.GlobalExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.mark.demo.core.ProjectConstant.PROFILE_DEV;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final Logger logger = LoggerFactory.getLogger(WebConfig.class);
	@Value("${spring.profiles.active}")
	private String env;//当前激活的配置文件

	//使用阿里 FastJson 作为JSON MessageConverter
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//保留空的字段
		//SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
		//SerializerFeature.WriteNullNumberAsZero//Number null -> 0
		// 按需配置，更多参考FastJson文档哈

		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		converters.add(converter);
	}


	//统一异常处理
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new GlobalExceptionResolver());
	}

	//解决跨域问题
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//registry.addMapping("/**");
	}

	//添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token或其他更好的方式替代。
		//开发环境忽略签名认证
		if (!PROFILE_DEV.equals(env)) {
			registry.addInterceptor(new CheckInterceptor());
		}
	}


}
