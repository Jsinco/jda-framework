package dev.jsinco.discord.framework.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Intended for use on static fields to inject values from another class.
 * @see dev.jsinco.discord.framework.FrameWork
 * @since 1.0
 * @author Jonah
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectStatic {
    Class<?> value();
    String specificField() default "";
}
