package sido.maven.it.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SimpleTest {
	
	@Test
	public void simple() {
		Person person = new Person();
		assertNull(person.getName());
		person.setName("Test");
		assertEquals("Test", person.getName());
	}
	
}
