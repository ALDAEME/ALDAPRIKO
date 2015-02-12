package alda.heap;
// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate
//
// @author Läroboken
// @author Elise Edette tero0337
// @author Emma Persson empe5691
// @author Aframyeos Rohoum afro0793

/**
 * Implements a D- heap.
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>>
{
	
	private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_CHILDREN = 2;

    private int currentSize;      // Number of elements in heap
    private int childCount;
    private AnyType [ ] array; // The heap array

    /**
     * Construct the D- heap.
     */
    public DHeap( )
    {
        this( DEFAULT_CHILDREN );
    }

    /**
     * Construct the D- heap.
     * @param capacity the capacity of the binary heap.
     */
    public DHeap( int antal )
    {
        if(antal < 2) throw new IllegalArgumentException();

        childCount = antal;
        array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY ];
    }

    public int size()
    {
    	return currentSize;
    }
    
    public AnyType get(int index)
    {
    	return array[index];
    }
    
    public int parentIndex(int index)
    {
    	/*
    	 * Vi använder inte denna metod eftersom den är valfri och inte fungerar med våran insert() ändå för den använder index 0 
    	 * för att förenkla algoritmen. Vi kunde ha skrivit om insert() men eftersom denna metod är optional så var det enklare att inte använda denna metod.
    	 * Vi är inte säkra om vi får ta bort denna metod (och därmed testfallet) så vi har den kvar för att inte trippa testfallet.
    	 * Man skulle kunna tänka att det är ok att ta bort testfallet som berör denna metod men vi valde att ha den kvar, just in case.
    	 * */
       if(index<=1){
    	   throw new IllegalArgumentException();
       }

    	return (index+(childCount-2))/childCount;
    }
    
    public int firstChildIndex(int index)
    {
    	if(index < 1) throw new IllegalArgumentException();

    	return childCount * (index - 1) + 2;
    }
    
    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );

        // Percolate up
        int hole = ++currentSize;

        for( array[ 0 ] = x; x.compareTo( array[ (hole+(childCount-2))/childCount ] ) < 0; hole = (hole+(childCount-2))/childCount )
            array[ hole ] = array[ (hole+(childCount-2))/childCount ];

        array[ hole ] = x;
    }
    
    private void enlargeArray( int newSize )
    {
            AnyType [] old = array;
            array = (AnyType []) new Comparable[ newSize ];
            for( int i = 0; i < old.length; i++ )
                array[ i ] = old[ i ];        
    }
    
    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
    	/*
    	 * Arbetet avröts för denna metod eftersom den inte verkar användas.
    	 * Får vi ta bort denna? Vi är inte säkra så den stannar kvar.
    	 * */
        for( int i = parentIndex(currentSize); i > 0; i-- )
            percolateDown( i );
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }


    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole )
    {
        int child;
        AnyType tmp = array[ hole ];

        for( ; firstChildIndex(hole) <= currentSize; hole = child ){
        	
            child = firstChildIndex(hole);
           int childtmp = firstChildIndex(hole);
            for(int i=1; i<(childCount+1); i++){
            	if(childtmp+i <=currentSize){
            		if( child!= currentSize && array[ childtmp + i ].compareTo( array[ child ] ) < 0 ){
            			child=childtmp+i;
            		}
            	}
            }
            if( array[ child ].compareTo( tmp ) < 0 )
            	array[ hole ] = array[ child ];
            else
                break;    
        }
        array[ hole ] = tmp;
    }


        // Test program
    public static void main( String [ ] args )
    {
        int numItems = 10000;
        DHeap<Integer> h = new DHeap<Integer>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }

}
