package net.sf.sido.schema.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.sido.schema.SidoPrefix;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class SchemaBuilder {

	public static SchemaBuilder create(String uri) {
		return new SchemaBuilder(uri);
	}

	private final String uri;
	private final Map<String, String> prefixes = new HashMap<String, String>();
	private final Map<String, TypeBuilder> typeBuilders = new HashMap<String, TypeBuilder>();

	protected SchemaBuilder(String uri) {
		this.uri = uri;
	}

	public SchemaBuilder addPrefix(String prefix, String uri) {
		prefixes.put(prefix, uri);
		return this;
	}

	protected List<SidoPrefix> buildPrefixes() {
		List<SidoPrefix> result = new ArrayList<SidoPrefix>();
		for (Map.Entry<String, String> entry : prefixes.entrySet()) {
			String prefix = entry.getKey();
			String uri = entry.getValue();
			SidoPrefix sidoPrefix = new SidoPrefix(prefix, uri);
			result.add(sidoPrefix);
		}
		return result;
	}

	public SidoSchema build() {
		return new SidoSchema(uri, buildPrefixes(), buildTypes());
	}

	private Collection<SidoType> buildTypes() {
		return Collections2.transform(typeBuilders.values(), new Function<TypeBuilder, SidoType>() {
			@Override
			public SidoType apply(TypeBuilder o) {
				return o.build();
			}
		});
	}

	public TypeBuilder addType(String name) {
		TypeBuilder typeBuilder = TypeBuilder.create(name);
		typeBuilders.put(name, typeBuilder);
		return typeBuilder;
	}

}
