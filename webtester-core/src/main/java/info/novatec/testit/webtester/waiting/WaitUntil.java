package info.novatec.testit.webtester.waiting;

import lombok.AccessLevel;
import lombok.Getter;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class offers a number of methods which allow for the waiting until a specific condition is met for any object type.
 *
 * @see Wait
 * @since 2.0
 */
@Getter(AccessLevel.PACKAGE)
public class WaitUntil<T> {

    private final Waiter waiter;
    private final WaitConfig config;
    private final T object;

    /**
     * Creates a new {@link WaitUntil} instance for the given {@link PageFragment} and {@link WaitConfig}.
     *
     * @param config the configuration to use
     * @param object the object to use
     * @see Wait
     * @since 2.0
     */
    WaitUntil(Waiter waiter, WaitConfig config, T object) {
        this.waiter = waiter;
        this.config = config;
        this.object = object;
    }

    /**
     * Waits until the given condition is met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> is(Condition<? super T> condition) throws TimeoutException {
        return doWait(condition);
    }

    /**
     * Waits until the given condition is met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> has(Condition<? super T> condition) throws TimeoutException {
        return doWait(condition);
    }

    /**
     * Waits until the given condition is NOT met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> isNot(Condition<? super T> condition) throws TimeoutException {
        return doWait(condition.negate());
    }

    /**
     * Waits until the given condition is NOT met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> hasNot(Condition<? super T> condition) throws TimeoutException {
        return doWait(condition.negate());
    }

    /**
     * Waits until the given condition is NOT met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> not(Condition<? super T> condition) throws TimeoutException {
        return doWait(condition.negate());
    }

    private WaitUntil<T> doWait(Condition<? super T> condition) {
        waiter.waitUntil(config, () -> condition.test(object));
        return this;
    }

    /**
     * This method does nothing by it's own. It is intended to be used in order to write more expressive linked wait
     * statements.
     * <p>
     * <b>Example:</b> {@code Wait.until(button).is(visible()).and().not(editable());}
     *
     * @return the same instance for fluent API use
     * @see Wait
     * @since 2.0
     */
    public WaitUntil<T> and() {
        return this;
    }

    /**
     * This method does nothing by it's own. It is intended to be used in order to write more expressive linked wait
     * statements.
     * <p>
     * <b>Example:</b> {@code Wait.until(button).is(visible()).but().not(editable());}
     *
     * @return the same instance for fluent API use
     * @see Wait
     * @since 2.0
     */
    public WaitUntil<T> but() {
        return this;
    }

}
