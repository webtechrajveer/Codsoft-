import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

class QuizTimer {
    private int timeLimit;
    private Timer timer;
    private TimerTask task;

    public QuizTimer(int timeLimit, Runnable onTimeUp) {
        this.timeLimit = timeLimit;
        this.timer = new Timer();
        this.task = new TimerTask() {
            @Override
            public void run() {
                onTimeUp.run();
            }
        };
    }

    public void start() {
        timer.schedule(task, timeLimit * 1000);
    }

    public void cancel() {
        timer.cancel();
    }
}

public class QuizApplication {
    private static Question[] questions = {
        new Question("How many Bits make one Byte??", new String[]{"16bit", "64bit", "32bit", "8bit"}, 0),
        new Question("What is the full form of RAM?'?", new String[]{"Random Access Memory", "Read And Memory", "Random All Memory", "None of the above mentioned"}, 0),
        new Question("Who is the founder of Facebook??'?", new String[]{"Andrew Maclin", "Mark Adon", "Mark Zuckerberg", "None of the above mentioned"}, 0),
    };

    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        for (Question question : questions) {
            displayQuestion(question);
            QuizTimer timer = new QuizTimer(10, () -> {
                System.out.println("\nTime's up!");
                processAnswer(-1);
            });
            timer.start();

            int answer = getAnswer();
            timer.cancel();
            processAnswer(answer);
        }

        displayResult();
    }

    private static void displayQuestion(Question question) {
        System.out.println(question.question);
        for (int i = 0; i < question.options.length; i++) {
            System.out.println((i + 1) + ": " + question.options[i]);
        }
        System.out.print("Your answer: ");
    }

    private static int getAnswer() {
        try {
            return scanner.nextInt() - 1;
        } catch (Exception e) {
            scanner.next(); // clear invalid input
            return -1;
        }
    }

    private static void processAnswer(int answer) {
        if (answer == questions[currentQuestionIndex].correctAnswer) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect. The correct answer is " + (questions[currentQuestionIndex].correctAnswer + 1));
        }
        currentQuestionIndex++;
    }

    private static void displayResult() {
        System.out.println("\nQuiz Finished!");
        System.out.println("Your score: " + score + "/" + questions.length);
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Q" + (i + 1) + ": " + questions[i].question);
            System.out.println("Correct answer: " + (questions[i].correctAnswer + 1));
        }
    }
}

