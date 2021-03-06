package info.novatec.testit.webtester.events.pagefragments;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever a selection is made by multiple texts.
 * <p>
 * It contains the selected texts as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see MultiSelect
 * @since 2.0
 */
@Getter
public class SelectedByTextsEvent extends AbstractPageFragmentEvent {

    private final List<String> texts;

    public SelectedByTextsEvent(PageFragment fragment, Collection<String> texts) {
        super(fragment);
        this.texts = Collections.unmodifiableList(new LinkedList<>(texts));
    }

    @Override
    public String describe() {
        return "selected texts: " + texts + " of " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<SelectedByTextsEvent> {

        private List<String> before;
        private List<String> after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectedByTextsEvent> setBeforeData(WebElement webElement) {
            before = new Select(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getText()))
                .collect(Collectors.toList());
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectedByTextsEvent> setAfterData(WebElement webElement) {
            after = new Select(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getText()))
                .collect(Collectors.toList());
            return this;
        }

        @Override
        protected SelectedByTextsEvent buildWith(PageFragment fragment) {
            List<String> values = new LinkedList<>(after);
            values.removeAll(before);
            return new SelectedByTextsEvent(fragment, values);
        }

    }

}
