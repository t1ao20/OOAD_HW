package mod.instance;

import java.awt.*;
import java.util.Vector;

import javax.swing.JPanel;

import bgWork.handler.CanvasPanelHandler;
import mod.IClassPainter;
import mod.IFuncComponent;
import mod.PortObj;

public class UseCase extends JPanel implements IFuncComponent, IClassPainter, PortObj
{
	Vector <String>		texts			= new Vector <>();
	Dimension			defSize			= new Dimension(150, 40);
	int					maxLength		= 20;
	boolean				isSelect		= false;
	int					selectBoxSize	= 5;
	CanvasPanelHandler	cph;

	public UseCase(CanvasPanelHandler cph)
	{
		texts.add("New Use Case");
		reSize();
		this.setVisible(true);
		this.setLocation(0, 0);
		this.setOpaque(true);
		this.cph = cph;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		reSize();
		for (int i = 0; i < texts.size(); i ++)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, defSize.width, defSize.height);
			g.setColor(Color.BLACK);
			g.drawOval(0, 0, defSize.width - 1, defSize.height - 1);
			if (texts.elementAt(i).length() > maxLength)
			{
				g.drawString(texts.elementAt(i).substring(0, maxLength) + "...",
						3, (int) (0 + (i + 0.8) * defSize.getHeight()));
			}
			else
			{
				g.drawString(texts.elementAt(i), (int) (defSize.width * 0.2),
						(int) (defSize.getHeight()) / 2);
			}
		}
		if (isSelect == true)
		{
			paintSelect(g);
		}
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}

	@Override
	public void reSize()
	{
		switch (texts.size())
		{
			case 0:
				this.setSize(defSize);
				break;
			default:
				this.setSize(defSize.width, defSize.height);
				break;
		}
	}

	@Override
	public void setText(String text)
	{
		texts.clear();
		texts.add(text);
		this.repaint();
	}

	@Override
	public void paintSelect(Graphics gra)
	{
		gra.setColor(Color.BLACK);
		gra.fillRect(this.getWidth() / 2 - selectBoxSize, 0, selectBoxSize * 2,
				selectBoxSize);
		gra.fillRect(this.getWidth() / 2 - selectBoxSize,
				this.getHeight() - selectBoxSize, selectBoxSize * 2,
				selectBoxSize);
		gra.fillRect(0, this.getHeight() / 2 - selectBoxSize, selectBoxSize,
				selectBoxSize * 2);
		gra.fillRect(this.getWidth() - selectBoxSize,
				this.getHeight() / 2 - selectBoxSize, selectBoxSize,
				selectBoxSize * 2);
	}

	public int getSelectBoxSize() { return selectBoxSize; }
	public Point getPortLoc(int portSide) {
		Point loc = new Point(0, 0); // port 左上角點
		switch (portSide)
		{
			case 0: // 下
				loc.x = this.getLocation().x + this.getWidth() / 2 - selectBoxSize;
				loc.y = this.getLocation().y + this.getHeight() - selectBoxSize;
				break;
			case 1: // 左
				loc.x = this.getLocation().x;
				loc.y = this.getLocation().y + this.getHeight() / 2 - selectBoxSize;
				break;
			case 2: // 右
				loc.x = this.getLocation().x + this.getWidth() - selectBoxSize;
				loc.y = this.getLocation().y + this.getHeight() / 2 - selectBoxSize;
				break;
			case 3: // 上
				loc.x = this.getLocation().x + this.getWidth() / 2 - selectBoxSize;
				loc.y = this.getLocation().y;
				break;
			default:
				break;
		}
		return loc;
	}
}
