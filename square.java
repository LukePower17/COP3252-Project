import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.*;
import java.lang.*;
import java.util.*;
class square extends JButton
{
	private Color normalColor;
	private Color activeColor;
	private Color dangerColor;
	private Color lastMoveColor;
	private boolean selected;
	private boolean hasIcon;
	
	public square(Color normal, Color active, Color danger, Color lastMove)
	{
		super();
		normalColor = normal;
		activeColor = active;
		dangerColor = danger;
		lastMoveColor = lastMove;
		selected = false;
		hasIcon = false;
		super.setBorder(null);
		super.setOpaque(true);
		super.setBackground(normalColor);
		
	}
	
	public boolean Isfilled()
	{
		return hasIcon;
	}
	
	public void addActionListener(chess obj)
	{
		super.addActionListener(obj);
	}
	
	public void setNormalColor(Color n)
	{
		this.normalColor = n;
	}
	public void setActiveColor(Color n)
	{
		this.activeColor = n;
	}
	
	public void setDangerColor(Color n)
	{
		this.dangerColor = n;
	}

	public void setLastMoveColor(Color n)
	{
		this.lastMoveColor = n;
	}

	public void normalMode()
	{
		super.setBackground(normalColor);	
	}
	public void activeMode()
	{
		super.setBackground(activeColor);
	}
	public void dangerMode()
	{
		super.setBackground(dangerColor);
	}
	
	public void lastMoveMode()
	{
		super.setBackground(lastMoveColor);
	}
	
	public void setIcon(Icon icon)
	{
		
		super.setIcon(icon);
		if(icon == null)
			hasIcon = false;
		else
			hasIcon = true;
	}
	
	public void setBackground(Color c)
	{
		super.setBackground(c);
	}
}

