package seedu.address.ui;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import javafx.application.Platform;

/**
 * JUnit 5 extension to initialize JavaFX for headless testing.
 */
public class JavaFXTestExtension implements BeforeAllCallback {

    private static boolean jfxStarted = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!jfxStarted) {
            // Set headless properties
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.verbose", "true");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
            System.setProperty("javafx.animation.framerate", "1");
            System.setProperty("quantum.multithreaded", "false");

            // Start JavaFX platform
            Platform.startup(() -> {});
            jfxStarted = true;
        }
    }
}
