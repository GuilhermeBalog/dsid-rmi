package server;

import java.util.AbstractMap;
import java.util.List;
import java.io.Serializable;

public interface Part extends Serializable {
	int getCode();
	String getName();
	String getServerName();
	String getDescription();
	List<AbstractMap.SimpleEntry<Part, Integer>> getSubParts();
	void setSubParts(List<AbstractMap.SimpleEntry<Part, Integer>> subParts);
}
