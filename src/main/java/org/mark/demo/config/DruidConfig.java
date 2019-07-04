package org.mark.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 */
@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
public class DruidConfig {


	/**
	 * druidServlet注册
	 */
//	@Bean
//	public ServletRegistrationBean druidServletRegistration() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(new StatViewServlet());
//		registration.addUrlMappings("/druid/*");
//		return registration;
//	}

	/**
	 * druid监控 配置URI拦截策略
	 */
//	@Bean
//	public FilterRegistrationBean druidStatFilter() {
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//		//添加过滤规则.
//		filterRegistrationBean.addUrlPatterns("/*");
//		//添加不需要的格式信息.
//		filterRegistrationBean.addInitParameter(
//				"exclusions", "/static/**,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*");
//		//用于session监控页面的用户名显示 需要登录后主动将username注入到session里
//		filterRegistrationBean.addInitParameter("principalSessionName", "username");
//		return filterRegistrationBean;
//	}

	/**
	 * druid数据库连接池监控
	 */
	@Bean
	public DruidStatInterceptor druidStatInterceptor() {
		return new DruidStatInterceptor();
	}

	/**
	 * 切点
	 *
	 * @return
	 */
//	@Bean
//	public JdkRegexpMethodPointcut druidStatPointcut() {
//		JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
//		String patterns = "org.mark.demo.*.service.*";
//		//可以set多个
//		druidStatPointcut.setPatterns(patterns);
//		return druidStatPointcut;
//	}

	/**
	 * druid数据库连接池监控
	 */
	@Bean
	public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator() {
		BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
		beanTypeAutoProxyCreator.setTargetBeanType(DruidDataSource.class);
		beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
		return beanTypeAutoProxyCreator;
	}

	/**
	 * druid 为druidStatPointcut添加拦截
	 *
	 * @return
	 */
//	@Bean
//	public Advisor druidStatAdvisor() {
//		return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
//	}

	/**
	 * RequestContextListener注册
	 */
//	@Bean
//	public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
//		return new ServletListenerRegistrationBean<>(new RequestContextListener());
//	}

}
