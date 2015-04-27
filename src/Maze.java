/*------------------------------------------------------------------------
 * CS60 Spring, 2000
 * Name         :   SooYoung Jung
 * Filename     :   Maze.java
 * Assignment   :   5
 * Date         :   February 24, 2000
 * Description  :   This program is that a laboratory mouse is seeking the 
 *                  shortest path from its starting point in a maze to an 
 *                  appropriate destination, i.e., a can of spam). To reach the 
 *                  spam by the shortest possible path, uses implementing 
 *                  breadth-first search.
 */

/*
 * The MazeCell class implements a single cell of 
 *   a maze. It includes private data fields that
 *   tell 
 *     whether a cell is marked (already visited by the BFS)
 *     what a cell's contents are
 *     what a cell's parent is (for the BFS)
 *     what the cell's position is in the maze
 */

class MazeCell
{

    private boolean  marked;
    private char   contents;
    private MazeCell parent;
    private int         x,y;
  
    /*
     * The constructor for MazeCells. The cells are by default
     *   unmarked and without a parent cell. To construct a new cell,
     *   its position in the maze (x,y) and its contents '|', '-', 'X', or 'S'
     *   must be specified.
     */
 
    public MazeCell(int x, int y, char c) 
    {
        marked = false;
        contents = c;
        parent =  null;
        this.x = x; 
        this.y = y;
    }
  
    /*
     * A public nonstatic accessor method that returns the contents
     *   of the MazeCell that invokes it.
     */
    public char getContents() 
    {
        return this.contents;
    }

    /*
     * A public nonstatic method that changes marked value 
     *   of the MazeCell to ture that invokes it.
     */
    public void Mark()
    {
        marked = true;
    }

    /*
     * A public nonstatic accessor method that returns the marked value
     *   of the MazeCell that invokes it.
     */
    public boolean isMarked() 
    {
        return this.marked;
    }

    /*
     * A public nonstatic accessor method that returns the parent
     *   of the MazeCell that invokes it.
     */
    public MazeCell getParent() 
    {
        return this.parent;
    }

    /*
     * A public nonstatic method that changes parent value 
     *   of the MazeCell to parent that invokes it.
     */
    public void changeParent(MazeCell child) 
    {
        child.parent = this;
    }

    /*
     * A public nonstatic accessor method that returns the x
     *   of the MazeCell that invokes it.
     */
    public int xValue() 
    {
        return this.x;
    }  
    
    /*
     * A public nonstatic accessor method that returns the y
     *   of the MazeCell that invokes it.
     */
    public int yValue() 
    {
        return this.y;
    }
    
    /*
     * A public nonstatic method that changes contents value 
     *   of the MazeCell to '*' that invokes it.
     */
    public void star() 
    {
        contents = '*';
    }   
}

/*
 * The Maze class is in essence a two-dimensional array of
 *   MazeCells. In addition, each Maze has a name, a height
 *   and width, and the x- and y- coordinates of the
 *   starting point and finishing point (the spam) are maintained.
 */

class Maze
{
    private String       name;
    private MazeCell[][] grid;
    private int         width;
    private int        height;
    private int        startx;
    private int        starty;
    private int         spamx;
    private int         spamy;
  
    /* 
     * These static arrays of Strings represent the mazes
     *  available. The constructor for the Maze class
     *  needs one of the names of these mazes as input,
     *  it then initializes the array of MazeCells appropriately.
     *
     * The first string is the maze's name.
     * The second string is the maze's height.
     * The third string is the maze's width.
     * The remaining strings are the contents of the maze:
     *   a '|' or '-' character represents a wall
     *   a capital S represents the starting point
     *   a capital X represents the finishing point (the spam)
     *
     * These are static data members, meaning that they "belong to"
     *   the maze class, not every constructed maze object.
     *   After all, any particular object only needs to know
     *   about the single maze it's representing.
     */
  
    static String[] simpleMaze = 
    {
        "simpleMaze", "5", "5",
        "|---|",
        "|X  |",
        "|   |",
        "|  S|",
        "|---|"
    };
  
