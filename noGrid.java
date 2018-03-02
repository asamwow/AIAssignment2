import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class noGrid
{

    public static void main(String[] args) 
    {
      //Node initialization
      ArrayList<AStarNode> open = new ArrayList();
    
      AStarNode S = new AStarNode("S", null, new int[]{0}, 10);
      open.add(S);
      
      AStarNode A = new AStarNode("A", new AStarNode[]{S}, new int[]{3}, 7);
      //open.add(A);
      AStarNode D = new AStarNode("D", new AStarNode[]{A}, new int[]{6}, 1);
      //open.add(D);
      AStarNode F = new AStarNode("F", new AStarNode[]{D}, new int[]{1}, 1);
      //open.add(F);
      
      AStarNode B = new AStarNode("B", new AStarNode[]{S}, new int[]{2}, 10);
      //open.add(B);
      AStarNode E = new AStarNode("E", new AStarNode[]{B}, new int[]{4}, 8);
      //open.add(E);
      
      AStarNode C = new AStarNode("C", new AStarNode[]{S}, new int[]{1}, 20);
      //open.add(C);
      
      //Goal
      AStarNode G = new AStarNode("G", new AStarNode[]{C,E,F}, new int[]{20,8,1}, 0);
      //open.add(G);
      
      ArrayList<AStarNode> closed = new ArrayList();

      System.out.println("Search Order:");
      
      S.f = S.h;
      //System.out.println(S.successors.get(0).name);
      //Begin AStar search
      while(!open.isEmpty())
      {
         //current = node with lowest f
         AStarNode current = open.get(0);
         for (AStarNode n : open)
         {
            if (n.f < current.f)
            {
               current = n;
            }
         }
         
         System.out.print(current.name + " --> ");
                  
         //if at goal, end loop
         if (current.name == "G")
         {
            break;
         }
         
         open.remove(current);
         closed.add(current);
         
         //For each neighbor
         for(int i = 0; i < current.successors.size(); i++)
         {
            AStarNode successor = current.successors.get(i);
            successor.parent = current;
            //if neighbor is not in the closed list
            if(!closed.contains(successor))
            {
               //calc f for neighbor
               for (int x = 0; x < successor.parents.size(); x++)
               {
                  //dumb math to find the correct g value in the distance array...
                  if (successor.parents.get(x) == current)
                  {
                     successor.f = successor.gs.get(x) + successor.h;
                  }
               }
               //if neighbor is not in open_list
               if(!open.contains(successor))
               {
                  open.add(successor);
               }
            }
         }
      }
      System.out.println("\n\nBest Path:");
      AStarNode path = G;
      String print = G.name;
      while (path.parent != null)
      {
         print = path.parent.name + " --> " + print;
         path = path.parent;
      }
      System.out.print(print);
      
    }
}