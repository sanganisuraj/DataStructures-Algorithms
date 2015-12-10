import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Scanner;


/**
 * 
 * @author Amrita Dasgupta and Suraj Sangani | Project to do addition,
 *         subtraction, multiplication and power using linked lists. Negative
 *         numbers are not considered as a part of the input. The base of
 *         calculations is always 10. The user has to give the input as:
 *         <linenumber> variableName=Digit <linenumber> variableName=Variable1
 *         <Operator> variable2
 * 
 *         The project converts String inputs into linked lists of numbers and
 *         does the calculations based on the operator given.
 * 
 *         Functions used here are : addition (Node h1, Node h2) ==> adds h1+ h2
 *         LL; subtractLL (Node h1, Nodeh2) ==> subtracts h2 from h1 LL;
 *         multiplication(Node h1, Node h2) ==> multiplies h1 * h2; power( Node
 *         h1, Node h2) ==> calculates h1^ h2.
 * 
 *         Apart from the above, some more functions are used: StrtoNum( String
 *         s) ==> returns a linked list of the number entered as String.
 *         NumtoStr(Node h1) ==> returns the String form of the number by
 *         converting from the linked list. operate (String cmd) ==> performs
 *         operations based on the command entered as String cmd.
 *         multiplyWithOne (Node h1, int n) ==> returns the linked list
 *         containing the value of multiplying h1 LL with one single integer n.
 *         This is used by multiplication function, this gives it better
 *         re-usage.
 * 
 * 
 */
public class axd122730_P5 {

	// String used while converting LinkedLIst number to string as NumToStr
	// function uses recursion.
	static String s = "";
	// contains the line number of the input and the command as a string.
	// eg. if the user inputs 1 a=99; then 1 becomes the key and a=99 becomes
	// the value.
	static HashMap<Integer, String> linenum = new HashMap<Integer, String>();
	// contains the variable names and their values given in the user input.
	// eg. if the user enters 1 a=99; then a becomes the key and 99 becomes the
	// value.
	static HashMap<Character, String> var = new HashMap<Character, String>();
	// Linked list holder of the two operands in nodes[0] and nodes[1] and
	// nodes[2] contains
	// the result after operation
	static DataHolder<Integer>[] nodes = new DataHolder[3];

