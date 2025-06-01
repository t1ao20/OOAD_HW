package mod;

import java.awt.*;

public interface PortObj {
    Point getPortLoc(int index);      // 取得某一側 port 的位置
    int getSelectBoxSize();        // 取得選擇框大小
}
