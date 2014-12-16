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
	RECT rectClient = new RECT();
    RECT rectChild;
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
		User32.instance.MoveWindow(hWndChild,rectClient.left, rectClient.top, rectClient.right / 4, rectClient.bottom - 100, false);
	    rectChild = new RECT(rectClient.left,rectClient.top,rectClient.right / 4,rectClient.bottom - 100);
	    windowsList.add(new WindowInfo(hWndChild, rectChild));
	}

	/*
	 * Move window in its previous position on the monitor
	 *
	 */
	public void moveWindowtoDesktop(){


		WindowInfo window = windowsList.get(0);
		int desktop = User32.instance.GetDesktopWindow();
		int monitorWindth = getMonitorWidth();
		int monitorHeight = getMonitorHeight();

		User32.instance.SetParent(window.hwnd, desktop);
		User32.instance.MoveWindow(window.hwnd, 0, 0, monitorWindth/2, monitorHeight, false);

		windowsList.clear();
	}


	/*
	 * Destroy a window on table
	 */
	public void deleteWindow(){

		WindowInfo window = windowsList.get(0);
		//System.out.println(window.hwnd);
		User32.instance.DestroyWindow(window.hwnd);

		 new WindowProc(){
			public LRESULT callback(int hWnd, int uMsg, WPARAM wParam, LPARAM lParam){

				System.out.println();
				System.out.println(uMsg+" "+hWnd);


				switch(uMsg)
			    {
			        case WinUser.WM_CLOSE:
			        	User32.instance.DestroyWindow(hWnd);
			        break;
			    }

				 return User32.instance.DefWindowProc(hWnd, uMsg, wParam, lParam);
			}

		 };

	}

	/*
	 * Scroll down event are handling sending the corresponding
	 * msg to the window
	 */
    public void scrollWindowDown(){

    	WindowInfo window = windowsList.get(0);

    	User32.instance.ScrollWindowEx(window.hwnd, 0, 5, null, null, null, null,0);




    }

    /*
	 * Scroll up event are handling sending the corresponding
	 * msg to the window
	 */
    public void scrollWindowUp(){

    	WindowInfo window = windowsList.get(0);

    	User32.instance.ScrollWindowEx(window.hwnd, 0, -5, null, null, null, null,0);




    }



  }
