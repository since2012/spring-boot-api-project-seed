package org.mark.demo.core.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.mark.demo.core.ServiceException;
import org.mark.demo.core.result.Result;
import org.mark.demo.core.result.ResultCode;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @FileName GlobalExceptionResolver
 * @Description TODO
 * @Author markt
 * @Date 2019-07-04
 * @Version 1.0
 */
@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		Result result = new Result();
		if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
			result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
			log.info(e.getMessage());
		} else if (e instanceof NoHandlerFoundException) {
			result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
		} else if (e instanceof ServletException) {
			result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
		} else {
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
			String message;
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
						request.getRequestURI(),
						handlerMethod.getBean().getClass().getName(),
						handlerMethod.getMethod().getName(),
						e.getMessage());
			} else {
				message = e.getMessage();
			}
			log.error(message, e);
		}
		responseResult(response, result);
		return new ModelAndView();
	}

	private void responseResult(HttpServletResponse response, Result result) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(200);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}
}