	/**
	 * Main function reads input from stdio.in if the input is 1000 then the
	 * program exits. The program checks for the ? in the input String. If it
	 * finds ? then it performs a loop based on the variable value becoming 0
	 * and going to a particular line number. Else it calls operate( String)
	 * method.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		
//		try{

			// variable to hold current line number
			int linenumVal = 0;
			// variable to hold the rest of the string after linenumber.
			String cmd;

			// read the user input
			Scanner in = new Scanner(System.in);
			while (in.hasNext()) {
				// populate the line number entered
				linenumVal = in.nextInt();
				// if line number is 1000 then break out of the loop and it will
				// exit the program.
				if (linenumVal == 1000)
					break;
				// populate the rest of the input string in cmd.
				cmd = in.next();
				// save the line number and the command in the linenum hashmap.
				linenum.put(linenumVal, cmd);
				// check for ? in the input string
				CharSequence ch = "?";
				long startT = System.currentTimeMillis();
				if (!cmd.contains(ch)) {
					// if no ? is found then call operate function with the command
					// string as input
					operate(cmd);
				} else {
					System.out.println("ch is found");
					// ? is found. This is used for loop operations.
					// this is the current line number.
					int currLine = linenumVal;
					// find the variable whose value needs to be 0 to exit the loop
					char a = cmd.charAt(0);

					// keep running the loop till char a becomes 0
					while (Integer.parseInt(var.get(a)) >= 1) {
						// this will have the goto line number.
						int gotoLine = Integer.parseInt(cmd.substring(2));
						// keep doing all the operations from goto line number to
						// current line number.
						while (gotoLine < currLine) {
							String comm = linenum.get(gotoLine);
							// operate for each command
							operate(comm);
							gotoLine++;

						}
					}

				}
				long endT = System.currentTimeMillis();
//				System.out.println("time: " + (endT - startT) + " ms.");
			}
			System.out.println("Exiting code");

//			} catch (Exception e){
//				System.out.println("Terminating program. ");
//			}
	}
	
	
	/**
	 * Function to perform the operation given in the cmd string. We check if
	 * the length of cmd is 1. If it is 1, then print the output of the last
	 * operation done or print the value of the input character.
	 * 
	 * @param cmd
	 */
	public static void operate(String cmd) {

		boolean flag = false; // flag false means the statement is an operation
								// statement else assignment statement.
		boolean opFlag1 = false; // flag to check if the statement is a self
									// assignment statement or not
		// cmd.length is greater than 1, it is an operation statement.
		if (cmd.length() > 1) {
			int check = cmd.charAt(2);
			// check if the character after = in the input is a number or a
			// character
			if (check >= 97 && check <= 122) // if character
				flag = false;
			else
				// it is a number
				flag = true;

			if (flag == true) { // it's an assignment statement
				String s = cmd.substring(2); // s is the number after = in the
												// input.
				String num = (s);
				char v = cmd.charAt(0); // variable name for the number
				var.put(v, num); // add them in the hashmap

			} else { // it is an operation
				char op = cmd.charAt(3); // get the operator
				char res = cmd.charAt(0); // get the variable name of the result
				char operand1 = cmd.charAt(2); // get operand 1
				int operand2 =0;
				if (cmd.length()>4) 
					operand2 = cmd.charAt(4);// get operand 2
				if (operand1 == res) {
					// System.out.println("self assignment");
					opFlag1 = true;
				}
				// get value of operand 1 value from var hashmap with key as
				// operand 1 and convert into LL.
				DataHolder<Integer> h1;
				if (var.get(operand1).charAt(0) == '-'){
					h1 = StrtoNum(var.get(operand1).substring(1), true);
				}
				else{
					h1 = StrtoNum(var.get(operand1).substring(0), false);
				}
				
				DataHolder<Integer> h2 = null;
				// if operand2 is a character, get its value from hashmap var
				// else it is a number input from the user.
				if (operand2!= 0){
					if (operand2 >= 97 && operand2 <= 122) {
						String str = var.get(cmd.charAt(4));
						if (str.charAt(0) == '-'){
							h2 = StrtoNum(str.substring(1), true);// convert to LL
						}
						else{
							h2 =  StrtoNum(str.substring(0), false);
						}
					} else {// convert the input digit into LL
						String str = cmd.substring(4);
						if (str.charAt(0) == '-')
							h2 = StrtoNum(str.substring(1), true);// convert to LL
						else
							h2 =  StrtoNum(str.substring(0), false);
					}
				}
				DataHolder<Integer> h3 = null;// holder for result

				// pointers to hold both the operands
				nodes[0] = h1;
				nodes[1] = h2;
				// switch case for operator
				switch (op) {

				case '+':
					h3 = adding(h1, h2);
					break;

				case '-':
					h3 = subtracting(h1, h2);
					break;

				case '*':
					h3 = multiplication(h1, h2);
					break;

				case '^':
					h3 = power(h1, h2);
					break;
					
				case '/':
					h3 = division(h1, h2, false);
					break;
				
				case '%':
					h3 = division(h1, h2, true);
					break;
				
				case '~':
					h3 = squareRoot(new DataHolder<Integer>(new Node<Integer>(0), false), h1, h1);
					display(h3.node);
					break;
				
				case ')':
					h3 = maxPower(h1, new DataHolder<Integer> (new Node<Integer>(2), false));
				}
				nodes[2] = h3; // save the result in nodes[2]

				s = "";// Static string to be used for converting LL to String
				if (opFlag1 == true) {// Input is a Self Assignment Statement
					// updating the value of existing operator
					if (h3.negative)
						var.put(operand1, "-"+ NumtoStr(h3.node));
					else 
						var.put(operand1,  NumtoStr(h3.node));
					s="";
				}
				else{
					if (h3.negative)
						var.put(cmd.charAt(0), "-"+ NumtoStr(h3.node));
					else
						var.put(cmd.charAt(0), NumtoStr(h3.node));
					s="";
				}
				opFlag1 = false;

			}
		} else if (cmd.length() == 1) { // this is for printing output
			s = "";
			if (var.containsKey(cmd.charAt(0))) {// if it was a self assignment
													// statement get the updated
													// value.
				System.out.println(var.get(cmd.charAt(0)));
			} else{
				// print the result of last operation
				
				if (nodes[2].negative)
					System.out.println("-"+ NumtoStr(nodes[2].node));
				else
					System.out.println(NumtoStr(nodes[2].node));
			}
				
		}
	}
	
	
	/**
	 * This is for recursively calculating the squareroot of a number 
	 * @param low
	 * @param high - value of number
	 * @param n - value of number
	 * @return the linked list representing the number
	 */
	public static DataHolder<Integer> squareRoot(DataHolder<Integer> low, DataHolder<Integer> high, DataHolder<Integer> n)
	{	
		//if number is 0 return 0
		if(n.node.data == 0 && n.node.next == null) 
			return new DataHolder<Integer>(new Node<Integer>(0), false);
		//if number is 1 return 1
		if (n.node.data == 1 && n.node.next == null)
			return new DataHolder<Integer>(new Node<Integer>(1), false);
		//square root of negative number is not possible
		if (n.negative)
			return null;
		Node <Integer> midH = null; //LL to store the (low+high)/2
		//Calculate the value of midH
		midH =	addition(new DataHolder<Integer>(low.node, false), new DataHolder<Integer>(high.node, false)).node;
		midH.prev = null; //previous value of midH should be null
		Node <Integer> two = new Node<Integer>(2); //LL to store the node with value 2
		//calculate mid=(midH/2)
		Node <Integer> mid = division(new DataHolder<Integer>(midH, false),
				new DataHolder<Integer>(two , false), false).node;
		//if high>low
		if (GreaterThan(high.node, low.node)){
			//if low==mid or mid==high then we have reached the end and return mid
			if (EqualTo(low.node, mid)== true ||  EqualTo(mid, high.node) ==true){
				return new DataHolder<Integer> (mid, false);
			}
			//calculate pow=mid*mid
			Node<Integer> pow = multiplication(new DataHolder<Integer> (mid, false),
					new DataHolder<Integer> (mid, false)).node;
			//if pow is greater than the number
			if (GreaterThan(pow, n.node)){
				//recursively call square root as square root will lie in (low,mid) and pass(low, mid, n)
				return squareRoot(low, new DataHolder<Integer> (mid, false), n);
			}
			else {
				//recursively call square root as square root will lie in (mid, n) and pass(mid, high, n)
				return squareRoot(new DataHolder<Integer> (mid, false), high, n);
			}
		}
		
		return new DataHolder<Integer> (mid, false); //return the linked list and positive flag
	}
	/**
	 * Function to calculate the division/modulus of two numbers represented by Linked Lists
	 * @param h1
	 * @param h2
	 * @param mod is the parameter to check whether we are performing division or modulus
	 * @return modulus if mod = true and quotient if mod = false
	 */
	
