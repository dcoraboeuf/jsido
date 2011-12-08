package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XType;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.model.DefaultSidoType;

public class TypeDefinition {

	private final SidoSchema schema;
	private final XType xType;

	private ResolutionStatus status;
	// FIXME Use a builder for the type
	private DefaultSidoType type;

	public TypeDefinition(SidoSchema schema, XType xType) {
		this.schema = schema;
		this.xType = xType;
        this.status = ResolutionStatus.PENDING;
	}

	public ResolutionStatus getStatus() {
		return status;
	}

	public String getName() {
		return xType.getName();
	}

	public String getQualifiedName() {
		return String.format("%s::%s", schema.getUid(), getName());
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

}
