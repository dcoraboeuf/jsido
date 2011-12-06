package net.sf.sido.schema.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.sido.schema.SidoPrefix;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;

import org.junit.Test;

public class SidoSchemaTest {

	@Test(expected = UnsupportedOperationException.class)
	public void immutable_prefixes() {
		List<SidoPrefix> prefixes = new ArrayList<SidoPrefix>();
		prefixes.add(new SidoPrefix("api", "sido.api"));
		SidoSchema schema = new SidoSchema("sido.test", prefixes, Collections.<SidoType>emptyList());
		// Tries to add a prefix
		schema.getPrefixes().put("import", new SidoPrefix("import", "import.api"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void immutable_types() {
		List<SidoType> types = new ArrayList<SidoType>();
		types.add(new SidoType("Person"));
		SidoSchema schema = new SidoSchema("sido.test", Collections.<SidoPrefix>emptyList(), types);
		// Tries to add a type
		schema.getTypes().put("Person", new SidoType("Person"));
	}

}
