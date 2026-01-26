import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Scanner sc = new Scanner(System.in);

        //implement a REPL (Read-Eval-Print Loop)
        while(true){
            System.out.print("$ ");
            String command = sc.nextLine();

            if (command.equals("exit")) {
                System.out.println("Exiting shell...");
                break;
            }
            //As of now we will throw all commands as invalid.    
            System.out.println(command+": command not found");
        }

        sc.close();
    }
}
