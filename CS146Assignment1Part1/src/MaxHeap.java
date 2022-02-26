import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

public class MaxHeap
{
   private ArrayList<Student> students; // will build the heap out of the arraylist
   /**
    * constructor that takes in an int(positive) and creates an empty arraylist of type student
    * that specified the initial capacity.
    * @param capacity
    */
   public MaxHeap(int capacity)
   {
      students = new ArrayList<Student>(capacity); 
   }
      
   public MaxHeap(Collection<Student> collection)
   {
      students = new ArrayList<Student>(collection);
      /**
       * We maxHapify starting from the bottom-up, left-to-right. 
       * we initialize at size/2 -1 because the last element will on the longest edge in the heap, and
       * based on heap structure, size/2 will give use the edge length.
       * i-- will move onto the next longest edge until we get to 0, the root. 
       */
      for(int i = size()/2 - 1; i >= 0; i--)
      {
         maxHeapify(i);
      }
   }
   /**
    * Returns the max value of the heap.
    * According to the property of a max heap, the root will always be the largest value in the heap.
    * if the arraylist has no elements, then we do not have a root we can get.
    * @return
    */
   public Student getMax()
   {
      if(size() < 1)
      {
         throw new IndexOutOfBoundsException("No maximum value:  the heap is empty.");
      }
      return students.get(0); // the root of the heap is always the max value of heap.
   }
   
   public Student extractMax()
   {
      Student value = getMax();
      students.set(0,students.get(size()-1)); // moves the root node to the last position in the heap, some leaf spot
      students.remove(size()-1); // removes that leaf
      maxHeapify(0); // readjusts the heap to meet max heap property
      return value;
   }
   
   // returns size of students arraylist
   public int size()
   {
      return students.size(); 
   }
   
   private void moveUp(Student stu) {
	  int index = students.indexOf(stu);
	  int parentLocation = parent(index);
	  Student parent = students.get(parentLocation);
	  while(index > 0 && stu.compareTo(parent) > 0) { // dont need to check if root is larger than parent
		   	// if parent node is smaller, swap with newly created node:
		   	swap(index, parentLocation);
		   	index = parentLocation;  
	   } 
   }
   
   
   public void insert(Student elt)
   {
      //Please write me.  I should add the given student into the heap,
	  //following the insert algorithm from the videos.
	   students.add(elt);
	   int index = students.size() -1;
	   int parLocation = parent(index);
	   while(index > 0 && elt.compareTo(students.get(parLocation)) > 0) { // dont need to check if root is larger than parent
		   	swap(index, parLocation);
		   	index = parLocation;  
		  	parLocation = parent(index); // update parent location
	   }   
   }
   
   public void addGrade(Student elt, double gradePointsPerUnit, int units)
   {
      //Please write me.  I should change the student's gpa (using a method
	  //from the student class), and then adjust the heap as needed using
	  //the changeKey algorithm from the videos.
	   elt.addGrade(gradePointsPerUnit, units);
	   int index = students.indexOf(elt);
	   int parIndex = parent(index);
	   int leftIndex = left(index);
	   int rightIndex = right(index);
	   
	   /** if elt's GPA has increased to the point where it is larger than its parent's gpa,
	    * we need to move elt up the heap
	    */
	   while(index > 0 && elt.compareTo(students.get(parIndex)) > 0) { // dont need to check if root is larger than parent
		   	swap(index, parIndex);
		   	index = parIndex;  
		  	parIndex = parent(index); // update parent location
	   }
	   /**
	    * If the gpa decreased, check to see if elt's students should be moving up
	    */
		   
	   Student leftStudent = students.get(leftIndex);
	   Student rightStudent = students.get(rightIndex);
	   
	   while(index > 0 && (elt.compareTo(leftStudent) < 0 || elt.compareTo(rightStudent) < 0)) {
		   maxHeapify(index);
	   }
   }
   
   
   private int parent(int index)
   {
      return (index - 1)/2;
   }
   
   private int left(int index)
   {
      return 2 * index + 1;
   }
   
   private int right(int index)
   {
      return 2 * index + 2;
   }
   
   private void swap(int from, int to)
   {
      Student val = students.get(from);
      students.set(from,  students.get(to));
      students.set(to,  val);
   }
   
   private void maxHeapify(int index)
   {
      int left = left(index);
      int right = right(index);
      int largest = index; // every root node of its subheap should be the largest value.
      if (left <  size() && students.get(left).compareTo(students.get(largest)) > 0)
      {
         largest = left;
      }
      if (right <  size() && students.get(right).compareTo(students.get(largest)) > 0)
      {
         largest = right;
      }
      if (largest != index)
      {
         swap(index, largest);
         maxHeapify(largest);
      }  
   }   
   public static void main(String[] args) {
	   MaxHeap heap = new MaxHeap(10);
	   Student susan = new Student("Susan", 3, 6);
	   Student ben = new Student("Ben", 2.4, 10);
	   Student reed = new Student("Reed", 3.3, 3);
	   Student johnny = new Student("Johnny", 1, 4);
	   heap.insert(susan);
	   heap.insert(ben);
	   heap.insert(johnny);
	   heap.insert(reed);
	   System.out.println("The max"+heap.getMax().getName()); // my own statement
	   assertEquals(reed, heap.getMax());
	
	   heap.addGrade(susan, 4, 3);  //should give her a 3.333333333 gpa
	   System.out.println("susan gpa:"+susan.gpa());
	   assertEquals(susan, heap.getMax());
	   
	
	   
   }
}