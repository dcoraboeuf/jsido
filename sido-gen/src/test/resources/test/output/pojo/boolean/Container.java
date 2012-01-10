package sido.test;

import java.util.ArrayList;
import java.util.List;

public class Container {

	private Boolean optionalFlag;
	private boolean flag = false;
	private List<Boolean> flags = new ArrayList<Boolean>();

	public Boolean getOptionalFlag () {
		return optionalFlag;
	}

	public void setOptionalFlag (Boolean pValue) {
		optionalFlag = pValue;
	}

	public boolean isFlag () {
		return flag;
	}

	public void setFlag (boolean pValue) {
		flag = pValue;
	}

	public List<Boolean> getFlags () {
		return flags;
	}

	public void addFlags (Boolean... pValues) {
		for (Boolean pValue : pValues) {
			flags.add(pValue);
		}
	}

	public void setFlags (List<Boolean> pValues) {
		flags = pValues;
	}
	
}
