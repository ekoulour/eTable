package winAPI;


import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

public interface User32 extends StdCallLibrary{
	
	static Map UNICODE_OPTIONS = new HashMap() {
        {
            put("type-mapper", W32APITypeMapper.UNICODE);
            put("function-mapper", W32APIFunctionMapper.UNICODE);
        }
    };
    
    final User32 instance = (User32) Native.loadLibrary(User32.class, UNICODE_OPTIONS);
    
    final int GW_HWNDNEXT = 2;
    final int GW_HWNDPREV = 3;
    
    
    boolean EnumWindows (WndEnumProc wndenumproc, int lParam);
    boolean IsWindowVisible(int hWnd);
    int GetWindowRect(int hWnd, RECT r);
    void GetWindowTextA(int hWnd, byte[] buffer, int buflen);
    int GetTopWindow(int hWnd);
    int GetWindow(int hWnd, int flag);
    boolean GetWindowPlacement(int hWnd,WINDOWPLACEMENT wp);
    int GetSystemMetrics(int index);
    boolean MoveWindow(int hWnd,int x,int y,int width,int height,boolean repaint);
    int WindowFromPoint(POINT point);
    long SetWindowLong(int hWnd,int style,long val);
    int GetForegroundWindow();
    int FindWindow(String title);
    int SetParent(int hWndChild,int hWndParent);
    boolean GetClientRect(int hWnd,RECT r);
    boolean DestroyWindow(int hWnd);
   
}