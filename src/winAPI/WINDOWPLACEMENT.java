package winAPI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;


public class WINDOWPLACEMENT extends Structure {

	public int length,flags,showCmd,ptMinPosition,ptMaxPosition;
	public RECT rc;
	
	@Override
	protected List<String> getFieldOrder() {
		List<String> fields= new ArrayList<String>();
		fields = Arrays.asList(new String[] {"length","flags","showCmd","ptMinPosition","ptMaxPosition","rc",});
		//System.out.println(field);
		return fields; 
	}

}
