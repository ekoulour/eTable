package winAPI;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;

/*
 * This class call contains methods for window handing.
 *  Methods of this class are triggered by the
 * corresponding gesture.
 *
 */

public class Window {

	//List<WindowInfo> windowsRight = new ArrayList<WindowInfo>();
	List<WindowInfo> windowsList = new ArrayList<WindowInfo>();
    RECT rectChild;
	RECT rectClient = new RECT();
	int hWndChild;
	int hWndParent;

	/*
	 * Call win32 function to get the width of the monitor
	 * @return monitorWidth
	 */
	public int getMonitorWidth(){
		int monitorWidth = User32.instance.GetSystemMetrics(0);
		return monitorWidth;

	}


	/*
	 * Call win32 function to get the height of the monitor
	 * @return monitorHeight
	 */
	public int getMonitorHeight(){
		int monitorHeight = User32.instance.GetSystemMetrics(1);
		return monitorHeight;

	}


	/*
	 * Foreground window is the window that the user currently uses.
	 * @return WindowInfo 	contains window handler and position (rectangle)
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

	public void moveWindow(List<WindowInfo> windowsList,int leftCoordinate){

		if(windowsList.isEmpty()){
    		User32.instance.GetClientRect(hWndParent,rectClient);
         }else{
        	 WindowInfo lastWindow = windowsList.get(windowsList.size()-1);
        	 User32.instance.GetClientRect(lastWindow.hwnd,rectClient);
         }

	    User32.instance.MoveWindow(hWndChild,leftCoordinate, rectClient.top, rectClient.right/4, rectClient.bottom, false);
	    rectChild = new RECT(rectClient.left,rectClient.top,rectClient.right/4,rectClient.bottom);
	    windowsList.add(new WindowInfo(hWndChild, rectChild));

	}*/


	/*
	 * Move window in specific position of client area on the table.
	 * Client area is defined as the area of the window's output
	 *
	 * @param parentTitle  title of the application's window (parent)
	 */

	public void moveWindowtoTable(String parentTitle){


	    //int leftCoordinate;

		WindowInfo usedWindow = getForegroundWindow();
		hWndChild = usedWindow.hwnd;
		hWndParent = User32.instance.FindWindow(null,parentTitle);

		System.out.println(hWndChild);

		User32.instance.SetParent(hWndChild, hWndParent);
		User32.instance.GetClientRect(hWndParent,rectClient);
		User32.instance.MoveWindow(hWndChild,rectClient.left, rectClient.top, rectClient.right / 4, rectClient.bottom - 20, false);
	    rectChild = new RECT(rectClient.left,rectClient.top,rectClient.right / 4,rectClient.bottom - 20);
	    windowsList.add(new WindowInfo(hWndChild, rectChild));

	   /* if(side=="LEFT"){
	    	leftCoordinate = rectClient.left;
	    	moveWindow(windowsList,leftCoordinate);
	    }else{
	    	leftCoordinate = rectClient.right / 2;
	    	moveWindow(windowsRight,leftCoordinate);
	    }*/
	}

	/*
	 * Move window in its previous position on the monitor
	 *
	 */
	public void moveWindowtoDesktop(){

		//WindowInfo usedWindow = getForegroundWindow();
		WindowInfo window = windowsList.get(0);
		int desktop = User32.instance.GetDesktopWindow();
		int monitorWindth = getMonitorWidth();
		int monitorHeight = getMonitorHeight();

		User32.instance.SetParent(window.hwnd, desktop);
		//User32.instance.MoveWindow(window.hwnd, 0, 0, usedWindow.rect.right/2, usedWindow.rect.bottom, false);
		User32.instance.MoveWindow(window.hwnd, 0, 0, monitorWindth/2, monitorHeight, false);
	}


	/*
	 * Destroy a window on table
	 */
	public void deleteWindow(){

		WindowInfo window = windowsList.get(0);
		System.out.println(window.hwnd);
		//User32.instance.DestroyWindow(usedWindow.hwnd);

		 new WindowProc(){
			public LRESULT callback(int hWnd, int uMsg, WPARAM wParam, LPARAM lParam){

				if (hWnd == window.hwnd){
					switch(uMsg)
				    {
				        case WinUser.WM_CLOSE:
				        	User32.instance.DestroyWindow(hWnd);
				        break;
				        default:
				            return User32.instance.DefWindowProc(hWnd, uMsg, wParam, lParam);
				    }

				}
				 return User32.instance.DefWindowProc(hWnd, uMsg, wParam, lParam);
			}

		 };

	}

  }
