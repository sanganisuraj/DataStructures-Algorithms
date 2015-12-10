import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Suraj Sangani | Project to implement Multi-Dimensional Search. The
 *         Project allows users to specify attributes of products that they are
 *         seeking, and shows products that have most of those attributes. To
 *         make search efficient, the data is organized using appropriate data
 *         structures, such as balanced trees. The idea is to create a new set
 *         of references to the objects for each search field, and organize them
 *         to implement search operations on that field efficiently. As the
 *         objects change, these access structures have to be kept consistent.
 * 
 *         In this project, each object has 3 attributes: id , name, and price.
 *         The following functions are used: a. insert(id,price,name): insert a
 *         new item. If an entry with the same id already exists, its name and
 *         price are replaced by the new values. If name is empty, then just the
 *         price is updated. Returns 1 if the item is new, and 0 otherwise. b.
 *         searches(id): return price of item with given id (or 0, if not
 *         found). c. remove(id): delete item from storage. Returns the sum of
 *         the long ints that are in the name of the item deleted (or 0, if such
 *         an id did not exist). d. minPrice(n): given a long int n, find items
 *         whose name contains n (exact match with one of the long ints in the
 *         item's name), and return lowest price of those items. e. maxPrice(n):
 *         given a long int n, find items whose name contains n, and return
 *         highest price of those items. f. priceange(n,low,high): given a long
 *         int n, find the number of items whose name contains n, and their
 *         prices fall within the given range, [low, high]. g. pricehike(l,h,r):
 *         increase the price of every product, whose id is in the range [l,h],
 *         by r% Discard any fractional pennies in the new prices of items.
 *         Returns the sum of the net increases of the prices.
 * 
 *         The project uses an AVL Tree along with an HashMap to simultaneously
 *         perform search operations efficiently.
 * 
 *         Apart from the above functions, some helper functions are also used:
 *         1. recursiveBalance(AvlNode) - Check the balance for each node
 *         recursively and call required methods for balancing the tree until
 *         the root is reached. 2. removeFoundNode(AvlNode) - Removes a node
 *         from a AVL-Tree, while balancing will be done if necessary. 3.
 *         rotateLeft(AvlNode) - Left Rotation of node to balance the tree. 4.
 *         rotateRight(AvlNode) - Right Rotation of node to balance the tree. 5.
 *         doubleRotateLeftRight(AvlNode) - Rotation to balance the tree. 6.
 *         doubleRotateRightLeft(AvlNode) - Rotation to balance the tree. 7.
 *         successor(AvlNode) - Returns the successor of a given node in the
 *         tree (search recursively). 8. height(AvlNode) - Calculating the
 *         "height" of a node. 9. maximum(int, int) - Returns maximum of two
 *         integers. 10. debug(AvlNode) - Gives entire information about the
 *         node. 11. setBalance(AvlNode) - Sets Balance of Tree. 12. inorder() -
 *         returns inorder traversal of Tree. 13. preorder() - returns preorder
 *         traversal of Tree. 14. postorder() - returns postorder traversal of
 *         Tree.
 *
 */
class AvlTree {
	// HashMap which holds the id as the key and the node as the value.
	public static HashMap<Integer, AvlNode> idname = new HashMap<Integer, AvlNode>();

	protected AvlNode root; // the root node

	/***************************** Core Functions ************************************/

	/**
	 * Add a new element with id, price and name into storage.
	 * 
	 * @param id
	 * @param price
	 * @param name
	 * @return 1 if the item is new, and 0 otherwise.
	 */
	public int insert(int id, float price, String name) {
		// split the names into individual ints and store it in array
		String[] names = name.split(" ");
		// create new node
		AvlNode n = new AvlNode(id, price, names);
		boolean flag; // flag value to appropriately return value of insert
		idname.put(id, n); // if new node, put it in the hashmap
		flag = insertAVL(this.root, n); // start recursive procedure for
										// inserting the node
		if (flag == true) // new node has been inserted into the tree
		{
			return 1; // return 1 since new node has been inserted into the tree
		} else
			// node already exists in the tree
			return 0; // return 0
	}

	/**
	 * Recursive method to insert a node into the tree.
	 * 
	 * @param p
	 *            Node that already exists in the tree
	 * @param q
	 *            New node to be inserted into the tree
	 * @return true if node successfully inserted; false if node already exists
	 *         in the tree
	 */
	public boolean insertAVL(AvlNode p, AvlNode q) {
		// If node to compare is null, the node is inserted.
		// If the root is null, it is the root of the tree.
		if (p == null) {
			this.root = q;
			return true;
		} else {

			// If compare node is smaller, continue with the left node
			if (q.key < p.key) {
				if (p.left == null) {
					p.left = q;
					q.parent = p;
					recursiveBalance(p);
					return true;
					// Node is inserted now, continue checking the balance
				} else {
					insertAVL(p.left, q);
				}

			} else if (q.key > p.key) {
				if (p.right == null) {
					p.right = q;
					q.parent = p;
					// Node is inserted now, continue checking the balance
					recursiveBalance(p);
					return true;
				} else {
					insertAVL(p.right, q);
				}
			}
			// node exists in the tree
			else {
				if (q.name.length == 0) // if name of node is null
				{
//					System.out.println(p.price + " " + q.price);
					p.price = q.price; // update price of node
//					System.out.println(p.price + " " + q.price);
				}
				else
				{
				p.name = q.name;// update name of old node with new name
				p.price = q.price;// update price of old node with new price
				}
				if (idname.containsKey(q.key)) // if hashmap already contains a key with id
					// to be inserted
					{
					for(int i=0;i<p.name.length;i++)
						idname.put(q.key, p); // replace the node of key id with new price
					// and new name
					}				
				return false;// return false since node already exists in the
								// tree
			}
		}
		return true; // default return statement
	}

	/**
	 * Check the balance for each node recursively and call required methods for
	 * balancing the tree until the root is reached.
	 * 
	 * @param cur
	 *            : The node to check the balance for, usually you start with
	 *            the parent of a leaf.
	 */
	public void recursiveBalance(AvlNode cur) {

		// we do not use the balance in this class, but the store it anyway
		setBalance(cur);
		int balance = cur.balance;

		// check the balance
		if (balance == -2) {

			if (height(cur.left.left) >= height(cur.left.right)) {
				cur = rotateRight(cur);
			} else {
				cur = doubleRotateLeftRight(cur);
			}
		} else if (balance == 2) {
			if (height(cur.right.right) >= height(cur.right.left)) {
				cur = rotateLeft(cur);
			} else {
				cur = doubleRotateRightLeft(cur);
			}
		}

		// we did not reach the root yet
		if (cur.parent != null) {
			recursiveBalance(cur.parent);
		} else {
			this.root = cur;
			// System.out.println("------------ Balancing finished ----------------");
		}
	}

	/**
	 * Function to search for item with given id
	 * 
	 *
	 * @param val
	 *            - id of item to be searched
	 * @return true if node exists in the tree and false if node doesn't exist
	 *         in the tree
	 */
	public float find(int val)
	{
		AvlNode r = idname.get(val);
		return r.price;
	}
		// HashMap to store the sum of the names of the item to be deleted for
	// remove function
	private HashMap<Integer, Integer> hashmap = new HashMap<Integer, Integer>();

	// Constructor to get the hashmap
	public HashMap<Integer, Integer> getHashmap() {
		return hashmap;
	}

	// constructor to set the hashmap
	public void setHashmap(HashMap<Integer, Integer> hashmap) {
		this.hashmap = hashmap;
	}

	/**
	 * Function to remove item.
	 * 
	 * @param k
	 *            id of item to be removed
	 */
	public void remove(int k) {
		// First we must find the node, after this we can delete it.
		removeAVL(this.root, k);
		// remove node from the hashmap
		idname.remove(k);
	}

	/**
	 * Finds a node and calls a method to remove the node.
	 * 
	 * @param p
	 *            The node to start the search.
	 * @param q
	 *            The id of node to remove.
	 */
	public void removeAVL(AvlNode p, int q) {
		int names = 0;
		if (p == null) {
			return; // empty tree
		} else {
			if (p.key > q)// if id of root is greater than id of item to be
							// removed
			{
				removeAVL(p.left, q);// recursively access the left sub-child of
										// the parent
			} else if (p.key < q)// if id of root is smaller than id of item to
									// be removed
			{
				removeAVL(p.right, q);// recursively access the right sub-child
										// of the parent
			} else if (p.key == q)// found correct node to be removed
			{
				String name[] = new String[p.name.length];// name array to store
															// the names of the
															// item to be
															// removed
				for (int i = 0; i < p.name.length; i++) {
					name[i] = p.name[i]; // store the names of the item to be
											// removed
				}
				for (int j = 1; j < name.length; j++) {
					names = names + Integer.valueOf(name[j]); // calculate the
																// sum of the
																// names
				}
				hashmap.put(0, names);// put the sum of the names at key 0 in
										// the hashmap
				// we found the node in the tree.. now lets go on!
				removeFoundNode(p);
			}
		}

	}

	/**
	 * Removes a node from a AVL-Tree, while balancing will be done if
	 * necessary.
	 * 
	 * @param q
	 *            The node to be removed.
	 */
	public void removeFoundNode(AvlNode q) {
		AvlNode r;
		// at least one child of q, q will be removed directly
		if (q.left == null || q.right == null) {
			// the root is deleted
			if (q.parent == null) {
				this.root = null;
				q = null;
				return;
			}
			r = q;
		} else {
			// q has two children --> will be replaced by successor
			r = successor(q);
			q.key = r.key;
		}

		AvlNode p;
		if (r.left != null) {
			p = r.left;
		} else {
			p = r.right;
		}

		if (p != null) {
			p.parent = r.parent;
		}

		if (r.parent == null) {
			this.root = p;
		} else {
			if (r == r.parent.left) {
				r.parent.left = p;
			} else {
				r.parent.right = p;
			}
			// balancing must be done until the root is reached.
			recursiveBalance(r.parent);
		}
		r = null;
	}

	/**
	 * Function to calculate minimum price of items consisting of a given name
	 * 
	 * @param name
	 *            name or part of a name of the item
	 * @return minimum price of items which contains a given name
	 */
	public float minPrice(String name) {
		float smallest = Float.MAX_VALUE; // variable to find out minimum price
		int large = Integer.MIN_VALUE;// variable to store the largest key in
										// idname
		Set a = new HashSet();// create a new hashset to store the keys
		a = idname.keySet();// store the key set of idname in a
		Iterator it = a.iterator();// iterator to iterate over the keyset
		int elementarray[] = new int[a.size()];// array to store the keys in the
												// keyset
		int ite = 0;// counter to loop over the elements of the key set one key
					// at a time
		while (it.hasNext())// while there are keys left in the keyset
		{
			int element = (int) it.next();// store the key in the element
			elementarray[ite] = element;// store the element in the array
			ite++;// increment ite to access next key
		}
		for (int iter = 0; iter < elementarray.length; iter++)// loop over the
																// element array
		{
			if (large < elementarray[iter])// find largest element in the array
			{
				large = elementarray[iter];
			}
		}
		float price[] = new float[large + 1];// array to store prices of items
		for (int i = 1; i <= large; i++)// loop over all the keys in the keyset
		{
			if (idname.containsKey(i) == false)// key at given position is not
												// present in the storage
			{
				// System.out.println("Sorry key not present in the storage");
			} else// found key in storage
			{	AvlNode t = idname.get(i); // store the node found at given
											// position of idname
				// System.out.println("Name of node" + t.name[(int) i]);
				// System.out.println(name);
				String abc = " "; // string to store the names of the item
				for (int k = 0; k < t.name.length; k++) {
					abc = abc + t.name[k].toString();// store name of item
														// accessed
//					 System.out.println("abc" + abc);
				}
				if (abc.contains(name))// item contains the given name input by
										// the user
				{
					price[(int) i] = t.price;// store the price of the item in
												// the array
//					 System.out.println("success"+price[(int) i]);
				
				if (smallest > price[(int) i])// loop to find out the minimum
												// price of the item
				{
//					System.out.println("first" + smallest + " " + price[(int) i]);
					smallest = price[(int) i];
//					System.out.println("later" + smallest + " " + price[(int) i]);
				}
				}
			}

		}
//		System.out.println("Smallest" + smallest);
		return smallest;// return minimum price of item

	}

	/**
	 * Function to find out the maximum price of items consisting of a given
	 * name
	 * 
	 * @param name
	 *            name or part of the name of the item
	 * @return the maximum price of items containing the given name
	 */
	public float maxPrice(String name) {
		float largest = Float.MIN_VALUE;// variable to find out maximum price
		int large = Integer.MIN_VALUE;// variable to store the largest key in
										// idname
		Set a = new HashSet();// create a new hashset to store the keys
		a = idname.keySet();// store the key set of idname in a
		Iterator it = a.iterator();// iterator to iterate over the keyset
		int elementarray[] = new int[a.size()];// array to store the keys in the
												// keyset
		int ite = 0;// counter to loop over the elements of the key set one key
					// at a time
		while (it.hasNext())// while there are keys left in the keyset
		{
			int element = (int) it.next();// store the key in the element
			elementarray[ite] = element;// store the element in the array
			ite++;// increment ite to access next key
		}
		for (int iter = 0; iter < elementarray.length; iter++)// find largest
																// element in
																// the array
		{
			if (large < elementarray[iter])// loop to find out the largest key
			{
				large = elementarray[iter];
			}
		}
		float price[] = new float[large + 1];// array to store the price of the
												// item
		for (int i = 1; i <= large; i++) {
			// System.out.println(idname.get(i));
			// System.out.println(idname.keySet());
			if (idname.containsKey(i) == false)// key at given position not
												// present in the storage
			{
				// System.out.println("Sorry key not present in the storage");
			} else {
				AvlNode t = idname.get(i);// store the node found at given
											// position in idname
				// System.out.println("Name of node" + t.name[(int) i]);
				// System.out.println(name);
				String abc = " ";// variable to store the names of the item
				for (int k = 0; k < t.name.length; k++) {
					abc = abc + t.name[k].toString();// store name of the item
														// accessed
					// System.out.println("abc" + abc);
				}
				if (abc.contains(name))// item contains the given input by the
										// user
				{
					price[(int) i] = t.price;// store the price of the item in
												// array
					// System.out.println("success"+price[(int) i]);
				}
				if (largest < price[(int) i])// loop to find maximum price of
												// the item
				{
					largest = price[(int) i];
				}

			}

		}
		// System.out.println("Largest" + largest);
		return largest;// return the maximum price of item containing the given
						// name

	}

	/**
	 * Function to find out all items consisting of a given name in the given
	 * price range. low and high are also considered if an item is found with
	 * price=low or price=high
	 * 
	 * @param name
	 *            name or part of name of the item
	 * @param low
	 *            lower bound of the price range.
	 * @param high
	 *            higher bound of the price range
	 * @return number of items in the given price range
	 */
	public float pricerange(String name, float low, float high) {
		int large = Integer.MIN_VALUE;// variable to store the largest key in
										// idname
		Set a = new HashSet();// create a new hashset to store the keys
		a = idname.keySet();// store the key set of idname in a
		Iterator it = a.iterator();// iterator to iterate over the keyset
		int elementarray[] = new int[a.size()];// array to store the keys in the
												// keyset
		int ite = 0;// counter to loop over the elements of the key set one key
					// at a time
		while (it.hasNext())// while there are keys left in the keyset
		{
			int element = (int) it.next();// store the key in the element
			elementarray[ite] = element;// store the element in the array
			ite++;// increment ite to access next key
		}
		for (int iter = 0; iter < elementarray.length; iter++)// find largest
																// element in
																// the array
		{
			if (large < elementarray[iter])// loop to find out the largest key
			{
				large = elementarray[iter];
			}
		}
		float price[] = new float[large + 1];// array to hold the price of the
												// items in the given range
		int count = 0;// variable to store the number of items in the given
						// price range
		float sumprice = 0;// variable to calculate sum of the prices of items
							// in the given range
		for (int i = 1; i <= large; i++) {
			// System.out.println(idname.get(i));
			// System.out.println(idname.keySet());
			if (idname.containsKey(i) == false)// item with id = i not present
												// in storage
			{
				// System.out.println("Sorry key not present in the storage");
			} else {
				AvlNode t = idname.get(i);// store the node found at position in
											// idname
				String abc = " ";// string to hold the names of the item
				for (int k = 0; k < t.name.length; k++) {
					abc = abc + t.name[k].toString();// store name of the item
														// accessed
					// System.out.println("abc" + abc);
				}
				if (abc.contains(name))// item contains the given name input by
										// the user
				{
					price[(int) i] = t.price;// store price of the item in array
					// System.out.println("success"+price[(int) i]);

				}
				if (price[(int) i] >= low && price[(int) i] <= high)// price is
																	// in given
																	// range
				{
					count++;// increase count as correct item found
					sumprice = sumprice + price[(int) i];// calculate
															// appropriate price
				}
			}
		}
		// System.out.println("Number of items" + count);
		// System.out.println("final sum" + sumprice);
		return count;// return count of items in given price range containing
						// the name input by user
	}

	/**
	 * Function to increase the price of every product in given range of id low
	 * and high are also considered if an item is found with id=low or id=high
	 * 
	 * @param low
	 *            lower range of id
	 * @param high
	 *            higher range of id
	 * @param r
	 *            rate of increase of price in %
	 * @return the sum of the net increases of the prices
	 */
	public float pricehike(float low, float high, float r) {
		Set a = new HashSet();// create a new hashset to hold the keys
		a = idname.keySet();// store the keyset of idname in a
		Iterator iterator = a.iterator();// iterator to iterate over the keyset
		float pricearray[] = new float[a.size()];// array to store the net
													// increases of prices of
													// the items
		int i = 0;// counter to iterate to the next position in the array
		while (iterator.hasNext())// while there are keys left in the keyset
		{
			int b = (int) iterator.next();// iterate to the next key in the
											// keyset
			if (b > low && b <= high)// if the key is in the specified range
			{
				if (idname.containsKey(b))// and if the key is in the hashmap
				{
					AvlNode t = idname.get(b);// store the node
					float pr = t.price;// get the price of the item and store in
										// a variable
					float prs = pr + ((r * pr) / 100);// increase the price of
														// the item by r%
					float pricedifference = prs - pr;// find out the increase in
														// price
					pricearray[i] = pricedifference;// store the price in the
													// price array
					t.price = prs;// update the new price of the item
					idname.put(b, t);// put the item with the updated price in
										// the hashmap
					i++;// iterate to the next position in the array
				}
			}
		}
		float p = 0;// variable to store the sum of the net price differences
		for (int j = 0; j < pricearray.length; j++)// loop to find out the sum
													// of the net price
													// differences
		{
			p = p + pricearray[j];// calculate the sum
		}
		return p;// return the sum of the net price differences
	}

	/**
	 * Left rotation using the given node.
	 * 
	 * 
	 * @param n
	 *            The node for the rotation.
	 * 
	 * @return The root of the rotated tree.
	 */
	public AvlNode rotateLeft(AvlNode n) {

		AvlNode v = n.right;
		v.parent = n.parent;

		n.right = v.left;

		if (n.right != null) {
			n.right.parent = n;
		}

		v.left = n;
		n.parent = v;

		if (v.parent != null) {
			if (v.parent.right == n) {
				v.parent.right = v;
			} else if (v.parent.left == n) {
				v.parent.left = v;
			}
		}

		setBalance(n);
		setBalance(v);

		return v;
	}

	/**
	 * Right rotation using the given node.
	 * 
	 * @param n
	 *            The node for the rotation
	 * 
	 * @return The root of the new rotated tree.
	 */
	public AvlNode rotateRight(AvlNode n) {

		AvlNode v = n.left;
		v.parent = n.parent;

		n.left = v.right;

		if (n.left != null) {
			n.left.parent = n;
		}

		v.right = n;
		n.parent = v;

		if (v.parent != null) {
			if (v.parent.right == n) {
				v.parent.right = v;
			} else if (v.parent.left == n) {
				v.parent.left = v;
			}
		}

		setBalance(n);
		setBalance(v);

		return v;
	}

	/**
	 * 
	 * @param u
	 *            The node for the rotation.
	 * @return The root after the double rotation.
	 */
	public AvlNode doubleRotateLeftRight(AvlNode u) {
		u.left = rotateLeft(u.left);
		return rotateRight(u);
	}

	/**
	 * 
	 * @param u
	 *            The node for the rotation.
	 * @return The root after the double rotation.
	 */
	public AvlNode doubleRotateRightLeft(AvlNode u) {
		u.right = rotateRight(u.right);
		return rotateLeft(u);
	}

	/***************************** Helper Functions ************************************/

	/**
	 * Returns the successor of a given node in the tree (search recursively).
	 * 
	 * @param q
	 *            The predecessor.
	 * @return The successor of node q.
	 */
	public AvlNode successor(AvlNode q) {
		if (q.right != null) {
			AvlNode r = q.right;
			while (r.left != null) {
				r = r.left;
			}
			return r;
		} else {
			AvlNode p = q.parent;
			while (p != null && q == p.right) {
				q = p;
				p = q.parent;
			}
			return p;
		}
	}

	/**
	 * Calculating the "height" of a node.
	 * 
	 * @param cur
	 * @return The height of a node (-1, if node is not existent eg. NULL).
	 */
	private int height(AvlNode cur) {
		if (cur == null) {
			return -1;
		}
		if (cur.left == null && cur.right == null) {
			return 0;
		} else if (cur.left == null) {
			return 1 + height(cur.right);
		} else if (cur.right == null) {
			return 1 + height(cur.left);
		} else {
			return 1 + maximum(height(cur.left), height(cur.right));
		}
	}

	/**
	 * Return the maximum of two integers.
	 */
	private int maximum(int a, int b) {
		if (a >= b) {
			return a;
		} else {
			return b;
		}
	}

	/**
	 * Only for debugging purposes. Gives all information about a node.
	 * 
	 * @param n
	 *            The node to write information about.
	 */
	public void debug(AvlNode n) {
		long l = 0;
		long r = 0;
		long p = 0;
		if (n.left != null) {
			l = n.left.key;
		}
		if (n.right != null) {
			r = n.right.key;
		}
		if (n.parent != null) {
			p = n.parent.key;
		}

		// System.out.println("Left: "+l+" Key: "+n+" Right: "+r+" Parent: "+p+" Balance: "+n.balance);

		if (n.left != null) {
			debug(n.left);
		}
		if (n.right != null) {
			debug(n.right);
		}
	}

	private void setBalance(AvlNode cur) {
		cur.balance = height(cur.right) - height(cur.left);
	}

	/* Function for inorder traversal */
	public void inorder() {
		inorder(root);
	}

	private void inorder(AvlNode r) {
		if (r != null) {
			inorder(r.left);
			System.out.print(r.key + " ");
			inorder(r.right);
		}
	}

	/* Function for preorder traversal */
	public void preorder() {
		preorder(root);
	}

	private void preorder(AvlNode r) {
		if (r != null) {
			System.out.print(r.key + " ");
			preorder(r.left);
			preorder(r.right);
		}
	}

	/* Function for postorder traversal */
	public void postorder() {
		postorder(root);
	}

	private void postorder(AvlNode r) {
		if (r != null) {
			postorder(r.left);
			postorder(r.right);
			System.out.print(r.key + " ");
		}
	}

	/**
	 * Function to calculate inorder recursivly.
	 * 
	 * @param n
	 *            The current node.
	 * @param io
	 *            The list to save the inorder traversal.
	 */
	final protected void inorder(AvlNode n, ArrayList<AvlNode> io) {
		if (n == null) {
			return;
		}
		inorder(n.left, io);
		io.add(n);
		inorder(n.right, io);
	}
}

public class sns140230_P6 {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);// scanner to scan the input of
												// the user
		AvlTree avlt = new AvlTree();// access constructor of AvlTree
		HashMap<Integer, Integer> hm = avlt.getHashmap();// access hashmap in
															// AvlTree
		float resultarray[] = new float[100000000];// array to store the result
		float result = 0;// variable to store the result of each operation
		int i = 0;// iterator to iterate over the result array
		String pattern = "Insert";
		String pattern2 = "Find";
		String pattern3 = "Delete";
		String pattern4 = "FindMinPrice";
		String pattern5 = "FindMaxPrice";
		String pattern6 = "FindPriceRange";
		String pattern7 = "PriceHike";
		String patternpound = "#";
		String exitpattern = "exit";
		while (scan.hasNext())// while user is still entering data
		{
			String s1 = scan.next();// scan the next token
			if (s1.equals(patternpound))// if string is '#' its a comment
			{
				// comment - do nothing
			}
			if (s1.equals(pattern))// if insert operation
			{
				int id = Integer.parseInt(scan.next());// store id
				// System.out.println(id);
				float price = Float.parseFloat(scan.next());// store price
				String name = scan.nextLine();// store name
				name = name.substring(0, name.length() - 1);// ignore the 0 at
															// the end of the
															// insert
				result = avlt.insert(id, price, name);// store result of insert
//				 System.out.println("result" + result);
				resultarray[i] = result;// store result in result array
				i++;// increment the counter to access next position of array
			} else if (s1.equals(pattern2))// if find operation
			{
				result = avlt.find(scan.nextInt());// store result of find
				resultarray[i] = result;// store result in result array
//				System.out.println(result);
				i++;// increment the counter to access next position of array
			} else if (s1.equals(pattern3))// if delete operation
			{
				avlt.remove(scan.nextInt());// call remove function
				result = hm.get(0);// get the result of remove from the hashmap
									// hm
				resultarray[i] = result;// store result in result array
				i++;// increment the counter to access next position of array
			} else if (s1.equals(pattern4))// if find minimum price operation
			{
				result = avlt.minPrice(scan.next());// store result of minPrice
				resultarray[i] = result;// store result in result array
				i++;// increment the counter to access next position of array
			} else if (s1.equals(pattern5))// if find maximum price operation
			{
				result = avlt.maxPrice(scan.next());// store result of maxPrice
				resultarray[i] = result;// store result in resultarray
				i++;// increment the counter to access next position of array
			} else if (s1.equals(pattern6))// if price range operation
			{
				String name = scan.next();// store name
				float low = Float.parseFloat(scan.next());// store the low of
															// price range
				float high = Float.parseFloat(scan.next());// store the high of
															// price range
				result = avlt.pricerange(name, low, high);// store result of
															// price range
				resultarray[i] = result;// store result in result array
				i++;// increment the counter to access next position of array
			} else if (s1.equals(pattern7))// if price hike operation
			{
				float low = Float.parseFloat(scan.next());// store the low of id
				float high = Float.parseFloat(scan.next());// store the high of
															// id
				int r = scan.nextInt();// store the percentage increase
				result = avlt.pricehike(low, high, r);// store result of price
														// hike
				result = (float) Math.round(result * 100) / 100;// discard
																// fractional
																// pennies from
																// result
				resultarray[i] = result;// store result in result array
				i++;// increment the counter to access next position of array
			} else if (s1.equals(exitpattern))// enter exit to exit
				break;
		}

		float res = 0;
		for (int j = 0; j < i; j++) {
//			System.out.println(resultarray[j]);
			res = res + resultarray[j]; // calculate sum of results of all
										// operations
		}
		System.out.println(res);// print result

	}
}

/** Here is the AVL-Node class for Completeness **/
class AvlNode {
	public AvlNode left;
	public AvlNode right;
	public AvlNode parent;
	public int key;
	public int balance;
	public float price;
	public String name[];

	public AvlNode(int k, float price, String name[]) {
		left = right = parent = null;
		this.price = price;
		this.name = name;
		balance = 0;
		key = k;
	}

	public AvlNode(int k) {
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "" + key;
	}

}