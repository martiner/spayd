package cz.geek.spayd;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static cz.geek.spayd.SimpleValue.simpleValue;
import static org.junit.Assert.assertEquals;

public class SimpleValueTest {

	@Test
	public void testSpecialCharacterEscaping() throws UnsupportedEncodingException {
		String original = " abc  123\u2665\u2620**123  abc-+ěščřžýáíé---%20" ;
		assertEquals("abc  123%E2%99%A5%E2%98%A0%2A%2A123  abc-%2B%C4%9B%C5%A1%C4%8D%C5%99%C5%BE%C3%BD%C3%A1%C3%AD%C3%A9---%2520",
				simpleValue(original).asString());
	}

}
