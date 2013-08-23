package org.jboss.tools.bpmn2.itests.reddeer.suite;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.eclipse.swtbot.swt.finder.junit.ScreenshotCaptureListener;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class BPMN2Suite extends RedDeerSuite {

	public BPMN2Suite(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
		super(clazz, foo(builder));
	}

	private static RunnerBuilder foo(RunnerBuilder builder) {
		// readConfigurationProperties();
		closeWelcomeScreen();
		return builder;
	}
	
	@Override
	public void run(RunNotifier notifier) {
		RunListener failureSpy = new ScreenshotCaptureListener();
		notifier.removeListener(failureSpy);
		notifier.addListener(failureSpy);
		try {
			super.run(notifier);
		} finally {
			notifier.removeListener(failureSpy);
		}
	}

	private static void readConfigurationProperties() {
		Properties props = null;
		try {
			String propsFilePath = System.getProperty("swtbot.test.properties");

			props = System.getProperties();
			props.load(new FileInputStream(new File(propsFilePath)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void closeWelcomeScreen() {
		try {
			Bot.get().viewByTitle("Welcome").close();
		} catch (Exception ex) {
			// Ignore
		}
	}

}