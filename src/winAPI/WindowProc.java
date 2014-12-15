package winAPI;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.win32.StdCallLibrary;


public interface WindowProc extends StdCallLibrary.StdCallCallback {

	LRESULT callback(int hWnd, int uMsg, WPARAM wParam, LPARAM lParam);
}