    static String[] mediumMaze = 
    { 
        "mediumMaze", "10", "10",
        "|--------|",
        "|   |    |",
        "| S |  | |",
        "|   |X | |",
        "|   |--| |",
        "|  |   | |",
        "|  |   | |",
        "|  | --| |",
        "|        |",
        "|--------|"
    };
  
    static String[] difficultMaze = 
    {
        "difficultMaze", "15", "15",
        "|-------------|",
        "|             |",
        "| | |-----| | |",
        "| | |     | | |",
        "| | | |-| | | |",
        "| | | |X| | | |",
        "| |   | | | | |",
        "| | | | | | | |",
        "| | | | | | | |",
        "| | |   | | | |",
        "| | |---- | | |",
        "| |         | |",
        "| |---------| |",
        "|S            |",
        "|-------------|",
    };

    static String[] bigMaze = 
    {
        "bigMaze", "25", "35",
        "|---------------------------------|",
        "|S                                |",
        "|  |------| |---------| |------|  |",
        "|  |      | |    |                |",
        "|  | --|    |--     --------      |",
        "|  | | |  | |    --|  |    | |- --|",
        "|  | | |- |     |        | | |    |",
        "|  | |    |-----|   |----- | | ---|",
        "| -| | ---|         |      |      |",
        "| |  |          |   |      |  ----|",
        "| |  |------------ ------  |  |   |",
        "| |        |        |     |   --  |",
        "| |  |---  |-- -|------ ---       |",
        "| |--|     |    |       |      |  |",
        "|   |   ---| ---|    |-----    |  |",
        "|  |    |     |   |--|      |--- -|",
        "|  | ----- ---- | |       |--     |",
        "|  |    |  --|  ----| --| |       |",
        "|- --- -- -|        |   | | |-|---|",
        "|   |   |  -----|-- ----| | |     |",
        "|-- |      | |  |   |   |-  |-| --|",
        "|   |------| |  |       |     |   |",
        "| -    |     |  ------ --------|  |",
        "|   |               |            X|",
        "|---------------------------------|",
    };
  
    static String[] ouchMaze = 
    {
        "ouchMaze", "5", "5",
        "|---|",
        "|X  |",
        "|---|",
        "|  S|",
        "|---|"
    };
  

    /* 
     * This constructor takes in the name of one of the 
     *  above statically defined String arrays and creates 
     *  the maze represented in the specified array.
     */

    public
    Maze(String[] info) 
    {
        name   = info[0];
        height  = (new Integer(info[1])).intValue();
        width = (new Integer(info[2])).intValue();
        grid   = new MazeCell[height][width];
    
        char c;
    
        for (int i=0 ; i<height ; ++i) 
        {
            for (int j=0 ; j<width ; ++j) 
            {
                c = info[3+i].charAt(j);
                grid[i][j] = new MazeCell(j,i,c);

                if (c == 'S') 
                {
                    startx = j; 
                    starty = i;
                }

                if (c == 'X') 
                {
                    spamx = j;
                    spamy = i;
                }
            }
        }
    }


    /*
     * The printMark method changes contents of the MazeCell to '*'
     *   here invoked Queue is shortest path.
     */
    public void printMark(MazeCell pathCell) 
    {
        while ( pathCell.getParent() != null ) 
        {                   // changes contents until it meets null pointer.
            pathCell = pathCell.getParent();
               pathCell.star();
        }
    }   
    
 
    /*
     * The toString method allows a user to print
     *   a Maze Object with the System.out.println
     *   command
     */

    public String toString()
    {
        StringBuffer theFullAnswer = new StringBuffer();
        theFullAnswer.append("Maze ");
        theFullAnswer.append(name);
        theFullAnswer.append(" :  \n");

        for (int i=0 ; i<height ; ++i) 
        {
            for (int j=0 ; j<width ; ++j) 
            {
                theFullAnswer.append(String.valueOf(grid[i][j].getContents()));
            }
            theFullAnswer.append("\n");
        }

        theFullAnswer.append("\n");
        return theFullAnswer.toString();
    }
  
    /* 
     * BFS routine 
     * Using Queue, this solve() method is doing BFS.
     * enqueue all children and deque them until it gets final point.
     */
  
