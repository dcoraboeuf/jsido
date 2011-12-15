package net.sf.sido.schema.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoCircularInheritanceException;
import net.sf.sido.schema.support.SidoPropertyNotFoundException;

public class DefaultSidoType extends AbstractSidoItem implements SidoType {

	private final SidoSchema schema;
	private final String name;
	
	private final Map<String, ? super SidoProperty> properties = new HashMap<String, SidoProperty>();
	
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
		// Checks for double update
		if (this.parentType != null) {
			throw new IllegalStateException("Parent type has already been set");
		}
		// Checks for circularity
		if (parentType != null) {
			SidoType type = parentType;
			while (type != null) {
				if (type == this) {
					throw new SidoCircularInheritanceException(getQualifiedName());
				} else {
					type = type.getParentType();
				}
			}
		}
		// OK
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
	public Collection<? super SidoProperty> getProperties() {
		return properties.values();
	}
	
	@Override
	public <P extends SidoProperty> P getProperty(String name, boolean required) {
		@SuppressWarnings("unchecked")
		P property = (P) properties.get(name);
		if (property == null && required) {
			throw new SidoPropertyNotFoundException(getQualifiedName(), name);
		} else {
			return property;
		}
	}

	public void addProperty(SidoProperty property) {
		checkNotClosed();
		properties.put(property.getName(), property);
	}

}
