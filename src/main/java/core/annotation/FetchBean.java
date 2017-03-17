package main.java.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

/**
 * Created by digvijaysharma on 29/01/17.
 */

/**
 * @FetchBean Annotation is used for intersharing of maos, services and resources through reflection in java.
 * @Usage :-
 * For fields annotated with this method, always check if field is not null before using them. Their beans may not
 * have been fetched if the beans are invalid or some other reason.
 */
@NameBinding @Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FetchBean {

}