    void solve() 
    {
        MazeCell N, S, E, W;
                            // Store North, South, East, and West of MazeCell.

        Queue Q = new Queue();
                            // Create new Queue to do BFS
                            
        MazeCell startCell = new MazeCell(startx, starty, 'S');
                            // Create MazeCell and mark it as starting cell.
        startCell.Mark();
                            // Set it as visited.
                            
        Queue.enqueue(startCell, Q);
    
        while(!Queue.is_empty(Q))
        {
            MazeCell CMC = (MazeCell)Queue.dequeue(Q);

            if( CMC.getContents() == 'X' )
            {               // Checks content of dequeued Cell.
                            // If it is 'X', found finish position.
                printMark(CMC);
                            // Print '*' mark at shortest path.
                return;
            }

                            // North child
                            // store North child which can be the one of 
                            // shortest path
            if ( !((CMC.yValue() - 1) <= 0) )
            {               // Out of range.
        
                N = (grid[(CMC.yValue())-1][CMC.xValue()]);
                            // Gets north MazeCell
                            
                if ( N.getContents() != '|' && N.getContents() != '-' &&
                   !(N.isMarked()) )
                {           // If content is not wall and have not visited,
                            // For North MazeCell
                    N.Mark();
                            // Set it as visited cell.
                            
                    CMC.changeParent(N);
                            // Keep parent's infomation so later it can trace
                            // shortest paht from finish point to start point.
                            
                    Queue.enqueue( N, Q );
                }
            }

                            // South child
                            // store South child which can be the one of 
                            // shortest path
            if ( !((CMC.yValue()) + 1 >= height) )
            {               // Out of range.
            
                S = (grid[(CMC.yValue())+1][CMC.xValue()]);             
                            // Gets South MazeCell
                            
                if ( S.getContents() != '|' && S.getContents() != '-' &&
                   !(S.isMarked()) )
                {           // If content is not wall and have not visited.
                            // For North MazeCell
                    S.Mark();
                            // Set it as visited cell.
                            
                    CMC.changeParent(S);
                            // Keep parent's infomation so later it can trace
                            // shortest paht from finish point to start point.
                            
                    Queue.enqueue( S, Q );
                }
            }

                            // East child
                            // store East child which can be the one of 
                            // shortest East
            if ( !((CMC.xValue()) - 1 <= 0) )
            {               // Out of range.    

                E = (grid[CMC.yValue()][(CMC.xValue())-1]);
                            // Gets East MazeCell
                            
                if ( E.getContents() != '|' && E.getContents() != '-' &&
                   !(E.isMarked()) )
                {           // If content is not wall and have not visited,
                            // For North MazeCell
                    E.Mark();
                            // Set it as visited cell.
                    CMC.changeParent(E);
                            // Keep parent's infomation so later it can trace
                            // shortest paht from finish point to start point.
                            
                    Queue.enqueue( E, Q );
                }
            }

                            // West child
                            // store West child which can be the one of 
                            // shortest path
            if ( !((CMC.xValue()) + 1 >= width) )
            {               // Out of range.    

                W = (grid[CMC.yValue()][(CMC.xValue())+1]);
                            // Gets West MazeCell
                            
                if ( W.getContents() != '|' && W.getContents() != '-' &&
                   !(W.isMarked()) )
                {           // If content is not wall and have not visited,
                            // For North MazeCell
                    W.Mark();
                            // Set it as visited cell.
                            
                    CMC.changeParent(W);
                            // Keep parent's infomation so later it can trace
                            // shortest paht from finish point to start point.
                            
                    Queue.enqueue( W, Q );
                }
            }
        }
        
        System.out.println("Maze not solvable!");
        System.out.println();
                            // Maze is not solvable so
                            // Reports it and returns.
        
    }
  
    /*
     * The main method for testing the code.
     *   At the moment, it does not solve the mazes, so the 
     *   solutions do not differ from the originals.
     *   The correct output (one possible version..
     *   there are many shortest paths in general)
     *   is in /cs/cs60/as/a5/Maze.out
     */
  
