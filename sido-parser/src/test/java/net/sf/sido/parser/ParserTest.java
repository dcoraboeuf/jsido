package net.sf.sido.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import net.sf.jstring.Strings;
import net.sf.sido.schema.Sido;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.DefaultSidoContext;
import net.sf.sido.schema.support.SidoException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class ParserTest {

	private static Strings strings;

	@BeforeClass
	public static void load() {
		strings = new Strings("net.sf.sido.parser.Strings",
				"net.sf.sido.schema.Strings");
	}

	private SidoContext context;

	@Before
	public void before() {
		context = new DefaultSidoContext();
		Sido.setContext(context);
	}

	// TODO Already loaded modules
	// TODO Circularity

	@Test
	public void name_only() {
		SidoSchema schema = parseOne("name-only");
		assertNotNull("Returned schema is null", schema);
		assertEquals("sido.test", schema.getUid());
		assertTrue(schema.getTypes().isEmpty());
	}

	@Test
	public void complex() {
		try {
			SidoSchema schema = parseOne("complex");
			assertNotNull("Returned schema is null", schema);
			assertEquals("sido.test", schema.getUid());
			assertEquals(5, schema.getTypes().size());
			// Types checks
			assertTypes();
		} catch (SidoException ex) {
			fail(ex.getLocalizedMessage(strings, Locale.ENGLISH));
			ex.printStackTrace();
		}
	}

	@Test
	public void parsing_error_0() {
		try {
			parseOne("parsing-error-0");
			fail("Expected parsing error");
		} catch (SidoException ex) {
			assertEquals(
					"Error while parsing \"parsing-error-0\":  - Input \"<\" (position 32 to 33) is not valid for:\n"
							+ " - schema/prefix_list/Sequence/prefix/whitespaces/whitespace\n"
							+ " - schema/prefix_list/Sequence/prefix/\"for\"/'f'\n"
							+ ".\n",
					ex.getLocalizedMessage(strings, Locale.ENGLISH));
		}
	}

	@Test
	public void modules() {
		try {
			Collection<SidoSchema> schemas = parse("module-2", "module-1",
					"module-0");
			// Simple check
			assertNotNull("Returned schemas are null", schemas);
			assertEquals(3, schemas.size());
			// Context
			assertModules();
		} catch (SidoException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage(strings, Locale.ENGLISH));
		}
	}

	private void assertModules() {
		// Context
		Collection<SidoSchema> schemas = context.getSchemas();
		assertNotNull(schemas);
		assertEquals(3, schemas.size());
		// Schema names
		SidoSchema sMain = context.getSchema("sido.test.main", true);
		SidoSchema sAddress = context.getSchema("sido.test.address", true);
		SidoSchema sCompany = context.getSchema("sido.test.company", true);
		assertNotNull(sMain);
		assertNotNull(sAddress);
		assertNotNull(sCompany);
		// TODO Schema checks
		fail("Checks the modules");
	}

	private void assertTypes() {
		// Person
		SidoType personType = assertType("sido.test", "Person", false, null);
		// Address
		SidoType addressType = assertType("sido.test", "Address", true, null);
		// FreeAddress
		SidoType freeAddressType = assertType("sido.test", "FreeAddress",
				false, addressType);
		// StructuredAddress
		SidoType structuredAddressType = assertType("sido.test",
				"StructuredAddress", false, addressType);
		// Company
		SidoType companyType = assertType("sido.test", "Company", false, null);
		// Person properties
		assertProperty(personType, "name", String.class, false, false, null);
		assertProperty(personType, "age", Integer.class, true, false, null);
		assertProperty(personType, "phones", String.class, true, true, null);
		assertProperty(personType, "mainAddress", addressType, false, false,
				null);
		assertProperty(personType, "addresses", addressType, false, true,
				"country");
		// Address properties
		assertProperty(addressType, "country", String.class, false, false, null);
		// FreeAddress properties
		assertProperty(freeAddressType, "lines", String.class, false, true, null);
		// StructuredAddress properties
		assertProperty(structuredAddressType, "city", String.class, false, false, null);
		assertProperty(structuredAddressType, "zipcode", String.class, false, false, null);
		assertProperty(structuredAddressType, "lines", String.class, false, true, null);
		// Company properties
		assertProperty(companyType, "name", String.class, false, false, null);
		assertProperty(companyType, "employees", personType, false, true, null);
	}

	private void assertProperty(SidoType type, String name,
			SidoType propertyType, boolean nullable, boolean collection,
			String collectionIndex) {
		SidoRefProperty property = assertProperty(type, name, nullable,
				collection, collectionIndex);
		assertTrue(propertyType == property.getType());
	}

	private <T> void assertProperty(SidoType type, String name,
			Class<T> propertyType, boolean nullable, boolean collection,
			String collectionIndex) {
		SidoSimpleProperty<T> property = assertProperty(type, name, nullable,
				collection, collectionIndex);
		assertEquals(propertyType, property.getType());
	}

	private <P extends SidoProperty> P assertProperty(SidoType type,
			String name, boolean nullable, boolean collection,
			String collectionIndex) {
		P property = type.getProperty(name, true);
		assertEquals(name, property.getName());
		assertEquals(nullable, property.isNullable());
		assertEquals(collection, property.isCollection());
		assertEquals(collectionIndex, property.getCollectionIndex());
		return property;
	}

	private SidoType assertType(String uri, String name, boolean abstractType,
			SidoType parentType) {
		// Gets the type
		SidoType type = context.getType(uri, name, true);
		// Name
		assertEquals(name, type.getName());
		// Abstract
		assertEquals(abstractType, type.isAbstractType());
		// Parent
		assertTrue(type.getParentType() == parentType);
		// OK
		return type;
	}

	private SidoSchema parseOne(String fileName) {
		Collection<SidoSchema> schemas = parse(fileName);
		if (schemas != null) {
			if (schemas.isEmpty()) {
				return null;
			} else if (schemas.size() == 1) {
				return schemas.iterator().next();
			} else {
				throw new IllegalStateException(String.format(
						"The parsing returned more than 1 schema: %d",
						schemas.size()));
			}
		} else {
			return null;
		}
	}

	private Collection<SidoSchema> parse(String... fileNames) {
		Collection<NamedInput> inputs = Collections2.transform(
				Arrays.asList(fileNames), new Function<String, NamedInput>() {
					@Override
					public NamedInput apply(String fileName) {
						String path = String.format("/sidol/schema-%s.sidol",
								fileName);
						InputStream in = getClass().getResourceAsStream(path);
						if (in == null) {
							throw new IllegalStateException(
									"Cannot find resource at " + path);
						} else {
							String sidol;
							try {
								sidol = IOUtils.toString(in);
								return new NamedInput(fileName, sidol);
							} catch (IOException e) {
								throw new IllegalStateException(String.format(
										"Cannot read resource at %s", path), e);
							}
						}
					}
				});
		SidoParser parser = SidoParserFactory.createParser();
		return parser.parse(inputs);
	}

}
