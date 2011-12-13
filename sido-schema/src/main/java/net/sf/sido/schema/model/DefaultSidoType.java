package net.sf.sido.schema.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoPropertyNotFoundException;

public class DefaultSidoType extends AbstractSidoItem implements SidoType {

	private final SidoSchema schema;
	private final String name;
	
	private final Map<String, ? extends SidoProperty<?>> properties = new HashMap<String, SidoProperty<?>>();
	
	private SidoType parentType;
	private boolean abstractType;

	public DefaultSidoType(SidoSchema schema, String name) {
		this.schema = schema;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SidoSchema getSchema() {
		return schema;
	}
	
	@Override
	public String getQualifiedName() {
		return String.format("%s%s%s", schema.getUid(), SidoContext.SCHEMA_SEPARATOR, name);
	}
	
	@Override
	public SidoType getParentType() {
		return parentType;
	}

	public void setParentType(SidoType parentType) {
		checkNotClosed();
		this.parentType = parentType;
	}
	

	@Override
	public boolean isAbstractType() {
		return abstractType;
	}

	public void setAbstractType(boolean abstractType) {
		checkNotClosed();
		this.abstractType = abstractType;
	}
	
	@Override
	public Collection<? extends SidoProperty<?>> getProperties() {
		return properties.values();
	}
	
	@Override
	public <P> SidoProperty<P> getProperty(String name, boolean required) {
		@SuppressWarnings("unchecked")
		SidoProperty<P> property = (SidoProperty<P>) properties.get(name);
		if (property == null && required) {
			throw new SidoPropertyNotFoundException(getQualifiedName(), name);
		} else {
			return property;
		}
	}

}
