/*
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

import static org.apache.commons.lang.Validate.notEmpty;
import static org.apache.commons.lang.Validate.notNull;

public class SpaydQRFactory {

	public static final int MIN_QR_SIZE = 200;
	public static final int DEFAULT_QR_SIZE = 250;
	public static final int MAX_QR_SIZE = 800;
	private final String brandFont = "Verdana";

	private int size = DEFAULT_QR_SIZE;

	private boolean branded = true;

	private String brandText = "QR Platba";

	public void saveQRCode(SpaydPayment spaydPayment, String formatName, OutputStream stream) {
		try {
			ImageIO.write(createQRCode(spaydPayment), formatName, stream);
		} catch (IOException e) {
			throw new SpaydQRException("Unable to create QR code", e);
		}
	}

	public void saveQRCode(SpaydPayment spaydPayment, String formatName, File file) {
		try {
			ImageIO.write(createQRCode(spaydPayment), formatName, file);
		} catch (IOException e) {
			throw new SpaydQRException("Unable to create QR code", e);
		}
	}

	public BufferedImage createQRCode(SpaydPayment spaydPayment) {
		notNull(spaydPayment);
		return createQRCode(spaydPayment.asString());
	}
	/**
	 * Generate a QR code with the payment information of a given size, optionally, with branded
	 * @param paymentString A SPAYD string with payment information
	 * @return An image with the payment QR code
	 * @throws SpaydQRException
	 */
	public BufferedImage createQRCode(String paymentString) {
		notEmpty(paymentString);

		final BitMatrix matrix;
		final int barsize;
		final Writer writer = new MultiFormatWriter();
		try {
			final Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
			final QRCode code = Encoder.encode(paymentString, ErrorCorrectionLevel.M, hints);
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			barsize = size / (code.getMatrix().getWidth() + 8);
			matrix = writer.encode(paymentString, BarcodeFormat.QR_CODE, size, size, hints);
		} catch (WriterException e) {
			throw new SpaydQRException("Unable to create QR code", e);
		}

		final BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

		return isBranded() ? brandImage(image, barsize) : image;
	}

	protected BufferedImage brandImage(BufferedImage image, int barsize) {
		final Graphics2D g = (Graphics2D) image.getGraphics();
		final Font font = new Font(brandFont, Font.BOLD, size / 12);

		final BasicStroke bs = new BasicStroke(2);
		g.setStroke(bs);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, size, 0);
		g.drawLine(0, 0, 0, size);
		g.drawLine(size, 0, size, size);
		g.drawLine(0, size, size, size);

		g.setFont(font);

		final FontMetrics fm = g.getFontMetrics();
		final Rectangle2D rect = fm.getStringBounds(brandText, g);

		g.setColor(Color.WHITE);
		g.fillRect(2 * barsize, size - fm.getAscent(), (int) rect.getWidth() + 4 * barsize, (int) rect.getHeight());

		final int padding = 4 * barsize;

		final BufferedImage paddedImage = new BufferedImage(size + 2 * padding, size + padding + (int) rect.getHeight(), image.getType());
		final Graphics2D g2 = paddedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2.setFont(font);
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, paddedImage.getWidth(), paddedImage.getHeight());
		g2.drawImage(image, padding, padding, Color.WHITE, null);

		g2.setColor(Color.BLACK);
		g2.drawString(brandText, padding + 4 * barsize, (int) (padding + size + rect.getHeight() - barsize));
		return paddedImage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size > MAX_QR_SIZE)
			throw new IllegalArgumentException("Max size is " + MAX_QR_SIZE);
		if (size < MIN_QR_SIZE)
			throw new IllegalArgumentException("Min size is " + MIN_QR_SIZE);
		this.size = size;
	}

	public boolean isBranded() {
		return branded;
	}

	public void setBranded(boolean branded) {
		this.branded = branded;
	}

	public String getBrandText() {
		return brandText;
	}

	public void setBrandText(String brandText) {
		notEmpty(brandText);
		this.brandText = brandText;
	}

	public String getBrandFont() {
		notEmpty(brandFont);
		return brandFont;
	}
}
