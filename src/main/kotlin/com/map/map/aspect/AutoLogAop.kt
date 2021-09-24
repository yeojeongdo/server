package com.map.map.aspect;

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}

@Component
@Aspect
class AutoLogAop {
	@Pointcut("@annotation(com.map.map.annotation.AutoLogging)")
	fun logging(){}

    @Before("logging()")
    fun beforeMethod(joinPoint: JoinPoint){
        var signature = joinPoint.signature

        var methodName = signature.name
        var className = joinPoint.target.javaClass.name
        var ip = getIp()

        var printMessage = makeString(methodName, className, ip!!)
        logger.info(printMessage)
    }

    @Around("logging()")
    fun aroundMethod(joinPoint: ProceedingJoinPoint): Any {
        var stopWatch = StopWatch()

        stopWatch.start()

        var signature = joinPoint.signature
        var methodName = signature.name
        var className = joinPoint.target.javaClass.name
        var ip = getIp()

        var proceed = joinPoint.proceed()

        stopWatch.stop()

        var printMessage = makeString(methodName, className, ip!!, stopWatch.totalTimeMillis)
        logger.info(printMessage)

        return proceed
    }

    private fun makeString(methodName:String, className: String, ip: String): String{
        return StringBuffer().append("\n")
            .append("ip : ").append(ip).append("\n")
            .append("className : ").append(className).append("\n")
            .append("methodName : ").append(methodName).append("\n")
            .toString()
    }

    private fun makeString(methodName:String, className: String, ip: String, time: Long): String{
        return StringBuffer().append("\n")
            .append("ip : ").append(ip).append("\n")
            .append("className : ").append(className).append("\n")
            .append("methodName : ").append(methodName).append("\n")
            .append("taskTime : ").append(time).append("\n")
            .toString()
    }

    private fun getIp(): String? {
        val request = (RequestContextHolder
            .currentRequestAttributes() as ServletRequestAttributes).request
        return decodingIpHeader(request)
    }

    private fun decodingIpHeader(request: HttpServletRequest): String? {
        var ip = request.getHeader("X-Forwarded-For")
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP") // 웹로직
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (ip == null) {
            ip = request.remoteAddr
        }
        return ip
    }
}