	public static DataHolder<Integer> division(DataHolder<Integer> h1, DataHolder<Integer> h2,
			boolean mod) {
		//base cases
		if (h1.node == null || h2.node == null) //if h1 and h2 are null return null
			return null;
		if (h2.node.next == null && h2.node.data == 0) { //cannot divide with 0

			return null;
		}
		boolean flag = false; //flag to check if h1 and h2 represent positive or negative numbers
		if (h2.node.next == null && h2.node.data ==1) return h1; //division by 1 gives answer 1
		if (h1.negative == h2.negative) flag = false; //division of 2 negative numbers gives a positive result
		else flag = true; //if signs of numerator and denominator are different result will be negative
		Node<Integer> p1 = h1.node, p2 = h2.node;//pointers to h1 and h2
		Node<Integer> result = new Node<Integer>(100);//100 is a dummy value. We will print result from result.next
		Node<Integer> resTail = result;//pointers to tail of the result
		Node<Integer> p3 = null; //p3 is a temporary pointer to keep track of intermediate subtractions and hold the prev element of p1
		Node<Integer> dummy = null;//dummy node to move the pointers
		while (p1.next != null) {//iterate to the end of p1 because division starts from the MSB
			p1 = p1.next;
		}
		
		p3 = new Node<Integer>(p1.data);//p3 points to the MSB of p1
		boolean b = false;//flag to check if one LL is greater than the other LL
		boolean c = false;//flag to check if one LL is smaller than the other LL
		int k =5;
		while (p1 != null && p1.prev != null) {//while p1 has data left
//		while (k>=0){
			while (true) {
				b = GreaterThan(p3, p2); //is p3>p2?
				c = EqualTo(p3, p2);//is p3=p2?
				if (b == false && c == false) // if p3 is less than p2
				{
					if (p1 == null) //we are at the end of the dividend
						break;
					if (p1.prev != null) { //p1 still has elements left
						if (p3 == null){ 
							if (p1.prev.data != 0) //check if p1 has data left which is not 0
								p3 = new Node<Integer>(p1.prev.data); //add the leftover element of p1 to p3
							
						}
						else 
							p3.prev = new Node<Integer>(p1.prev.data);
						if (p3!= null && p3.prev != null) {//if p3 has elements make sure p3 has prev and next pointers
							Node<Integer> next = p3.prev;
							next.next = p3;
							p3 = p3.prev;
						}
						
					}
					
					if (GreaterThan(p2, p3)){//if p2 is greater than p3
						if (result != resTail){ //if result is not at the tail
							if (p1!= null  && p1.prev != null){ //if p1 still has elements left
								result.prev = new Node<Integer> (0); //add a 0 to the result
								//move result to result.prev
								dummy = result;
								result = result.prev;
								result.next = dummy;
							}
						}
					}
					p1 = p1.prev; //move p1 to previous node
				} else {//since p3>p2 proceed to division
					break;
				}

			}
			Node<Integer> prod;
			int count = 1;
			while (count < 10 && p3!= null) {
				if (GreaterThan(p2, p3)) break; //if p2>p3 break out of this loop
				prod = multiplyWithOne(new DataHolder<Integer>(p2, false), count).node;//multiply p2*count
				boolean big = GreaterThan(prod, p3); //flag to check if current product is greater than p3
				boolean eq = EqualTo(prod, p3);//flag to check if current product is equal to p3
				
				if (big == true || eq == true) { //if big or eq is true we have found correct quotient
					if (!eq) {//if current product is greater than the dividend
						count--;//quotient should have one less count
						result.prev = new Node<Integer>(count);//store the count
						dummy = result;//dummy to move the result
						result = result.prev;
						result.next = dummy;
						prod = multiplyWithOne(new DataHolder<Integer>(p2, false), count).node; //find correct product
						p3 = subtractLL(new DataHolder<Integer>(p3, false), //subtract p3-prod to find out remainder
								new DataHolder<Integer>(prod, false)).node;
						if (p3.next == null && p3.data ==0) p3= null;//make p3 null if p3 =0
						break;
					} else {//found the correct quotient
						result.prev = new Node<Integer>(count);//store the correct count
						dummy = result;//dummy to move the result
						result = result.prev;
						result.next = dummy;
						p3.next = null;
						p3.prev = null;
						p3 = null;//make p3 null
						break;
					}
				}
				count++;//increment count by 1 for next iteration
				if (count == 10 && resTail.data == 100) {//when count becomes 10 and result has 100(dummy value)
					count--; //incorrect count so decrease by 1
					result.prev = new Node<Integer>(count);//store the correct count
					dummy = result;
					result = result.prev;
					result.next = dummy;//dummy to move the result
					prod = multiplyWithOne(new DataHolder<Integer>(p2, false), count).node;//find the correct product
					p3 = subtractLL(new DataHolder<Integer>(p3, false), 
							new DataHolder<Integer>(prod, false)).node;//compute p3-prod to find correct remainder
					break;	
				}	
			}
		}
		//division for single digit
		if (p1 != null && p1.prev == null && p1.next == null) {
			b = GreaterThan(p3, p2);//is p3>p2?
			c = EqualTo(p3, p2);//is p3=p2?
			if (b && !c) {
				//if p3>p2
				Node<Integer> prod;
				int count = 1;
				while (count < 10) {
					prod = multiplyWithOne((new DataHolder<Integer>(p2, false)), count).node;//do prod=p2*count
					boolean big = GreaterThan(prod, p3);//is prod>p3
					boolean eq = EqualTo(prod, p3);//is prod=p3?

					if (big == true || eq == true) { //found correct quotient
						if (!eq) {//if not equal
							count--;//incorrect count so decrease by 1
							result.prev = new Node<Integer>(count);//store count
							dummy = result;
							result = result.prev;
							result.next = dummy;//dummy node to move result
							prod = multiplyWithOne((new DataHolder<Integer>(p2, false)), count).node;//find correct prod
							p3 = subtractLL(new DataHolder<Integer>(p3, false), 
									new DataHolder<Integer>(prod, false)).node;//p3-prod to cpmpute correct remainder
							break;
						} else {//prod = p3
							result.prev = new Node<Integer>(count);//found correct count, store it
							dummy = result;
							result = result.prev;
							result.next = dummy;//dummy to move result
							p3.next = null;
							p3.prev = null;
							p3 = null;//make p3 null
							break;
						}
					}

					count++;//increment count for next iteration

				}
			} else if (!b && c) {// its equal
				if (!mod)
					return new DataHolder<Integer>(new Node<Integer>(1), flag);
				else
					return new DataHolder<Integer>(new Node<Integer>(0), false);
			} else if (!b && !c) { // dividend is smaller

				if (!mod)
					return new DataHolder<Integer>(new Node<Integer>(0), false);
				else
					return h1;
			}
		}

		if (!mod) {
			resTail = resTail.prev;
			resTail.next = null;
			return new DataHolder<Integer>(result, flag); // return result
		} else {
			if (p3 != null)
				return new DataHolder<Integer>(p3, false);//return remainder
			else
				return new DataHolder<Integer>(new Node<Integer>(0), false);
		}

	}
	
	
	/**
	 * This is to calculate L1 to the power of L2
	 * @param h1
	 * @param h2
	 * @return the linked list corresponding to L1^L2
	 */
	
