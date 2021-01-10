
@SuppressWarnings("unchecked")
public class RedBlackNode
{
	private RedBlackNode left, right;
	private Comparable val;
	private ColorNode color;
	
	public RedBlackNode(Comparable v) 
	{
		val = v; 
		color = ColorNode.RED;
		left = new RedBlackNode();
		right = new RedBlackNode();
	}
	public RedBlackNode() {color = ColorNode.BLACK; val = null;}
	public void setLeft(RedBlackNode l) {left = l;}
	public void setRight(RedBlackNode r) {right = r;}
	public void setValue(Comparable v) {val = v;}
	public void setColor(ColorNode c) {color = c;}
	
	public RedBlackNode getLeft() {return left;}
	public RedBlackNode getRight() {return right;}
	public Comparable getValue() {return val;}
	public ColorNode getColor() {return color;}
	public String getStringColor()
	{
		if(color == ColorNode.RED)
			return "Red";	
		else
			return "Black";
	}
	public String toString() {return "Value:" + val + ", Left:" + ((left == null) ? "null":left.getValue()) + ", Right:" + ((right == null) ? "null":right.getValue()) + ", Color:" + getStringColor();}
}

enum ColorNode
{
	RED, BLACK, DOUBLE_BLACK
}
