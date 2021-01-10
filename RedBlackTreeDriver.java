import java.util.*;
import static java.lang.System.*;
import java.io.*;

@SuppressWarnings("unchecked")
public class RedBlackTreeDriver {
	public static void main(String args[])throws IOException
	{
		Scanner input = new Scanner(new File("RedBlackTree.txt"));

		RedBlackTree tree = new RedBlackTree();
		int numLines = input.nextInt();
		input.nextLine();
		for(int i=0; i<numLines; i++)
		{
			String[] list = input.nextLine().split(" ");
			for(String k:list)
			{
				RedBlackNode temp = new RedBlackNode(k);
				tree.add(temp);
			}
		}
		out.println("Starting Tree");
		out.println(tree);
		tree.printFullTree(tree.fullLevelOrder(), Math.min(tree.getNumLevels(),6));
		out.println();
		numLines = input.nextInt();
		for(int i=0; i<numLines; i++)
		{
			String x = input.next();
			RedBlackNode k = tree.remove(x);
			if(k!=null)
			{
				out.println("Level Order Tree after removing: "+k);
				out.println("Level order: "+tree.levelOrder());
			}
			else
				out.println("Cannot remove "+x+" from tree");
		}
		out.println();
		out.println("Finish Tree Display");
		tree.printFullTree(tree.fullLevelOrder(), Math.min(tree.getNumLevels(),6));
	}
}
