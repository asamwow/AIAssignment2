import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AStarNode
{
   public int x,y,g,h;
   public String name;
   public int f;
   public AStarNode parent;
   public ArrayList<Integer> gs = new ArrayList();
   public ArrayList<AStarNode> parents = new ArrayList();
   public ArrayList<AStarNode> successors = new ArrayList();
   
   public AStarNode()
   {
   
   }
   
   //String n: name of node
   //AStarNode parent: The parent node
   //int dis: distance from parent
   //int h: hueristic to the end node
   public AStarNode(String n, AStarNode[] parents, int[] dist, int h)
   {
      this.name = n;
      
      if(parents != null)
      {
         for (AStarNode node : parents)
         {
            node.addChild(this);
            this.parents.add(node);
         }
         for (int i = 0; i < parents.length; i++)
         {
            this.gs.add(parents[i].g + dist[i]);
         }
      } else
      {
         this.g = 0;
      }

      this.h = h;
   }
   
   public void addChild(AStarNode child)
   {
      successors.add(child);
   }
   
   
}