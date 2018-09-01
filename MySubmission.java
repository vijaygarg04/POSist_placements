import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MySubmission {
static int no;
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");

public static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		 no++; 
		 Node gen = createGenesisNode(no);
		 no++;
		Node child= addChildNode(1,gen,no,gen);
		no++;
		Node child2= addChildNode(2,child,no,gen);
		
		encrypt(child);
		
		encrypt(child2);
		
		boolean result=decrypt(child, "Vijay Garg");
		
		if(result==true) {
			
			System.out.println("Node Decrypted Successfully");
		}
		 
		 
	}
	
	//Task 1 Completed Here
	public static Node createGenesisNode(int node_number){

		   Node n = new Node() ;
		   
		   n.time_now =new Date();
		   // Get current timestamp

		   n.nodeNumber = node_number;
		   n.nodeId = node_number+"";
		   System.out.println("Enter DATA -> OwnerId -> value -> Name");
		   n.data=new Data();
		   n.data.ownerId=Integer.parseInt(scanner.nextLine());
		   n.data.value=Float.parseFloat(scanner.nextLine());
		   n.data.owner_name=scanner.nextLine();

		   n.referenceNodeid = null;
		   n.genesisReferenceNodeid = n;   // genesis node is created update address.
		   n.HashValue = new HashSet();
		   n.HashValue.add(n);
		   n.sumofchildsofar=0;

		 return n;                  // return address of genesis node for future purposes.
		}
	
	
	//Task 2 & 3 Completed Here
	public static Node addChildNode(int node_number,Node parent,int current_node_number,Node genesisnode){

	    
	    
	       Node p = new Node();

	         p.nodeNumber = current_node_number;
	         p.nodeId = node_number+"";
	            System.out.println("Enter DATA -> OwnerId -> value -> Name");
	            p.data=new Data();
	        p.data.ownerId=Integer.parseInt(scanner.nextLine());
	        p.data.value=Float.parseFloat(scanner.nextLine());
	        p.data.owner_name=scanner.nextLine();
	        while(p.data.value>parent.data.value-parent.sumofchildsofar) {
	        	System.out.println("Hey You have not followed the tree property please enter again its value");
	        	p.data.value=Float.parseFloat(scanner.nextLine());
	        }
	        p.referenceNodeid = parent ;      //This node's parent address will be &l
	        p.genesisReferenceNodeid = genesisnode;   //copying genesis node address we received.
	        p.HashValue = new HashSet();
			p.HashValue.add(p);
			parent.sumofchildsofar=parent.sumofchildsofar+p.data.value;
			   return p;
	       
	}
	
	//Task 3 & Task 4 
	public static void encrypt(Node n){

	    n.isEncrypted = true;
	    System.out.print("Enter owner password");
	    n.password=scanner.nextLine();

	    //encrypt by adding 13 to change ASCII value of character.
	    for(int i = 0; (i < 100 && i< n.password.length() ); i++) {
	    	char newchar=(char) ((int)n.password.charAt(i) + 13);
	        n.password=n.password.substring(0,i)+newchar+n.password.substring(i+1); 
	    }


	}
	
	//Task 3 & Task 4
	public static boolean decrypt(Node given_node,String pass){

		//decrypt by subtracting 13 to change ASCII value of character.
        for(int i = 0; (i < 100 && i < pass.length()); i++) {
        	char newchar=(char) ((int)pass.charAt(i) - 13);
	        pass=pass.substring(0,i)+newchar+pass.substring(i+1); 
        }
        

        if(given_node.password == pass){
            return true;
        }
        else{
            return false;
        }
}
	//task7
	public static void change_owner(Node given_node){

	    String pass1,pass2;
	    System.out.println("Enter Owner one password");
	    
	    pass1=scanner.nextLine();
	    if(decrypt(given_node,pass1)){
	        System.out.println("Ownership of Node will be changed,enter new owner id");
	        given_node.data.owner_name=scanner.nextLine();
	        encrypt(given_node);
	    }
	}
	
	//task 8 longest chain of genesis code
	public static int longest_chainofgenesisnode(Node node){
		int h = -1;

		for (Node child : node.childReferenceNodeid) {
			int ch = longest_chainofgenesisnode(child);
			if (ch > h) {
				h = ch;
			}
		}

		return h + 1; // for the distance between node and children
	
	    //This can be solved by applying DFS on the genesis node,i.e. the root node or any other node
	    //whose address is given by using child node pointers array.
	}
	
	//task 9 
	public static int diameter(Node ptr)
	{
	    // Base case
	    if (ptr== null)
	        return 0;
	 
	    // Find top two highest children
	    int max1 = 0, max2 = 0;
	    for (Node node:ptr.childReferenceNodeid)
	    {
	        int h = depthOfTree(node);
	        if (h > max1) {
	           max2 = max1; 
	           max1 = h;
	        }else if (h > max2)
	           max2 = h;
	    }
	 
	    // Iterate over each child for diameter
	    int maxChildDia = 0;
	    for (Node node : ptr.childReferenceNodeid)
	        maxChildDia = Math.max(maxChildDia, diameter(node));
	 
	    return Math.max(maxChildDia, max1 + max2 + 1);
	}
	
	//SubFuntion To get depth of my tree
	private static int depthOfTree(Node ptr)
	{
	    // Base case
	    if (ptr== null)
	        return 0;
	 
	    int maxdepth = 0;
	 
	    // Check for all children and find
	    // the maximum depth
	    for (Node node :ptr.childReferenceNodeid)
	 
	        maxdepth = Math.max(maxdepth , depthOfTree(node));
	 
	    return maxdepth + 1;
	}
	
	//task 10
	public static void merge_node(Node node1, Node node2){
	    //Add Data of both nodes.
		float finalvalue=node1.data.value + node2.data.value; 
	    // call function diameter to calculate the longer chain from two.
		
		int diameter1=longest_chainofgenesisnode(node1);
		int diameter2=longest_chainofgenesisnode(node2);
		if(diameter1>diameter2) {
			
			node1.data.value=node1.data.value+node2.data.value;
			node2=null;
			
			
		}else if(diameter2>diameter1) {
			
			node1.data.value=node1.data.value+node2.data.value;
			node1=null;
			
		}else {
			System.out.println("Cannot Be added as both heights are equal");
		}
		
	    //Which is larger ,its password is retained.
		
	}

	
	//class to store the data members 
	
	static class Data{
		
		int ownerId;
	    float value;
	    String owner_name; 
	    HashSet<Data> hashset;
	}
	
	//Node Structure 
	
	static class Node{
		
		Date time_now ;
	    Data data ;
	    String nodeId ;
	    int nodeNumber ;
	    Node referenceNodeid ;
	    ArrayList<Node> childReferenceNodeid;                 // Array of addresses.
	    Node genesisReferenceNodeid;
	    HashSet<Node> HashValue;         // Hash of value of the set.
	    boolean isEncrypted;                       //Will block access to node if it has been encrypted.
	    String password;
	    float sumofchildsofar;        //to keep the track of the sum of its child elements.
	}
	
}
