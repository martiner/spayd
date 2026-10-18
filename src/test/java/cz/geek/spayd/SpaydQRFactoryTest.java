package cz.geek.spayd;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpaydQRFactoryTest {

	// lower case letters keep the QR in BYTE mode, where the ECI header would show
	static final String PAYMENT = "SPD*1.0*ACC:CZ5820100000002000009442*AM:100.00*CC:CZK*MSG:thanks for geek spayd*X-VS:123";
	static final int SIZE = 250;

	@Test
	public void unbrandedImageMatchesReference() throws IOException {
		final SpaydQRFactory factory = new SpaydQRFactory();
		factory.setBranded(false);
		factory.setSize(SIZE);

		final BufferedImage actual = factory.createQRCode(PAYMENT);

		final BufferedImage expected;
		try (InputStream in = getClass().getResourceAsStream("payment-unbranded.png")) {
			expected = ImageIO.read(in);
		}
		assertThat(actual.getType(), is(BufferedImage.TYPE_BYTE_BINARY));
		assertThat(actual.getWidth(), is(expected.getWidth()));
		assertThat(actual.getHeight(), is(expected.getHeight()));
		for (int y = 0; y < expected.getHeight(); y++) {
			for (int x = 0; x < expected.getWidth(); x++) {
				assertThat("pixel " + x + "," + y, actual.getRGB(x, y), is(expected.getRGB(x, y)));
			}
		}
	}

	@Test
	public void brandedImageHasExpectedGeometry() {
		final SpaydQRFactory factory = new SpaydQRFactory();
		factory.setSize(SIZE);

		final BufferedImage image = factory.createQRCode(PAYMENT);

		// version 6-M has 41 modules, so barsize is 250 / (41 + 8) = 5 and the width is 250 + 8 * 5
		final int width = 290;
		final int padding = 20;
		assertThat(image.getType(), is(BufferedImage.TYPE_BYTE_BINARY));
		assertThat(image.getWidth(), is(width));
		assertThat(image.getHeight() > SIZE + padding, is(true));
	}
}
