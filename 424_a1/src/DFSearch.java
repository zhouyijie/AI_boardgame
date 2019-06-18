import java.util.ArrayList;
import java.util.Stack;


public class DFSearch
{
	
	public static void search(int[] board, boolean d)
	{
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

		performSearch(stack, d);
	}

	/**
	 * Initialization function for FWGC DFSearch
	 */
	/*
	public static void search(boolean d)
	{
		SearchNode root = new SearchNode(new FWGC_State());
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

		performSearch(stack, d);
	}
	*/

	/*
	 * Helper method to check to see if a SearchNode has already been evaluated.
	 * Returns true if it has, false if it hasn't.
	 */
	private static boolean checkRepeats(SearchNode n)
	{
		boolean retValue = false;
		SearchNode checkNode = n;

		// While n's parent isn't null, check to see if it's equal to the node
		// we're looking for.
		while (n.getParent() != null && !retValue)
		{
			if (n.getParent().getCurState().equals(checkNode.getCurState()))
			{
				retValue = true;
			}
			n = n.getParent();
		}

		return retValue;
	}

	/**
	 * Performs a DFSearch using q as the search space
	 * 
	 * @param s
	 *            - A SearchNode queue to be populated and searched
	 */
	public static void performSearch(Stack<SearchNode> s, boolean d)
	{
		int searchCount = 1; // counter for number of iterations

		while (!s.isEmpty()) // while the queue is not empty
		{
			SearchNode tempNode = (SearchNode) s.pop();

			// if tempNode is not the goal state
			if (!tempNode.getCurState().isGoal())
			{
				// generate tempNode's immediate successors
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors();

				/*
				 * Loop through the successors, wrap them in a SearchNode, check
				 * if they've already been evaluated, and if not, add them to
				 * the queue
				 */
				for (int i = 0; i < tempSuccessors.size(); i++)
				{
					// second parameter here adds the cost of the new node to
					// the current cost total in the SearchNode
					SearchNode newNode = new SearchNode(tempNode,
							tempSuccessors.get(i), tempNode.getCost()
									+ tempSuccessors.get(i).findCost(), 0);

					if (!checkRepeats(newNode))
					{
						s.add(newNode);
					}
				}
				searchCount++;
			}
			else
			// The goal state has been found. Print the path it took to get to
			// it.
			{
				// Use a stack to track the path from the starting state to the
				// goal state
				Stack<SearchNode> solutionPath = new Stack<SearchNode>();
				solutionPath.push(tempNode);
				tempNode = tempNode.getParent();

				while (tempNode.getParent() != null)
				{
					solutionPath.push(tempNode);
					tempNode = tempNode.getParent();
				}
				solutionPath.push(tempNode);

				// The size of the stack before looping through and emptying it.
				int loopSize = solutionPath.size();

				for (int i = 0; i < loopSize; i++)
				{
					tempNode = solutionPath.pop();
					tempNode.getCurState().printState();
					System.out.println();
					System.out.println();
				}
				System.out.println("The cost was: " + tempNode.getCost());
				if (d)
				{
					System.out.println("The number of nodes examined: "
							+ searchCount);
				}

				System.exit(0);
			}
		}

		// This should never happen with our current puzzles.
		System.out.println("Error! No solution found!");
	}
}