	public static DataHolder<Integer> power(DataHolder<Integer> h1, DataHolder<Integer> h2) {
		// handle for if one of them is null cases
		if (h2.node == null)
			return null;
		if (h1.node == null)
			return null;
		// if h2 has only 0 as data, then we can skip the calculation process
		// and return 1.
		if (h2.node.data == 0 && h2.node.next == null)
			return new DataHolder<Integer>(new Node<Integer>(1), false);
		//if h1 has only 1 as data, then return 0
		if (h1.node.data == 0 && h1.node.next == null)
			return new DataHolder<Integer>(new Node<Integer>(0), false);
		
		if (h2.negative) 
			return new DataHolder<Integer>(new Node<Integer>(0), false);
		// store the two head pointers in different variable for manipulation
		DataHolder<Integer>p2 = h2;
		Node <Integer> p1 = h1.node;
		// node to do the subtraction of h2 by one
		DataHolder<Integer> one = new DataHolder<Integer>(new Node<Integer>(1), false);
		// node to keep the product value everytime.
		Node<Integer> temp = h1.node;
		while (p2.node.next != null || p2.node.data != 1) {
			// subtracts p2 value by 1
			p2 = subtractLL(p2, one);
			// multiply p1 with itself.
			temp = multiplication(new DataHolder<Integer>(temp, false), 
					new DataHolder<Integer>(p1, false)).node;
		}
		// return the result along with the flag value of h1
		DataHolder<Integer> res = new DataHolder<Integer>(temp, false);
		if (!EqualTo(division(h2, new DataHolder<Integer> (new Node<Integer> (2), false), true).node, 
				(new Node<Integer>(0)))){
			res.negative = h1.negative;
		}				
		return res;
	}
	
