import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class draft {

	public static void main(String[] args) throws FileNotFoundException {

		//File questionsFile = ;
		File questionsFile = new File("spec-questions.txt");
		Scanner questions = new Scanner(questionsFile);
        QuestionsGame test1 = new QuestionsGame(questions);
        QuestionNode car = new QuestionNode("car", "A:",null,null);
        QuestionNode bike = new QuestionNode("bicycle", "A:",null,null);
        QuestionNode stuck = new QuestionNode("Does it get stuck in traffic", "Q:",car,bike);
       
        
        
        QuestionNode TA = new QuestionNode("TA", "A:",null,null);
        QuestionNode teacher = new QuestionNode("TEACHER", "A:",null,null);
        QuestionNode nice = new QuestionNode("is it nice", "Q:",TA,teacher);
        QuestionNode wheels = new QuestionNode("Does it have wheels", "Q:",stuck,nice);
        QuestionNode fly = new QuestionNode("can it fly", "Q:",null,null);
        QuestionNode isItAnimal = new QuestionNode("Is it an animal?", "Q", null, wheels);
        QuestionNode testNode = test1.getLastQuestion(isItAnimal);
       
        test1.print(testNode);
       
        //System.out.println(test);
        
        
	}

}