    public static void main(String[] arg)
    {
        Maze M = new Maze(simpleMaze);  
        System.out.println(M);
        M.solve();
        System.out.println(M);

        M = new Maze(mediumMaze);  
        System.out.println(M);
        M.solve();
        System.out.println(M);

        M = new Maze(difficultMaze);  
        System.out.println(M);
        M.solve();
        System.out.println(M);

        M = new Maze(bigMaze);  
        System.out.println(M);
        M.solve();
        System.out.println(M);
    
        M = new Maze(ouchMaze);  
        System.out.println(M);
        M.solve();
        System.out.println(M);
    }
}


/*
 * QCell class
 */

class QCell
{

  /*
   * The data members
   */

  protected Object    data;          // Storage for the data
  protected QCell     next;          // A reference to the next QCell
                                     //   in the Queue
  
  /*
   * Constructor requiring the Object to be stored
   *   and the QCell that the next field refers to.
   */
  
  QCell(Object o, QCell next)
  {
    this.data = o;
    this.next = next;  
  }
  
}


/*
 * The Queue class consists of two references:
 *   front, a reference to the first QCell in the queue,
 *          which is the first to go on a call to dequeue.
 *   back,  a reference to the last QCell in the queue,
 *          which is where new QCells are enqueued.
 */

class Queue
{
  
  /*
   * The data members
   */

  protected QCell front;
  protected QCell back;
  
  /*
   * Constructor for an empty Queue, in which
   *  both the front and the back are null references.
   */
  
  Queue()
  {
    front = null;
    back = null;
  }
  
  /*
   * The static and nonstatic is_empty methods.
   * A Queue is empty if either of its front
   *   or back QCell references is null.
   */
  
  public boolean is_empty() 
  {
    return (front == null);
  }

  public static boolean is_empty(Queue Q) 
  {
    return Q.is_empty();
  }
  
  /*
   * The static and nonstatic enqueue methods.
   * A new QCell is created and placed at the back of
   *   the queue. The previous back (if any)
   *   is adjusted to point to the new back.
   * The front is set to the same QCell if the Queue
   *   was originally empty.
   */
  
  public void enqueue(Object o)
  {
    QCell newCell = new QCell(o,null);
    
    if (back == null) {
      back = front = newCell;
    } else {
      back.next = newCell;
      back = newCell;    
    }

  }
  
  public static void enqueue(Object o, Queue Q)
  {
    Q.enqueue(o);  
  }
  
  /*
   * The static and nonstatic dequeue methods.
   * If there is only one QCell remaining, its data
   *   is returned and both the Queue's front and back are
   *   set to null, since the resulting Queue is empty.
   * If there are more than one QCell in the Queue,
   *   the data of the one in the front is returned and
   *   the front refernce is adjusted to refer to
   *   the next QCell in line.
   */
  
  public Object dequeue()
  {
    Object retVal = front.data;
    front = front.next;
    if (front == null) back = null;
    return retVal;
  }
  
  public static Object dequeue(Queue Q)
  {
    return Q.dequeue();  
  }
  
  /*
   * toString() is a nonstatic method that returns a string representation of
   *            the Queue that invokes this method. It may be invoked implicitly
   *            when a Queue is an argument to the + operator and another String.
   */

  public String toString()
  {
    StringBuffer theFullAnswer = new StringBuffer();  // now it's ""
    theFullAnswer.append("The Queue (or derived class) is\n"); 

    QCell current = this.front;
    while (current != null) {
      theFullAnswer.append("  " + current.data + "\n");
      current = current.next;
    }
    theFullAnswer.append("\n");
    return theFullAnswer.toString();
  }
  
  
  /* no main needed */
  
  public static void main(String[] arg)
  {
    Queue Q = new Queue();
    Q.enqueue(new Double(12.0));
    System.out.println(Q);
    Q.enqueue(new Double(14.25));
    System.out.println(Q);
    System.out.println("Q.dequeue() is " + Q.dequeue());
    System.out.println(Q);
    System.out.println("Q.dequeue() is " + Q.dequeue());
    System.out.println(Q);
    enqueue(new Double(-40.0),Q);
    Double D = new Double(1000.0);
    enqueue(D,Q);
    System.out.println(Q);
    System.out.println("dequeue(Q) is " + dequeue(Q));
    System.out.println(Q);
  }
  
}