	public static DataHolder<Integer> maxPower(DataHolder <Integer> h1, DataHolder<Integer> h2){
		
		long startTime = System.currentTimeMillis();
		// handle for if one of them is null cases
				if (h2.node == null)
					return null;
				if (h1.node == null)
					return null;
				// if h2 has only 0 as data, then we can skip the calculation process
				// and return 1.
				if (h2.node.data == 0 && h2.node.next == null)
					return new DataHolder<Integer>(new Node<Integer>(1), false);
				if (h1.node.data == 0 && h1.node.next == null)
					return new DataHolder<Integer>(new Node<Integer>(0), false);
				
				while (System.currentTimeMillis() < (startTime + 15000)){
					h1 = power(h1, h2);
				}
				
				return h1;
	}
	
	/**
	 * This is for multiplication of a LL with single digit
	 * 
	 * @param head
	 * @param n
	 * @return the head of the resultant LL
	 */
	public static DataHolder<Integer> multiplyWithOne(DataHolder<Integer> head, int n) {
		// handle base cases
		if (head.node == null)
			return null;
		Node<Integer> curr = head.node;
		DataHolder<Integer> result = new DataHolder<Integer>(new Node<Integer>(100), false);// LL to store result

		int carry = 0;
		int prod = 0; // intermediate product
		int val = 0; // dummy variable to be added in the LL
		while (curr != null) {
			prod = (n * curr.data) + carry; // get the product of n and current
											// data and add with carry

			carry = 0; // carry made zero as value is used in the previous line
			if (prod >= 10) // there will be a new carry
			{
				carry = prod / 10;
				val = prod % 10; // get the digit to be added in the LL
				add(result, val);
				prod = 0;
			} else {// there is no carry
				add(result, prod);
				prod = 0;
			}
			curr = curr.next;

		}
		// if curr has ended and there is a carry leftover, add it to the result
		// LL
		if (carry > 0) {
			add(result, carry);
		}

		result.node = result.node.next;
		return result; // result.data contains dummy data which is not part
							// of the result

	}
	
