package naf.norsys.reservation.utils;


import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

@Component
public class ClassTypeProvider {
    public Class<?>[] getClasses(Object targetClassObject, Class<?> genericSuperClass) {
        //get classes generic
        return GenericTypeResolver.resolveTypeArguments(targetClassObject.getClass(), genericSuperClass);
    }

}
