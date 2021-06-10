package server;

import java.util.List;
import javafx.util.Pair;
import java.io.Serializable;

public interface Part implements Serializable{
	int getCode();
	String getName();
	String getDescription();
	List<Pair<Part, Integer>> getSubParts();
}
