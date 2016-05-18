package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.operations.Window;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a window was maximized.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Window#maximize()
 * @since 2.0
 */
@SuppressWarnings("serial")
public class MaximizedWindowEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "maximized window";
    }

}