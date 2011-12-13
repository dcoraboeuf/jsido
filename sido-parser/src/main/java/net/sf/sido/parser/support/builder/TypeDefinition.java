package net.sf.sido.parser.support.builder;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.model.XType;
import net.sf.sido.parser.model.XTypeRef;
import net.sf.sido.parser.support.SidoSchemaPrefixNotDefinedException;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.builder.SidoSchemaBuilder;
import net.sf.sido.schema.builder.SidoTypeBuilder;

public class TypeDefinition {

	private final SidoSchemaBuilder schemaBuilder;
	private final XSchema xSchema;
	private final XType xType;
	private final SidoTypeBuilder typeBuilder;

	private ResolutionStatus status;

	public TypeDefinition(SidoSchemaBuilder schemaBuilder, XSchema xSchema, XType xType) {
		this.schemaBuilder = schemaBuilder;
		this.xSchema = xSchema;
		this.xType = xType;
        this.status = ResolutionStatus.PENDING;
        // Type builder
        typeBuilder = schemaBuilder.newType(xType.getName());
        // Basic information
        if (xType.isAbstractType()) {
        	typeBuilder.setAbstract();
        }
	}
	
	public SidoTypeBuilder getTypeBuilder() {
		return typeBuilder;
	}

	public ResolutionStatus getStatus() {
		return status;
	}

	public String getName() {
		return xType.getName();
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

	public String resolveQualifiedName(XTypeRef typeRef) {
		String token = typeRef.getToken();
		if (StringUtils.contains(token, SidoContext.SCHEMA_SEPARATOR)) {
			// Gets the prefix and the name
			String prefix = StringUtils.substringBefore(token, SidoContext.SCHEMA_SEPARATOR);
			String name = StringUtils.substringAfter(token, SidoContext.SCHEMA_SEPARATOR);
			// Resolves the prefix
			String uid = xSchema.resolvePrefix(prefix);
			if (StringUtils.isBlank(uid)) {
				throw new SidoSchemaPrefixNotDefinedException(prefix, name);
			}
			// OK
			return String.format("%s%s%s", uid, SidoContext.SCHEMA_SEPARATOR, name);
		} else {
			// No prefix, uses the same schema
			return String.format("%s%s%s", schemaBuilder.getUid(), SidoContext.SCHEMA_SEPARATOR, token);
		}
	}

}
