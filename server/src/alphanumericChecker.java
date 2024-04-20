import java.io.PrintWriter;

public class alphanumericChecker extends Thread {

    String task;
    PrintWriter output;

    alphanumericChecker(String text, PrintWriter out)
    {
        task=text;
        output=out;
    }

    @Override
    public void run()
    {
        boolean result = task.matches("[a-zA-Z0-9]+");
        output.println(result);
        System.out.println("Result of ("+task+") sent as: " + result + ".");
    }
}
