package winAPI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;


public class POINT extends Structure {
	
	public long x,y;

	@Override
	protected List<String> getFieldOrder() {
		List<String> fields= new ArrayList<String>();
		fields = Arrays.asList(new String[] {"x","y"});
		return fields; 
		
	}

}
