package mod.instance;

import java.awt.*;

import javax.swing.JPanel;

import Define.AreaDefine;
import Pack.DragPack;
import bgWork.handler.CanvasPanelHandler;
import mod.IFuncComponent;
import mod.ILinePainter;
import java.lang.Math;

public class DependencyLine extends JPanel
        implements IFuncComponent, ILinePainter
{
    JPanel				from;
    int					fromSide;
    Point				fp				= new Point(0, 0);
    JPanel				to;
    int					toSide;
    Point				tp				= new Point(0, 0);
    boolean				isSelect		= false;
    int					selectBoxSize	= 5;
    CanvasPanelHandler	cph;

    public DependencyLine(CanvasPanelHandler cph)
    {
        this.setOpaque(false);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(1, 1));
        this.cph = cph;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 確保先呼叫父類別方法

        Graphics2D g2 = (Graphics2D) g;
        Point fpPrime;
        Point tpPrime;
        renewConnect();
        fpPrime = new Point(fp.x - this.getLocation().x, fp.y - this.getLocation().y);
        tpPrime = new Point(tp.x - this.getLocation().x, tp.y - this.getLocation().y);

        // 設定虛線樣式
        float[] dashPattern = {10, 5};
        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
        g2.setColor(Color.BLACK);
        g2.drawLine(fpPrime.x, fpPrime.y, tpPrime.x, tpPrime.y);

//        paintArrow(g2, fpPrime, tpPrime); // 傳入起點和終點畫箭頭
        paintArrow(g2, tpPrime);

        if (isSelect) {
            paintSelect(g2);
        }
    }


    @Override
    public void reSize()
    {
        Dimension size = new Dimension(Math.abs(fp.x - tp.x) + 10,
                Math.abs(fp.y - tp.y) + 10);
        this.setSize(size);
        this.setLocation(Math.min(fp.x, tp.x) - 5, Math.min(fp.y, tp.y) - 5);
    }

    @Override
    public void paintArrow(Graphics g, Point to) {
        Graphics2D g2 = (Graphics2D) g;

        Point from = new Point(fp.x - this.getLocation().x, fp.y - this.getLocation().y);

//        paintArrow(g2, from, to);
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double angle = Math.atan2(dy, dx);

        int len = 10; // 箭頭長度
        int width = 5; // 箭頭寬度

        int x1 = to.x - (int) (len * Math.cos(angle - Math.PI / 6));
        int y1 = to.y - (int) (len * Math.sin(angle - Math.PI / 6));
        int x2 = to.x - (int) (len * Math.cos(angle + Math.PI / 6));
        int y2 = to.y - (int) (len * Math.sin(angle + Math.PI / 6));

        int[] xPoints = {to.x, x1, x2};
        int[] yPoints = {to.y, y1, y2};

        g2.fillPolygon(xPoints, yPoints, 3);
    }

//    public void paintArrow(Graphics2D g2, Point from, Point to) {
//        double dx = to.x - from.x;
//        double dy = to.y - from.y;
//        double angle = Math.atan2(dy, dx);
//
//        int len = 10; // 箭頭長度
//        int width = 5; // 箭頭寬度
//
//        int x1 = to.x - (int) (len * Math.cos(angle - Math.PI / 6));
//        int y1 = to.y - (int) (len * Math.sin(angle - Math.PI / 6));
//        int x2 = to.x - (int) (len * Math.cos(angle + Math.PI / 6));
//        int y2 = to.y - (int) (len * Math.sin(angle + Math.PI / 6));
//
//        int[] xPoints = {to.x, x1, x2};
//        int[] yPoints = {to.y, y1, y2};
//
//        g2.fillPolygon(xPoints, yPoints, 3);
//    }




    @Override
    public void setConnect(DragPack dPack)
    {
        Point mfp = dPack.getFrom();
        Point mtp = dPack.getTo();
        from = (JPanel) dPack.getFromObj();
        to = (JPanel) dPack.getToObj();
        fromSide = new AreaDefine().getArea(from.getLocation(), from.getSize(),
                mfp);
        toSide = new AreaDefine().getArea(to.getLocation(), to.getSize(), mtp);
        renewConnect();
        System.out.println("from side " + fromSide);
        System.out.println("to side " + toSide);
    }

    void renewConnect()
    {
        try
        {
            fp = getConnectPoint(from, fromSide);
            tp = getConnectPoint(to, toSide);
            this.reSize();
        }
        catch (NullPointerException e)
        {
            this.setVisible(false);
            cph.removeComponent(this);
        }
    }

    Point getConnectPoint(JPanel jp, int side)
    {
        Point temp = new Point(0, 0);
        Point jpLocation = cph.getAbsLocation(jp);
        if (side == new AreaDefine().TOP)
        {
            temp.x = (int) (jpLocation.x + jp.getSize().getWidth() / 2);
            temp.y = jpLocation.y;
        }
        else if (side == new AreaDefine().RIGHT)
        {
            temp.x = (int) (jpLocation.x + jp.getSize().getWidth());
            temp.y = (int) (jpLocation.y + jp.getSize().getHeight() / 2);
        }
        else if (side == new AreaDefine().LEFT)
        {
            temp.x = jpLocation.x;
            temp.y = (int) (jpLocation.y + jp.getSize().getHeight() / 2);
        }
        else if (side == new AreaDefine().BOTTOM)
        {
            temp.x = (int) (jpLocation.x + jp.getSize().getWidth() / 2);
            temp.y = (int) (jpLocation.y + jp.getSize().getHeight());
        }
        else
        {
            temp = null;
            System.err.println("getConnectPoint fail:" + side);
        }
        return temp;
    }

    @Override
    public void paintSelect(Graphics gra)
    {
        gra.setColor(Color.BLACK);
        gra.fillRect(fp.x, fp.y, selectBoxSize, selectBoxSize);
        gra.fillRect(tp.x, tp.y, selectBoxSize, selectBoxSize);
    }

    public boolean isSelect()
    {
        return isSelect;
    }

    public void setSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }
}
