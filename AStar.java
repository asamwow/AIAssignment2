import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AStar {

    private int[][] grid;

    public static void main(String[] args) {
        int width = 5;
        int height = 5;
        AStar a = new AStar();
        
        ArrayList<AStarNode> path = new ArrayList<>();
        path = a.runAStar(width,height,width - 1 ,height - 1);
        System.out.println("Path Taken:");
        for (AStarNode node : path) {
         System.out.print("("+node.x + "," + node.y + "), ");
        }
        System.out.println();
        
        System.out.println("Shown Path:");
        AStarNode goal = path.get(path.size() - 1);
        String best = "";
        a.grid[goal.x][goal.y] = 2;
        while(goal.parent != null)
        {
         best = "(" + goal.x + "," + goal.y + "), " + best;
         goal = goal.parent;
         a.grid[goal.x][goal.y] = 2;
        }
        System.out.println(best);
        
        for (int y = 0; y < height; y++)
        {
         System.out.println();
         for (int x = 0; x < width; x++)
         { 
            if (a.grid[x][y] == 1) System.out.print("- ");
            if (a.grid[x][y] == 0) System.out.print("0 ");
            if (a.grid[x][y] == 9) System.out.print("! ");
            if (a.grid[x][y] == 2) System.out.print("* ");
         }
        }
    }
    
    private void addIfValid(List<AStarNode> validNodes, int x, int y, AStarNode parent) {
     if (x < 0 || x >= grid.length || y < 0 || y > grid[x].length - 1 || grid[x][y] == 0) {
      return;
     }

     AStarNode node = new AStarNode();
     node.x = x;
     node.y = y;
     node.parent = parent;

     validNodes.add(node);
    }


    public ArrayList runAStar(int width, int height, int goalx, int goaly) {
        ArrayList<AStarNode> path = new ArrayList<>();
        grid = new int [width][height];

        for(int x = 0; x < width; x++)
        {
         for(int y = 0; y < height; y++)
         {
            if(y == goaly && x == goalx)
            {
               grid[x][y] = 9;
            } else
            {
               if (ThreadLocalRandom.current().nextInt(0, 10 + 1) == 10) grid[x][y]=0;
               else grid[x][y] = 1;
            }
         }
        }
        
        
        for (int y = 0; y < height; y++)
        {
         System.out.println();
         for (int x = 0; x < width; x++)
         {
            if (grid[x][y] == 1) System.out.print("- ");
            if (grid[x][y] == 0) System.out.print("0 ");
            if (grid[x][y] == 9) System.out.print("! ");

         }
        }
        System.out.println();

        ArrayList<AStarNode> openList = new ArrayList<>();
        ArrayList<AStarNode> closedList = new ArrayList<>();

        AStarNode startingNode = new AStarNode();
        startingNode.parent = null;
        startingNode.x = 0;
        startingNode.y = 0;
        startingNode.f = 0;

        openList.add(startingNode);

        AStarNode current = new AStarNode();
        AStarNode next = new AStarNode();
        ArrayList<AStarNode> openListTemp = new ArrayList();
        
        while (!openList.isEmpty()) {
            openListTemp = new ArrayList();
            //find the node with the least f on the open list
            float smallestF = Float.MAX_VALUE;
            for (AStarNode node : openList) {
                if (node.f < smallestF) {
                    smallestF = node.f;
                    current = node;
                }
            }
            //pop current off of the open list
            for (AStarNode node : openList) {
                if (node != current) {
                    openListTemp.add(node);
                }
            }
            openList = openListTemp;

            //generate current's 4 successors and set their parents to current
            ArrayList<AStarNode> successors = new ArrayList<>();

            addIfValid(successors, current.x - 1, current.y, current);
            addIfValid(successors, current.x + 1, current.y, current);
            addIfValid(successors, current.x, current.y - 1, current);
            addIfValid(successors, current.x, current.y + 1, current);

            ArrayList<AStarNode> tempFinal1 = new ArrayList<>();
            ArrayList<AStarNode> tempFinal2 = new ArrayList<>();
            for (AStarNode successor : successors) {
                int x = successor.x;
                int y = successor.y;
                if (grid[x][y] == 9) {
                    path.add(current);
                    path.add(successor);

                    return path;
                }
                successor.g = current.g + 1;

                successor.h = (goalx - successor.x) + (goaly - successor.y);

                successor.f = successor.g + successor.h;

                //check open and closed lists for the successors position, if found, the successor is skipped
                for (AStarNode checkOpenList : openList) {
                    if ((checkOpenList.x == x && checkOpenList.y == y) && checkOpenList.f < successor.f) {
                    } else {
                        tempFinal1.add(checkOpenList);
                    }
                }
                for (AStarNode checkClosedList : closedList) {
                    if ((checkClosedList.x == x && checkClosedList.y == y) && checkClosedList.f < successor.f) {
                    } else {
                        tempFinal2.add(checkClosedList);
                    }
                }
                //otherwise, add the node to the open list
                if (successors.contains(successor)) {//if the current successor has not been removed
                    next = successor;
                    openList.add(successor);
                }
            }
            //add the node and its parent to the path if the parent is not null
            if (current.parent != null) {
                path.add(current.parent);
                path.add(current);
            }
           //add current to the closed list
            closedList.add(current);
            current = next;
        }
        return path;
    }
}