	/**
	 * Function returns the multiplication of h1,h2
	 * 
	 * @param h1
	 * @param h2
	 * @return the head of the LL containing result
	 */
	public static DataHolder<Integer> multiplication(DataHolder<Integer> h1,
			DataHolder<Integer> h2) {
		// handle base cases
		if (h1.node == null || h2.node == null)
			return null;
		DataHolder<Integer> p1 = h1, p2 = h2;
		DataHolder<Integer> result = new DataHolder<Integer> (null, false);// LL for result

		DataHolder<Integer> r1 = new DataHolder<Integer>(new Node<Integer>(null), false); // LL for intermediate
													// result
		Node<Integer> rHead = r1.node;// Keep the head pointed to r1's head.

		int count = 0; // count the number of zeroes to be appended. Increases
						// after each iteration.

		while (p2.node != null) {
			// append the zeroes upto count times to r1
			for (int i = 0; i < count; i++) {
				r1.node.next = new Node<Integer>(0);
				r1.node = r1.node.next;
			}
			if (count > 0)
				rHead = rHead.next;	

			r1.node.next = multiplyWithOne(p1, p2.node.data).node;// get the result of
													// multiplication with each
													// integer in p2 with p1
			
			if (result.node != null) {				
				result = addition(new DataHolder<Integer> (rHead, false), result);// if intermediate result is
													// not null, add the
													// previous
													// intermediate result to
													// the new multiplication
													// result
			} else
				// this is the first case when the intermediate result is null
				result.node = rHead.next;
			rHead.next = null; // delete rhead's data
			rHead.data = null;
			// rHead again points to r1's head.
			r1.node = rHead;
			// move p2 to its next value
			p2.node = p2.node.next;
			// increment count for the next iteration.
			count++;
		}
//		result.node = result.node.next;
		if (h1.negative == h2.negative) result.negative = false;
		else result.negative = true;
		return result;

	}
	/**
	 * This checks for the sign of the two numbers and calculates the subtraction accordingly
	 * @param h1
	 * @param h2
	 * @return the LL which contains the result after subtraction
	 */
	public static DataHolder<Integer> subtracting (DataHolder<Integer> h1, DataHolder<Integer> h2){
		DataHolder<Integer> res = null; //Node to hold the result
		boolean flag = false; //flag to check if result is positive or negative
		//if h1 is positive and h2 is negative the resulting operation will be addition
		if (!h1.negative && h2.negative){
			res = addition(h1, h2);
			res.negative = false; //result will be positive
		}
		//if h1 is negative and h2 is positive the resulting operation will be addition
		else if (h1.negative && !h2.negative){
			res = addition(h1, h2);
			res.negative = true; //result will be negative
		}
		//if both h1 and h2 are positive we need to check which one is greater to compute subtraction
		else if (!h1.negative && !h2.negative){
			if (GreaterThan(h1.node, h2.node)){ //if h1 is greater than h2
				res = subtractLL(h1, h2); //compute h1-h2
				res.negative = false; //result will be positive
			}
			else{//h1 is smaller than h2
				res = subtractLL(h2, h1); //compute h2-h1
				res.negative = true; // result will be negative
			}
		}
		//if both h1 and h2 are negative we need to check which one is greater to compute subtraction
		else{
			if (GreaterThan(h1.node, h2.node)){//if h1 is greater than h2
				res = subtractLL(h1, h2);//compute h1-h2
				res.negative = true;//result will be negative
			}
			else{//h1 is smaller than h2
				res = subtractLL(h2, h1);//compute h2-h1
				res.negative = false;//result will be positive
			}
		}
		return res; //return the result
	}
	
	
	public static DataHolder<Integer> adding (DataHolder<Integer> h1, DataHolder<Integer> h2){
		
		DataHolder<Integer> res = null;
		boolean flag = false;
		if (h1.isNegative() && h2.isNegative()){
			flag = true;
			res = addition(h1, h2);
			res.negative =flag;
		}
		else if (!h1.isNegative() && !h2.isNegative()){
			flag = false;
			res = addition(h1, h2);
			res.negative =flag;
		}
		else{
			
			if (GreaterThan(h1.node, h2.node)){
				res = subtractLL(h1, h2);
				res.negative = h1.negative;
			}
			else{
				res = subtractLL(h2, h1);
				res.negative = h2.negative;
			}
			
		}
		
		return res;
	}

	/**
	 * function to calculate if h1 is greater than h2.
	 * 
	 * @param h1
	 * @param h2
	 * @return true if h1 is greater than h2. Else return false
	 */

	public static boolean GreaterThan(Node<Integer> h1, Node<Integer> h2) {
		//base cases
		if (h1 == null && h2 == null) // if both are null return true
			return true;
		if (h1 == null) //since h1 is null h2 is bigger than h1
			return false;
		if (h2 == null)// since h2 is null h1 is bigger than h2
			return true;
		Node<Integer> p1 = h1; //pointer to head of h1
		Node<Integer> p2 = h2; //pointer to head of h2

		//iterate to the end of h1 and h2
		while (p1.next != null && p2.next != null) {
			p1 = p1.next;
			p2 = p2.next;
		}
		
		//p1 still has elements left which means h1 is bigger than h2
		if (p1.next != null && p2.next == null)
			return true;

		//p2 still has elements left which means h2 is bigger than h1
		if (p1.next == null && p2.next != null)
			return false;

		//if p1 and p2 data are equal iterate to the previous elements to check which is bigger
		while ((p1.data == p2.data) && p1.prev != null && p2.prev != null) {
			p1 = p1.prev;
			p2 = p2.prev;
		}
		//if p2 is bigger
		if (p2.data > p1.data) {
			return false;
		} else if (p1.data > p2.data) //if p1 is bigger
			return true;

		return false; //return false
	}

