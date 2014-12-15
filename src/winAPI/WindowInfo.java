package winAPI;

public class WindowInfo {
	 int hwnd;
     RECT rect;
     //String title;
     public WindowInfo(int hwnd, RECT rect){ 
    	 this.hwnd = hwnd; 
    	 this.rect = rect; 
     }

     public String toString() {
         return String.format("(%d,%d)-(%d,%d) : \"%s\"",
             rect.left,rect.top,rect.right,rect.bottom);
     }
  }