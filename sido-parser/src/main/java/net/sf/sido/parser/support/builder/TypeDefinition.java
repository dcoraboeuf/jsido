package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XType;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.builder.SidoSchemaBuilder;
import net.sf.sido.schema.builder.SidoTypeBuilder;

public class TypeDefinition {

	private final SidoSchemaBuilder schemaBuilder;
	private final XType xType;
	private final SidoTypeBuilder typeBuilder;

	private ResolutionStatus status;

	public TypeDefinition(SidoSchemaBuilder schemaBuilder, XType xType) {
		this.schemaBuilder = schemaBuilder;
		this.xType = xType;
        this.status = ResolutionStatus.PENDING;
        // Type builder
        typeBuilder = schemaBuilder.newType(xType.getName());
	}

	public ResolutionStatus getStatus() {
		return status;
	}

	public String getName() {
		return xType.getName();
	}
	
	public String getSchemaUID() {
		return schemaBuilder.getUid();
	}

	public String getQualifiedName() {
		return String.format("%s%s%s", schemaBuilder.getUid(), SidoContext.SCHEMA_SEPARATOR, getName());
	}
	
	public XType getXType() {
		return xType;
	}

	public void start() {
		if (this.status == ResolutionStatus.ONGOING || this.status == ResolutionStatus.COMPLETE) {
			throw new IllegalStateException(String.format("Status %s for definition %s cannot be started.", this.status, getQualifiedName()));
		} else if (this.status == ResolutionStatus.PENDING) {
			this.status = ResolutionStatus.ONGOING;
		} else {
			throw new IllegalStateException(String.format("Status %s is unknown.", this.status));
		}
	}

	public void setParentType(SidoType parentType) {
		typeBuilder.setParentType(parentType);
	}

	public SidoType getType() {
		return typeBuilder.getType();
	}

	public void complete() {
		if (this.status == ResolutionStatus.COMPLETE || this.status == ResolutionStatus.PENDING) {
			throw new IllegalStateException(String.format("Status %s for definition %s cannot be completed.", this.status, getQualifiedName()));
		} else if (this.status == ResolutionStatus.ONGOING) {
			this.status = ResolutionStatus.COMPLETE;
			typeBuilder.close();
		} else {
			throw new IllegalStateException(String.format("Status %s is unknown.", this.status));
		}
	}

}
