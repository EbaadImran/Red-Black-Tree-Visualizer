import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class RedBlackTree 
{
	private RedBlackNode root;
	public void add(RedBlackNode x)
	{
		root = add(root, x);
		root.setColor(ColorNode.BLACK);
	}
	private RedBlackNode add(RedBlackNode iter, RedBlackNode x)
	{
		if(iter == null || iter.getValue() == null)
			return x;
		
		if(iter.getColor() == ColorNode.BLACK && iter.getLeft().getColor() == ColorNode.RED && iter.getRight().getColor() == ColorNode.RED)
		{
			iter.setColor(ColorNode.RED);
			iter.getLeft().setColor(ColorNode.BLACK);
			iter.getRight().setColor(ColorNode.BLACK);
		}
		
		int dir = x.getValue().compareTo(iter.getValue());
		if(dir < 0)
			iter.setLeft(add(iter.getLeft(), x));
		else if(dir > 0)
			iter.setRight(add(iter.getRight(), x));
		
		if(leftRight(iter))
		{
			iter.setLeft(rotateLeft(iter.getLeft(), iter.getLeft().getRight()));
			iter = rotateRight(iter, iter.getLeft());
			iter.getRight().setColor(ColorNode.RED);
			iter.setColor(ColorNode.BLACK);
		}
		else if(rightLeft(iter))
		{
			iter.setRight(rotateRight(iter.getRight(), iter.getRight().getLeft()));
			iter = rotateLeft(iter, iter.getRight());
			iter.getLeft().setColor(ColorNode.RED);
			iter.setColor(ColorNode.BLACK);
		}
		else if(leftLeft(iter))
		{
			iter = rotateRight(iter, iter.getLeft());
			iter.getRight().setColor(ColorNode.RED);
			iter.setColor(ColorNode.BLACK);
		}
		else if(rightRight(iter))
		{
			iter = rotateLeft(iter, iter.getRight());
			iter.getLeft().setColor(ColorNode.RED);
			iter.setColor(ColorNode.BLACK);
		}
		
		return iter;
	}
	public RedBlackNode remove(Comparable x)
	{
		if(getNode(root, x) == null)
			return null;
		RedBlackNode temp = getNode(root, x);
		ColorNode color = temp.getColor();
		if(temp.getLeft().getValue() == null && temp.getRight().getValue() == null)
			color = temp.getColor();
		else if(temp.getLeft().getValue() == null)
			color = maxNode(temp.getRight()).getColor();
		else
			color = maxNode(temp.getLeft()).getColor();
		root = remove(root, x);
		root.setColor(ColorNode.BLACK);
		RedBlackNode rtn = new RedBlackNode(x);
		rtn.setColor(color);
		return rtn;
	}
	private RedBlackNode remove(RedBlackNode iter, Comparable x)
	{
		if(iter == null || iter.getValue() == null)
			return null;
		int dir = x.compareTo(iter.getValue());
		if(dir < 0)
			iter.setLeft(remove(iter.getLeft(), x));
		else if(dir > 0)
			iter.setRight(remove(iter.getRight(), x));
		else
		{
			if(iter.getLeft().getValue() == null)
			{
				if(iter.getColor() == ColorNode.BLACK && iter.getRight().getColor() == ColorNode.RED)
					iter.getRight().setColor(ColorNode.BLACK);
				else if(iter.getColor() == ColorNode.BLACK && iter.getRight().getColor() == ColorNode.BLACK)
					iter.getRight().setColor(ColorNode.DOUBLE_BLACK);
				return iter.getRight();
			}
			else if(iter.getRight().getValue() == null)
			{
				if(iter.getColor() == ColorNode.BLACK && iter.getLeft().getColor() == ColorNode.RED)
					iter.getLeft().setColor(ColorNode.BLACK);
				else if(iter.getColor() == ColorNode.BLACK && iter.getLeft().getColor() == ColorNode.BLACK)
					iter.getLeft().setColor(ColorNode.DOUBLE_BLACK);
				return iter.getLeft();
			}
			iter.setValue(maxValue(iter.getLeft()));
			iter.setLeft(remove(iter.getLeft(), iter.getValue()));
		}
		//Check Double Blacks
		if(iter.getLeft().getColor() == ColorNode.DOUBLE_BLACK)
		{
			iter.getLeft().setColor(ColorNode.BLACK);
			if(iter.getRight().getRight().getColor() == ColorNode.RED)
			{
				iter = rotateLeft(iter, iter.getRight());
				iter.getRight().setColor(iter.getColor());
				iter.setColor(iter.getLeft().getColor());
				iter.getLeft().setColor(ColorNode.BLACK);
			}
			else if(iter.getRight().getLeft().getColor() == ColorNode.RED)
			{
				iter.setRight(rotateRight(iter.getRight(), iter.getRight().getLeft()));
				iter.getRight().setColor(ColorNode.BLACK);
				iter = rotateLeft(iter, iter.getRight());
				iter.getRight().setColor(iter.getColor());
				iter.setColor(iter.getLeft().getColor());
				iter.getLeft().setColor(ColorNode.BLACK);
			}
			else if(iter.getRight().getColor() == ColorNode.BLACK)
			{
				iter.getLeft().setColor(ColorNode.BLACK);
				iter.getRight().setColor(ColorNode.RED);
				if(iter.getColor() == ColorNode.RED)
					iter.setColor(ColorNode.BLACK);
				else
					iter.setColor(ColorNode.DOUBLE_BLACK);
			}
			else
			{
				iter = rotateLeft(iter, iter.getRight());
				iter.setColor(ColorNode.BLACK);
				iter.getLeft().setColor(ColorNode.BLACK);
				iter.getLeft().getLeft().setColor(ColorNode.BLACK);
				iter.getLeft().getRight().setColor(ColorNode.RED);
			}
		}
		else if(iter.getRight().getColor() == ColorNode.DOUBLE_BLACK)
		{
			iter.getRight().setColor(ColorNode.BLACK);
			if(iter.getLeft().getLeft().getColor() == ColorNode.RED)
			{
				iter = rotateRight(iter, iter.getLeft());
				iter.getLeft().setColor(iter.getColor());
				iter.setColor(iter.getRight().getColor());
				iter.getRight().setColor(ColorNode.BLACK);
			}
			else if(iter.getLeft().getRight().getColor() == ColorNode.RED)
			{
				iter.setLeft(rotateLeft(iter.getLeft(), iter.getLeft().getRight()));
				iter.getLeft().setColor(ColorNode.BLACK);
				iter = rotateRight(iter, iter.getLeft());
				iter.getLeft().setColor(iter.getColor());
				iter.setColor(iter.getRight().getColor());
				iter.getRight().setColor(ColorNode.BLACK);
			}
			else if(iter.getLeft().getColor() == ColorNode.BLACK)
			{
				iter.getRight().setColor(ColorNode.BLACK);
				iter.getLeft().setColor(ColorNode.RED);
				if(iter.getColor() == ColorNode.RED)
					iter.setColor(ColorNode.BLACK);
				else
					iter.setColor(ColorNode.DOUBLE_BLACK);
			}
			else
			{
				iter = rotateRight(iter, iter.getLeft());
				iter.setColor(ColorNode.BLACK);
				iter.getRight().setColor(ColorNode.BLACK);
				iter.getRight().getRight().setColor(ColorNode.BLACK);
				iter.getRight().getLeft().setColor(ColorNode.RED);
			}
		}
		
		return iter;
	}
	
	public RedBlackNode rotateLeft(RedBlackNode p, RedBlackNode c)
	{
		RedBlackNode temp = c.getLeft();
		c.setLeft(p);
		p.setRight(temp);
		return c;
	}
	public RedBlackNode rotateRight(RedBlackNode p, RedBlackNode c)
	{
		RedBlackNode temp = c.getRight();
		c.setRight(p);
		p.setLeft(temp);
		return c;
	}
	public boolean leftRight(RedBlackNode x)
	{
		if(x.getLeft().getValue() == null || x.getLeft().getRight().getValue() == null)
			return false;
		if(x.getColor() == ColorNode.BLACK && x.getLeft().getColor() == ColorNode.RED && x.getLeft().getRight().getColor() == ColorNode.RED)
			return true;
		return false;
	}
	public boolean rightLeft(RedBlackNode x)
	{
		if(x.getRight().getValue() == null || x.getRight().getLeft().getValue() == null)
			return false;
		if(x.getColor() == ColorNode.BLACK && x.getRight().getColor() == ColorNode.RED && x.getRight().getLeft().getColor() == ColorNode.RED)
			return true;
		return false;
	}
	public boolean leftLeft(RedBlackNode x)
	{
		if(x.getLeft().getValue() == null || x.getLeft().getLeft().getValue() == null)
			return false;
		if(x.getColor() == ColorNode.BLACK && x.getLeft().getColor() == ColorNode.RED && x.getLeft().getLeft().getColor() == ColorNode.RED)
			return true;
		return false;
	}
	public boolean rightRight(RedBlackNode x)
	{
		if(x.getRight().getValue() == null || x.getRight().getRight().getValue() == null)
			return false;
		if(x.getColor() == ColorNode.BLACK && x.getRight().getColor() == ColorNode.RED && x.getRight().getRight().getColor() == ColorNode.RED)
			return true;
		return false;
	}
	public Comparable minValue(RedBlackNode tree)
	{
		if(tree.getLeft().getValue() == null)
			return tree.getValue();
		return minValue(tree.getLeft());
	}
	public Comparable maxValue(RedBlackNode tree)
	{
		if(tree.getRight().getValue() == null)
			return tree.getValue();
		return maxValue(tree.getRight());
	}
	public RedBlackNode minNode(RedBlackNode tree)
	{
		if(tree.getLeft().getValue() == null)
			return tree;
		return minNode(tree.getLeft());
	}
	public RedBlackNode maxNode(RedBlackNode tree)
	{
		if(tree.getRight().getValue() == null)
			return tree;
		return maxNode(tree.getRight());
	}
	public int getNumLevels()
	{
		return getNumLevels(root);
	}
	private int getNumLevels(RedBlackNode tree)
	{
		if(tree.getValue() == null)
			return 0;
		return 1 + Math.max(getNumLevels(tree.getLeft()), getNumLevels(tree.getRight()));
	}
	public void printFullTree(String x, int lvl)
	{
		String[] levels = x.split(",");
		for(int i = 0; i < lvl; i++)
		{
			String[] nodes = levels[i].split(" ");
			for(String node : nodes)
				System.out.print(node + "|");
			System.out.println();
		}
	}
	public String fullLevelOrder()
	{
		String rtn = "";
		int lvl = getNumLevels(root);
		for(int i = 0; i < lvl; i++)
		{
			rtn += fullLevelOrder(root, i);
			rtn += ",";
		}
		return rtn;
	}
	public String fullLevelOrder(int lvl)
	{
		String rtn = "";
		for(int i = 0; i < lvl; i++)
		{
			rtn += fullLevelOrder(root, i);
			rtn += ",";
		}
		return rtn;
	}
	private String fullLevelOrder(RedBlackNode tree, int lvl)
	{
		if(tree == null && lvl > 0)
			return fullLevelOrder(null, lvl-1) + fullLevelOrder(null, lvl-1);
		else if(tree == null && lvl == 0)
			return "null ";
		if(lvl == 0)
			return "" + tree.getValue() + " ";
		return fullLevelOrder(tree.getLeft(), lvl-1) + fullLevelOrder(tree.getRight(), lvl-1);
	}
	public ArrayList<RedBlackNode> postOrder(ArrayList<RedBlackNode> comp, RedBlackNode tree)
	{
		if(tree == null || tree.getValue() == null)
			return comp;
		comp = postOrder(comp, tree.getLeft());
		comp = postOrder(comp, tree.getRight());
		comp.add(tree);
		return comp;
	}
	public ArrayList<RedBlackNode> preOrder(ArrayList<RedBlackNode> comp,  RedBlackNode tree)
	{
		if(tree == null || tree.getValue() == null)
			return comp;
		comp.add(tree);
		comp = preOrder(comp, tree.getLeft());
		comp = preOrder(comp, tree.getRight());
		return comp;
	}
	public Queue<Comparable> levelOrder()
	{
		Queue<RedBlackNode> q = new LinkedList<>();
		Queue<Comparable> rtn = new LinkedList<>();
		q.add(root);
		while(!q.isEmpty())
		{
			RedBlackNode temp = q.poll();
			rtn.add(temp.getValue());
			if(temp.getLeft().getValue() != null)
				q.add(temp.getLeft());
			if(temp.getRight().getValue() != null)
				q.add(temp.getRight());
		}
		return rtn;
	}
	public boolean isEmpty()
	{
		return root == null || root.getValue() == null;
	}
	public RedBlackNode getRoot()
	{
		return root;
	}
	public RedBlackNode getNode(RedBlackNode iter, Comparable x)
	{
		if(iter == null || iter.getValue() == null)
			return null;
		int dir = x.compareTo(iter.getValue());
		if(dir < 0)
			return getNode(iter.getLeft(), x);
		else if(dir > 0)
			return getNode(iter.getRight(), x);
		return iter;
	}
	public String toString()
	{
		String rtn = "";
		ArrayList<RedBlackNode> nodes = preOrder(new ArrayList<>(), root);
		for(RedBlackNode k : nodes)
			rtn += k + "\n";
		return rtn;
	}
}