	/**
	 * Function to check if L1=L2
	 * @param h1
	 * @param h2
	 * @return true if L1 = L2 else return false
	 */
	public static boolean EqualTo(Node<Integer> h1, Node<Integer> h2) {
		//base cases
		if (h1 == null && h2 == null) // if both are null L1=L2
			return true;
		if (h1 == null) //Since L1 is null and L2 is not L1!=L2
			return false;
		if (h2 == null)//Since L2 is null and L1 is not L1!=L2
			return true;
		Node<Integer> p1 = h1;//pointer to h1
		Node<Integer> p2 = h2;//pointer to h1
		//Iterate to the end of the lists
		while (p1.next != null && p2.next != null) {
			p1 = p1.next;
			p2 = p2.next;
		}

		//if p1 still has elements left p1!=p2
		if (p1.next != null && p2.next == null)
			return false;

		//if p2 still has elements left p1!=p2
		if (p1.next == null && p2.next != null)
			return false;

		//if p1 and p2 have the same data iterate to the previous nodes of the list
		while ((p1.data == p2.data) && p1.prev != null && p2.prev != null) {
			p1 = p1.prev;
			p2 = p2.prev;
		}
		 
		if (p2.data > p1.data) {
			return false; //p2 is not equal to p1
		} else if (p1.data > p2.data)
			return false;//p2 is not equal to p1

		return true; //p2 is equal to p1 so return true
	}

	/**
	 * function to subtract h2 from h1 linked list. If the number represented by
	 * h2 is bigger than h1 then, this return 0, as this does not handle
	 * negative results. input numbers also cannot be negative.
	 * 
	 * @param h1
	 * @param h2
	 * @return result linked list head.
	 */
	public static DataHolder<Integer> subtractLL(DataHolder<Integer> h1, DataHolder<Integer> h2) {

		// handle base cases.
		if (h1.node == null)
			return new DataHolder<Integer>(new Node<Integer>(0), false);
		// h1- null returns h1.
		if (h2.node == null)
			return h1;
		if (h2.node.next == null && h2.node.data == 0) return h1;
		
		
		Node<Integer> p1 = h1.node, p2 = h2.node;
		// holds the result linked list.
		Node<Integer> result = new Node<Integer>(null);
		// carry used to hold the current carry value.
		int carry = 0;
		// holds the current subtracted value
		int sub = 0;
		// dummy variable
		int dum = 0;

		// pointer for the tail of the result linked list. makes it easier to
		// add a new node
		// for each calculation
		Node<Integer> resTail = result;
		Node<Integer> prev = resTail;
		// by default p1 size should be the bigger one.
		while (p1 != null) {

			dum = p1.data - carry;
			carry = 0;
			// if the dum value is less than p2.data, then carry becomes 1
			// again.
			if (dum < p2.data) {
				sub = dum + 10 - p2.data;
				carry = 1;
			} else
				// no new carry
				sub = dum - p2.data;

			// add the sub value in the result LL.
			resTail.next = new Node<Integer>(sub);

			// move the tail pointer to next. This should always point at the
			// last node.
			prev = resTail;
			resTail = resTail.next;
			resTail.prev = prev;

			// if carry is not 0 but p1 and p2 have reached their ends, then p2
			// value> p1 value. Therefore,
			// return 0.
			// if (p1.next == null && p2.next == null && carry != 0)
			// return new Node<Integer>(0);
			// if p1 has ended but p2 hasn't then return 0. As this means p2>
			// p1.
			// if (p1.next == null && p2.next != null)
			// return new Node<Integer>(0);

			// move p1 and p2 to their next values.
			p1 = p1.next;
			if (p2.next == null)
				break;

			p2 = p2.next;
		}

		// if p2 has ended, but p1 still has digits left
		while (p1 != null) {
			// do the subtraction with the left over carry while running through
			// p1.
			if ((p1.data - carry) < 0) {
				dum = p1.data + 10 - carry;
				resTail.next = new Node<Integer>(dum);
				prev = resTail;
				resTail = resTail.next;
				resTail.prev = prev;
				carry = 1;
			} else {
				if (p1.data - carry != 0) {
					resTail.next = new Node<Integer>(p1.data - carry);
					prev = resTail;
					resTail = resTail.next;
					resTail.prev = prev;
				}
				carry = 0;
			}
			p1 = p1.next;
		}

		// return the result. Result's first node has null value, therefore,
		// result.next.

		while (resTail.data == 0) {
			if (resTail.data == 0) {
				resTail = resTail.prev;

			}
		}
		resTail.next = null;
		result = result.next;
		return new DataHolder<Integer>(result, false);
	}

	/**
	 * Function to perform the operation given in the cmd string. We check if
	 * the length of cmd is 1. If it is 1, then print the output of the last
	 * operation done or print the value of the input character.
	 * 
	 * @param cmd
	 */
	

	/**
	 * Function to add new node at the end of the LL
	 * 
	 * @param head
	 * @param item
	 */
	public static void add(DataHolder<Integer> head, int item) {
		Node<Integer> n = new Node<Integer>(item);
		// System.out.println("new item is =" + item);
		Node<Integer> curr = head.node;
		while (curr.getNext() != null) {
			curr = curr.getNext();
		}
		curr.setNext(n);
		n.prev = curr;

	}

