package net.sf.sido.schema.datetime;

import org.joda.time.DateTime;

import net.sf.sido.schema.support.AbstractSidoSimpleType;

public class DateTimeSimpleType extends AbstractSidoSimpleType<DateTime> {

	public DateTimeSimpleType() {
		super("datetime");
	}

	@Override
	public Class<DateTime> getType() {
		return DateTime.class;
	}

	@Override
	public DateTime getDefaultValue() {
		return new DateTime();
	}
	
	@Override
	public String getDefaultJavaInitialization() {
		return "new DateTime()";
	}

}
