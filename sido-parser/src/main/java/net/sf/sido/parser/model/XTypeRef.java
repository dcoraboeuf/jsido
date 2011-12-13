package net.sf.sido.parser.model;

import net.sf.sido.schema.SidoContext;

import org.apache.commons.lang3.StringUtils;

public class XTypeRef {

	private final String token;

	public XTypeRef(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getQualifiedName(String schemaUID) {
		if (StringUtils.contains(token, SidoContext.SCHEMA_SEPARATOR)) {
			return token;
		} else {
			return String.format("%s%s%s", schemaUID, SidoContext.SCHEMA_SEPARATOR, token);
		}
	}

}
