package winAPI;

import java.util.ArrayList;
import java.util.List;

/*
 * This class call contains methods for window handing.
 *  Methods of this class are triggered by the
 * corresponding gesture.
 *
 */

public class Window {

	List<WindowInfo> windowsRight = new ArrayList<WindowInfo>();
	List<WindowInfo> windowsLeft = new ArrayList<WindowInfo>();
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
    */
	/*public void moveWindow(List<WindowInfo> windowsList,int leftCoordinate,RECT rectClient){


	    User32.instance.MoveWindow(hWndChild,leftCoordinate, rectClient.top, rectClient.right/4, rectClient.bottom-100, false);
	    rectChild = new RECT(leftCoordinate,rectClient.top,rectClient.right/4,rectClient.bottom-100);
	    windowsList.add(new WindowInfo(hWndChild, rectChild));

	}
*/

	/*
	 * Move window in specific position of client area on the table.
	 * Client area is defined as the area of the window's output
	 *
	 * @param parentTitle  title of the application's window (parent)
	 */

	public void moveWindowtoTable(String parentTitle,String side){


	    int leftCoordinate;

		if(side == "LEFT"){
			WindowInfo usedWindow = getForegroundWindow();
			hWndChild = usedWindow.hwnd;

			hWndParent = User32.instance.FindWindow(null,parentTitle);
			User32.instance.GetClientRect(hWndParent,rectClient);

			User32.instance.SetParent(hWndChild, hWndParent);
			leftCoordinate = rectClient.left;

			User32.instance.MoveWindow(hWndChild,leftCoordinate, rectClient.top, rectClient.right/4, rectClient.bottom-100, true);
		    rectChild = new RECT(leftCoordinate,rectClient.top,rectClient.right/4,rectClient.bottom-100);
		    windowsLeft.add(new WindowInfo(hWndChild, rectChild));


		}else{
			System.out.println("MOVE TO RIGHT");

			WindowInfo usedWindow = null;

			if( windowsLeft.size() != 0 ){

				usedWindow = windowsLeft.get(0);
				windowsLeft.clear();
			}else{
				System.out.println("No window");
			};



			hWndChild = usedWindow.hwnd;
			leftCoordinate = rectClient.right - 320;

			User32.instance.MoveWindow(hWndChild,leftCoordinate, usedWindow.rect.top, usedWindow.rect.right, usedWindow.rect.bottom, true);
		    rectChild = new RECT(leftCoordinate,rectClient.top,rectClient.right/4,rectClient.bottom-100);
		    windowsRight.add(new WindowInfo(hWndChild, rectChild));
		    System.out.println(windowsRight.get(0));
		}

	}

	/*
	 * Move window in its previous position on the monitor
	 *
	 */
	public void moveWindowtoDesktop(String side){

		WindowInfo window = null;


		if(side == "LEFT"){

			if( windowsLeft.size() != 0 ){

				window = windowsLeft.get(0);
				windowsLeft.clear();
			}else{
				System.out.println("No window");
			};

		}else if(side == "RIGHT"){

			if( windowsRight.size() != 0 ){

				window = windowsRight.get(0);
				windowsRight.clear();
			}else{
				System.out.println("No window");
			};

		}

		int desktop = User32.instance.GetDesktopWindow();
		int monitorWindth = getMonitorWidth();
		int monitorHeight = getMonitorHeight();

		User32.instance.SetParent(window.hwnd, desktop);
		User32.instance.MoveWindow(window.hwnd, 0, 0, monitorWindth/2, monitorHeight, true);


	}


	/*
	 * Destroy a window on table

	public void deleteWindow(){
		if (windowsLeft.size() == 0) {
			System.out.println("No windows");
			return;
		}
		WindowInfo window = windowsLeft.get(0);
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
*/
	/*
	 * Scroll down event are handling sending the corresponding
	 * msg to the window

    public void scrollWindowDown(){

    	WindowInfo window = windowsLeft.get(0);

    	User32.instance.ScrollWindowEx(window.hwnd, 0, 5, null, null, null, null,0);




    }


	 * Scroll up event are handling sending the corresponding
	 * msg to the window

    public void scrollWindowUp(){

    	WindowInfo window = windowsLeft.get(0);

    	User32.instance.ScrollWindowEx(window.hwnd, 0, -5, null, null, null, null,0);




    }*/



  }
