package winAPI;

import com.sun.jna.Native;

/*
 * This class call contains methods 
 * for window handing 
 */

public class Window {

	
	public int getMonitorWidth(){
		int monitorWidth = User32.instance.GetSystemMetrics(0);
		return monitorWidth;
		
	}
	 
	public int getMonitorHeight(){
		int monitorHeight = User32.instance.GetSystemMetrics(1);
		return monitorHeight;
		
	}
		
	public WindowInfo getForegroundWindow(){
		
		int foreground = User32.instance.GetForegroundWindow();
	    
	    RECT r = new RECT();
        User32.instance.GetWindowRect(foreground, r);
        
        
        byte[] buffer = new byte[1024];
        User32.instance.GetWindowTextA(foreground, buffer, buffer.length);
        String title = Native.toString(buffer);
        
        return new WindowInfo(foreground, r, title);
		
	}
	    
	public void moveWindow(String title){
		
		WindowInfo window = getForegroundWindow();
		int hWndChild = window.hwnd;
		int hWndParent = User32.instance.FindWindow(title);
		
		try {
			User32.instance.SetParent(hWndChild, hWndParent);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Success!!!");
		
		//moveWindow in specific position in the parent and ajust its size
		
				
		
	}
	
  }
