package info.novatec.testit.webtester.junit.runner.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.IllegalClassException;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;


public final class ConfigurationValueInjector {

    private static final String UNINJECTABLE_FIELD_TYPE = "cannot inject configuration values into fields of type ";
    private static final Object STATIC_TARGET = null;

    private static final Map<Class<?>, Injector> INJECTOR_MAP = new HashMap<>();

    static {

        INJECTOR_MAP.put(String.class,
            (config, key, field, target) -> field.set(target, config.getStringProperty(key).get()));

        INJECTOR_MAP.put(Integer.class,
            (config, key, field, target) -> field.set(target, config.getIntegerProperty(key).get()));

        INJECTOR_MAP.put(Long.class,
            (config, key, field, target) -> field.set(target, config.getLongProperty(key).get()));

        INJECTOR_MAP.put(Float.class,
            (config, key, field, target) -> field.set(target, config.getFloatProperty(key).get()));

        INJECTOR_MAP.put(Double.class,
            (config, key, field, target) -> field.set(target, config.getDoubleProperty(key).get()));

        INJECTOR_MAP.put(Boolean.class,
            (config, key, field, target) -> field.set(target, config.getBooleanProperty(key).get()));

    }

    public static void injectStatics(Configuration config, Class<?> targetClass) {
        for (Field field : targetClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                injectStaticField(config, field);
            }
        }
    }

    public static void inject(Configuration config, Object target) {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                injectField(config, field, target);
            }
        }
    }

    private static void injectStaticField(Configuration config, Field field) {
        injectField(config, field, STATIC_TARGET);
    }

    private static void injectField(Configuration config, Field field, Object target) {

        ConfigurationValue configurationValue = field.getAnnotation(ConfigurationValue.class);
        if (configurationValue == null) {
            // field should not be injected
            return;
        }

        field.setAccessible(true);

        Injector injector = INJECTOR_MAP.get(field.getType());
        if (injector == null) {
            throw new IllegalClassException(UNINJECTABLE_FIELD_TYPE + field.getType());
        }

        try {
            injector.injectInto(config, configurationValue.value(), field, target);
        } catch (IllegalAccessException e) {
            /* since fields are set accessible at the beginning of this method  IllegalAccessExceptions should not occur.
             * That makes it ok to throw an UndeclaredThrowableException */
            throw new UndeclaredThrowableException(e);
        }

    }

    public static boolean canInjectValue(Field field) {
        return INJECTOR_MAP.containsKey(field.getType());
    }

    public static Set<Class<?>> getInjectableFieldClasses() {
        return Collections.unmodifiableSet(INJECTOR_MAP.keySet());
    }

    private ConfigurationValueInjector() {
        // utility constructor
    }

    private interface Injector {
        void injectInto(Configuration config, String key, Field field, Object target) throws IllegalAccessException;
    }

}
