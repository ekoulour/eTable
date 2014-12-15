package winAPI;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;

/*
 * This class call contains methods for window handing.
 * The methods of this class have to be triggered by the
 * corresponding gesture.
 * 
 */

public class Window {
	
	 RECT rectChild;
	 RECT rectClient = new RECT();
	 int hWndChild;
	 int hWndParent; 
    
	/*
	 * Call win32 function to get the width of the monitor
	 */
	public int getMonitorWidth(){
		int monitorWidth = User32.instance.GetSystemMetrics(0);
		return monitorWidth;
		
	}
	
	
	/*
	 * Call win32 function to get the height of the monitor
	 */ 
	public int getMonitorHeight(){
		int monitorHeight = User32.instance.GetSystemMetrics(1);
		return monitorHeight;
		
	}
	
	
	/*
	 * Foreground window is the window that the user currently uses.
	 * Pointer,coordinates and title are returned.
	 */
	public WindowInfo getForegroundWindow(){
		
		int foreground = User32.instance.GetForegroundWindow();
	    
	    RECT r = new RECT();
        User32.instance.GetWindowRect(foreground, r);
        return new WindowInfo(foreground, r);		
	}
	
	
	/*
	 * Move window of either the left side or right side checking
	 * first if the corresponding list is empty or not
	 */
	public void moveWindow(List<WindowInfo> windowsList,int leftCoordinate){
		
		if(windowsList.isEmpty()){
    		User32.instance.GetClientRect(hWndParent,rectClient);
         }else{
        	 WindowInfo lastWindow = windowsList.get(windowsList.size()-1);
        	 User32.instance.GetClientRect(lastWindow.hwnd,rectClient);
         }
	
	    User32.instance.MoveWindow(hWndChild,leftCoordinate, rectClient.top, rectClient.right/2, rectClient.bottom, false);
	    rectChild = new RECT(rectClient.left,rectClient.top,rectClient.right/2,rectClient.bottom);
	    windowsList.add(new WindowInfo(hWndChild, rectChild));
	}
	
	
	/*
	 * Move window in specific position on the table
	 * It has to check if there is overlap.In this case the style is changed
	 * The side that the window will move is also checked.
	 * Client area is defined as the area of the window's output
	 * 
	 */
	    
	public void moveWindowtoTable(String parentTitle, String side){
	
		List<WindowInfo> windowsLeft = new ArrayList<WindowInfo>();
	    List<WindowInfo> windowsRight = new ArrayList<WindowInfo>();
	    int leftCoordinate;
		
		WindowInfo usedWindow = getForegroundWindow();
		hWndChild = usedWindow.hwnd;
		hWndParent = User32.instance.FindWindow(parentTitle);
		
		User32.instance.SetParent(hWndChild, hWndParent);
		
	    if(side=="left"){
	    	leftCoordinate = rectClient.left;
	    	moveWindow(windowsLeft,leftCoordinate);
	    }else{
	    	leftCoordinate = rectClient.right / 2;
	    	moveWindow(windowsRight,leftCoordinate);
	    }
	}
	
	/*
	 * Move window in its previous position on the monitor
	 * 
	 */
	public void moveWindowtoDesktop(){
		
		WindowInfo usedWindow = getForegroundWindow();
		int monitorWindth = getMonitorWidth();
		
		User32.instance.MoveWindow(usedWindow.hwnd, monitorWindth/2, 0, usedWindow.rect.right, usedWindow.rect.bottom, false);
		
	}
	
	
	/*
	 * Destroy a window on table
	 */
	public void deleteWindow(){
		
		WindowInfo usedWindow = getForegroundWindow();
		User32.instance.DestroyWindow(usedWindow.hwnd);
		
		
	}
	
  }
