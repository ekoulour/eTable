package winAPI;


import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HRGN;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
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


    int GetWindowRect(int hWnd, RECT r);
    int GetWindow(int hWnd, int flag);
    int GetSystemMetrics(int index);
    boolean MoveWindow(int hWnd,int x,int y,int width,int height,boolean repaint);
    int GetForegroundWindow();
    int FindWindow(String className,String title);
    int SetParent(int hWndChild,int hWndParent);
    boolean GetClientRect(int hWnd,RECT r);
    boolean DestroyWindow(int hWnd);
    int GetDesktopWindow();
    LRESULT DefWindowProc(int hWnd, int uMsg, WPARAM wParam, LPARAM lParam);
    int ScrollWindowEx(int hWnd,int dx,int dy,RECT scrolledArea,RECT clipRect,HRGN region,Object boundaries,int flag);

}