package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class EventAspect {

    @AfterReturning("execution(public * Observable.*(..)) && target(observable)")
    public void notifyObservers(JoinPoint jp, Observable observable) {
        System.out.println(jp.toLongString());
        observable.notifyObservers();
    }

}
