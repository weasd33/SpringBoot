package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Aspect: AOP를 가능하게 하는 AspectJ 프로젝트에서 제공하는 어노테이션
@Aspect
@Component // Bean 등록
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // hello.hellospring 패키지 아래는 모두 적용
    //* hello.hellospring.service..*(..))" -> service만 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }
}
