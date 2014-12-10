package winAPI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class RECT extends Structure {
	
    public int left,top,right,bottom;

	@Override
	protected List<String> getFieldOrder() {
		
		List<String> field= new ArrayList<String>();
		field = Arrays.asList(new String[] {"left","top","right","bottom"});
		//System.out.println(field);
		return field;
		
	}
}