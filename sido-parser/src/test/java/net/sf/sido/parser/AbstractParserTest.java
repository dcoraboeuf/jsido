package net.sf.sido.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import net.sf.jstring.Strings;
import net.sf.sido.schema.Sido;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public abstract class AbstractParserTest {

	protected static Strings strings;

	@BeforeClass
	public static void load() {
		strings = new Strings("net.sf.sido.parser.Strings",
				"net.sf.sido.schema.Strings");
	}

	protected SidoContext context;

	@Before
	public void context() {
		context = createContext();
		Sido.setContext(context);
	}

	protected abstract SidoContext createContext();

	protected void assertAnonymousProperty(SidoType type, String name,
			boolean nullable, boolean collection,
			String collectionIndex) {
		SidoAnonymousProperty property = assertProperty(type, name, nullable,
				collection, collectionIndex);
		assertNotNull(property);
	}

	protected void assertProperty(SidoType type, String name,
			SidoType propertyType, boolean nullable, boolean collection,
			String collectionIndex) {
		SidoRefProperty property = assertProperty(type, name, nullable,
				collection, collectionIndex);
		assertTrue(propertyType == property.getType());
	}

	protected <T> void assertProperty(SidoType type, String name,
			Class<T> propertyType, boolean nullable, boolean collection,
			String collectionIndex) {
		SidoSimpleProperty<T> property = assertProperty(type, name, nullable,
				collection, collectionIndex);
		assertEquals(propertyType, property.getType().getType());
	}

	protected <P extends SidoProperty> P assertProperty(SidoType type,
			String name, boolean nullable, boolean collection,
			String collectionIndex) {
		P property = type.getProperty(name, true);
		assertEquals(name, property.getName());
		assertEquals(nullable, property.isNullable());
		assertEquals(collection, property.isCollection());
		assertEquals(collectionIndex, property.getCollectionIndex());
		return property;
	}

	protected SidoType assertType(String uri, String name, boolean abstractType,
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

	protected SidoSchema parseOne(String fileName) {
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

	protected Collection<SidoSchema> parse(String... fileNames) {
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
