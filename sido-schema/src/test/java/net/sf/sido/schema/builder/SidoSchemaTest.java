package net.sf.sido.schema.builder;

import java.util.ArrayList;
import java.util.List;

import net.sf.sido.schema.SidoPrefix;
import net.sf.sido.schema.SidoSchema;

import org.junit.Test;

public class SidoSchemaTest {

	@Test(expected = UnsupportedOperationException.class)
	public void immutable_prefixes() {
		List<SidoPrefix> prefixes = new ArrayList<SidoPrefix>();
		prefixes.add(new SidoPrefix("api", "sido.api"));
		SidoSchema schema = new SidoSchema("sido.test", prefixes);
		// Tries to add a prefix
		schema.getPrefixes().add(new SidoPrefix("import", "import.api"));
	}

}
