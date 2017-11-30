import java.io.PrintStream;
import java.util.*;

/**
 * Homework6 for CSE 143 2017Summer Written by: Su Wang, enrolled in quiz
 * session AC QuestionsGame is a guessing game which asks Yes/No questions to
 * guess the object the player is thinking about.
 */
public class QuestionsGame {
	private QuestionNode overallRoot;
	private Scanner console;
	private String userInput;

	/**
	 * QuestionNode is a node to represent a question/answer in QuestionsGame.
	 *
	 */
	private static class QuestionNode {
		public final String data;
		public final String type;
		public QuestionNode left;
		public QuestionNode right;

		/**
		 * Construct a new question node.
		 * 
		 * @param data
		 *            should be strings, which is the answer or question.
		 * @param type
		 *            should be either "Q:" or "A:" to represent the 'Question'
		 *            or 'Answer'.
		 */
		public QuestionNode(String data, String type) {
			this(data, type, null, null);
		}

		public QuestionNode(String data, String type, QuestionNode left, QuestionNode right) {
			this.data = data;
			this.type = type;
			this.left = left;
			this.right = right;
		}
	}

	public static final String QUESTION_MARK = "Q:";
	public static final String ANSWER_MARK = "A:";

	/**
	 * Initialize a new QuestionGame, construct the first question/answer.
	 * 
	 * @param initialObject
	 *            is a string, it is the first question/answer that would appear
	 *            when game starts.
	 */
	public QuestionsGame(String initialObject) {
		overallRoot = new QuestionNode(initialObject, QUESTION_MARK);

	}

	/**
	 * Initialize a new QuestionGame by reading a file of questions and answers
	 * in a standard format.
	 * 
	 * @param input
	 *            is not null, and is from a existing file in a standard format.
	 */
	public QuestionsGame(Scanner input) {
		overallRoot = QuestionsGameHelper(input);
	}

	/**
	 * Read the input file and save the information into a binary tree.
	 * 
	 * @param input
	 *            is a Scanner.Input format is standard and legal.
	 * @return a QuestionNode.
	 */
	private QuestionNode QuestionsGameHelper(Scanner input) {
		String type = input.nextLine();
		String content = input.nextLine();
		QuestionNode newNode = new QuestionNode(content, type);
		if (type.equals(QUESTION_MARK)) {
			newNode.left = QuestionsGameHelper(input);
			newNode.right = QuestionsGameHelper(input);
		}

		return newNode;

	}

	/**
	 * Store all questions and answers into the input file.
	 * 
	 * @param output
	 *            is a PrintStream, should not be null, throws
	 *            IllegalArgumentException if violated.
	 */
	public void saveQuestions(PrintStream output) {
		if (output == null) {
			throw new IllegalArgumentException();
		} else {
			saveQuestions(output, overallRoot);
		}
	}

	/**
	 * 
	 * @param output
	 *            is a PrintStream, should not be null
	 * @param root
	 *            should be a QuestionNode, which is the node that this method
	 *            start saving information from.
	 */
	private void saveQuestions(PrintStream output, QuestionNode root) {
		String type = root.type + "\r\n";
		String data = root.data + "\r\n";
		output.append(type);
		output.append(data);
		if (type.contains(QUESTION_MARK)) {
			saveQuestions(output, root.left);
			saveQuestions(output, root.right);
		}
	}

	/**
	 * Play QuestionGame with the user. Print out "Awesome! I win!" if the
	 * object the game finds out is the one that the user is thinking about. If
	 * computer lose it will ask user to input new question to help it to become
	 * more intelligent.
	 */
	public void play() {
		QuestionNode lastQuestion = null;
		QuestionNode answer = overallRoot;
		console = new Scanner(System.in);
		if (overallRoot.type.equals("Q:")) {
			// get the last Question before the computer finds the answer.
			lastQuestion = getLastQuestion(overallRoot);
			// get the answer the computer finds.
			answer = getAnswer(lastQuestion);
		}
		System.out.println("I guess that your object is " + answer.data + "!");
		System.out.print("Am I right? (y/n)? ");
		if (console.nextLine().trim().toLowerCase().startsWith("y")) {
			System.out.println("Awesome! I win!");
		} else {
			System.out.println("Boo! I loose. Please help me get better!");
			System.out.print("What is your object? ");
			String correctAnswer = console.nextLine().trim().toLowerCase();
			QuestionNode correctAnswerNode = new QuestionNode(correctAnswer, ANSWER_MARK, 
					null, null);
			System.out.println("Please give me a yes/no question that distinguishes "
					+ "between " + correctAnswer + " and "
					+ answer.data);
			String newQuestion = console.nextLine().trim();
			QuestionNode newQuestionNode = null;
			System.out.print("Is the answer \"yes\" for " + correctAnswer + "? ");

			if (console.nextLine().trim().toLowerCase().startsWith("y")) {
				newQuestionNode = new QuestionNode(newQuestion, QUESTION_MARK, 
						correctAnswerNode, answer);
			} else {
				newQuestionNode = new QuestionNode(newQuestion, QUESTION_MARK, 
						answer, correctAnswerNode);
			}
			if (overallRoot.type.equals("A:")) {
				overallRoot = newQuestionNode;
			} else {
				if (userInput.contains("y")) {
					lastQuestion.left = newQuestionNode;
				} else {
					lastQuestion.right = newQuestionNode;
				}
			}
		}

	}

	/**
	 * Go throw the entire tree to get the question the game asks before finds
	 * the answer. The input should be a QuestionNode from which the game
	 * starts.
	 * 
	 * @param current
	 *            is a QuestionNode, which represents the question that the game
	 *            start asking from. And its type should be "Q:"
	 * @returns a QuestionNode, which should be the last question the game asked
	 *          before finds the answer.
	 */
	private QuestionNode getLastQuestion(QuestionNode current) {
		System.out.print(current.data + " (y/n)? ");
		userInput = console.nextLine().trim().toLowerCase();
		if ((userInput.startsWith("y") && current.left.type.equals(ANSWER_MARK))
				|| (userInput.startsWith("n") && current.right.type.equals(ANSWER_MARK))) {
			return current;
		} else {
			if (userInput.startsWith("y")) {
				return getLastQuestion(current.left);
			} else {
				return getLastQuestion(current.right);
			}
		}

	}

	/**
	 * 
	 * @param lastQuestion
	 *            should be a QuestionNode, and its type should be "Q:". It is
	 *            the last question the game asked before finds the answer.
	 * @return a QuestionNode, which is the answer the game finds.
	 */
	private QuestionNode getAnswer(QuestionNode lastQuestion) {
		if (userInput.startsWith("y")) {
			return lastQuestion.left;
		} else {
			return lastQuestion.right;
		}
	}

}