	/**
	 * Function to display LL
	 * 
	 * @param head
	 */
	public static void display(Node<Integer> head) {
		Node<Integer> curr = head;

		while (curr != null) {
			String arrow;
			if (curr.getNext() != null) {
				arrow = " --> ";
			} else {
				arrow = " ";
			}
			System.out.print(curr.getData() + arrow);

			curr = curr.getNext();
		}
		System.out.println("\n");
	}

	/**
	 * Function to convert String containing a number to a LL Each node contains
	 * one digit
	 * 
	 * @param s
	 * @return the head of the LL
	 */
	public static DataHolder<Integer> StrtoNum(String s, boolean isNeg) {
		char[] c = s.toCharArray();

		DataHolder<Integer> head = new DataHolder<Integer>(
				new Node <Integer>(Character.getNumericValue(c[c.length - 1])), isNeg);

		// System.out.println("head data is " + head.data);
		int i = c.length - 2;
		while (i >= 0) {
			// System.out.println(Character.getNumericValue(c[i]));
			add(head, Character.getNumericValue(c[i]));
			i--;

		}
		return head;

	}

	/**
	 * Function to add two numbers represented by h1, h2
	 * 
	 * @param h1
	 * @param h2
	 * @return the head of the result
	 */
	public static DataHolder<Integer> addition(DataHolder<Integer> h1, DataHolder<Integer> h2) {
		// handle base cases
		if (h1.node == null)
			return h2;
		if (h2.node == null)
			return h1;

		DataHolder<Integer> result = new DataHolder<Integer>(new Node<Integer>(null), true);// LL to store result
		int carry = 0;
		int sum = 0; // variable to hold the intermediate sum
		while (h1.node != null && h2.node != null) {// while one of the LL hasn't ended,
											// run the loop
			sum = (int) h1.node.data + (int) h2.node.data + carry;
			carry = 0; // carry has been used in the previous statement

			if (sum >= 10) {// there is a new carry
				carry = 1;
				int val = sum % 10;// get the LSB of sum
				add(result, val);
				sum = 0;
			} else {// there is no carry, add directly to the new LL
				add(result, sum);
				sum = 0;
			}
			h1.node = h1.node.getNext();
			h2.node = h2.node.getNext();
		}
		if (h1.node == null) { // size of h1<h2

			while (h2.node != null) {// run through h2
				sum = (int) h2.node.data + carry; // add the leftover carry
				carry = 0;
				if (sum >= 10) {// there is a new carry
					carry = 1;
					int val = sum % 10;
					add(result, val);
					sum = 0;
				} else {// there is no carry
					add(result, sum);
					sum = 0;
				}
				h2.node = h2.node.getNext();
			}
		}
		if (h2.node == null) {// size of h2<h1
			while (h1.node != null) {// run through h1
				sum = (int) h1.node.data + carry;// add the leftover carry
				carry = 0;
				if (sum >= 10) {// there is a new carry
					carry = 1;
					int val = sum % 10;
					add(result, val);
					sum = 0;
				} else {// there is no carry
					add(result, sum);
					sum = 0;
				}
				h1.node = h1.node.getNext();
			}
		}
		if (carry > 0) // h1 and h2 both have ended, and there is still a carry
			add(result, carry); // add it to the result
		result.node = result.node.next;
		return result; // result.data is a dummy value, not related to the
							// operation
	}

	/**
	 * Function to convert LL into String
	 * 
	 * @param head
	 * @return the String
	 */
	public static String NumtoStr(Node<Integer> head) {
		StringBuilder s = new StringBuilder();
		Node<Integer> curr = head;
		curr.prev = null;
		if (curr == null)
			return ""; // LL is empty
		while (curr.getNext() != null) {
			curr = curr.getNext(); 
		}
		
		
		while (curr != null){
			s.append(curr.data);	
			curr = curr.prev;
			
		}
		return s.toString();
	}

	/**
	 * 
	 * Utility Class to define a Node of generic type E
	 *
	 * @param <E>
	 */
	public static class Node<E> {
		Node<E> next;
		Node<E> prev;
		E data;

		public Node(E datavalue) {
			next = null;
			data = datavalue;
			prev = null;
		}

		public Node(E datavalue, Node<E> nextvalue, Node<E> prevvalue) {
			next = nextvalue;
			data = datavalue;
			prev = prevvalue;
		}

		public E getData() {
			return data;
		}

		public void setData(E Datavalue) {
			data = Datavalue;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> nextValue) {
			next = nextValue;
		}
	}
	
	public static class DataHolder<E> {
		Node <E> node;
		boolean negative;
		
		public DataHolder(Node<E> node, boolean negative){
			this.node = node;
			this.negative = negative;
		}
		
		public Node<E> getNode() {
			return node;
		}
		public void setNode(Node<E> node) {
			this.node = node;
		}
		public boolean isNegative() {
			return negative;
		}
		public void setNegative(boolean negative) {
			this.negative = negative;
		}
		
		
	}

}