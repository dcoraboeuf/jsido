package sido.maven.it.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ComplexTest {
	
	@Test
	public void address_abstract() {
		assertTrue("Address class is abstract", Modifier.isAbstract(Address.class.getModifiers()));
	}
	
	@Test
	public void address_parent_of_free_address() {
		assertEquals("Address is the superclass of FreeAddress", FreeAddress.class.getSuperclass(), Address.class);
	}
	
	@Test
	public void address_parent_of_structured_address() {
		assertEquals("Address is the superclass of StructuredAddress", StructuredAddress.class.getSuperclass(), Address.class);
	}
	
	@Test
	public void person_name() {
		Person p = new Person();
		assertEquals("", p.getName());
		p.setName("Test");
		assertEquals("Test", p.getName());
	}
	
	@Test
	public void person_age() {
		Person p = new Person();
		assertNull(p.getAge());
		p.setAge(12);
		int age = p.getAge();
		assertEquals(12, age);
	}
	
	@Test
	public void person_mainAddress() {
		Person p = new Person();
		assertNull(p.getMainAddress());
		StructuredAddress address = new StructuredAddress();
		address.setCity("City");
		address.setZipcode("10000");
		address.setContent("Test");
		p.setMainAddress(address);
		assertTrue(address == p.getMainAddress());
	}

	@Test
	public void person_phones() {
		Person p = new Person();
		assertNull(p.getPhones());
		p.addPhones("111", "222");
		List<String> phones = p.getPhones();
		assertNotNull(phones);
		assertEquals(Arrays.asList("111", "222"), phones);
		phones.remove(0);
		assertEquals(Arrays.asList("222"), p.getPhones());
	}
	
	// FIXME person.addresses
	// FIXME freeAddress.country
	// FIXME freeAddress.lines
	// FIXME structuredAddress.country
	// FIXME structuredAddress.city
	// FIXME structuredAddress.zipcode
	// FIXME structuredAddress.content
	// FIXME company.employees	

}
