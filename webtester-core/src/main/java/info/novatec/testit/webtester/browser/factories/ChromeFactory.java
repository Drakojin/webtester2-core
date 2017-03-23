package info.novatec.testit.webtester.browser.factories;

import org.openqa.selenium.chrome.ChromeDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;


/**
 * Factory class for creating Chrome {@link Browser} instances.
 * Needs the {@code webdriver.chrome.driver} system property pointing to the driver proxy server executable.
 * <p>
 * <b>The following capabilities are set by default:</b>
 * <ul>
 * <li>Native Events are disabled</li>
 * <li>Unsigned certificates are accepted</li>
 * </ul>
 * <b>Additional information on using the {@link ChromeDriver}:</b>
 * <p>
 * https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver
 *
 * @see Browser
 * @see ChromeDriver
 * @since 2.1
 */
public class ChromeFactory extends BaseBrowserFactory<ChromeFactory> {

    private static final String CHROME_DRIVER_LOCATION = "webdriver.chrome.driver";

    public ChromeFactory() {
        super(ChromeDriver::new);
    }

    @Override
    protected void postProcessConfiguration(Configuration configuration) {
        configuration.getStringProperty(CHROME_DRIVER_LOCATION).ifPresent(driverLocation -> {
            System.setProperty(CHROME_DRIVER_LOCATION, driverLocation);
        });
    }

}
