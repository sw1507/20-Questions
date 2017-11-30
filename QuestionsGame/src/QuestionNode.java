

public class QuestionNode {
	
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
