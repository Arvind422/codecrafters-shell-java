import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Scanner sc = new Scanner(System.in);

        //implement a REPL (Read-Eval-Print Loop)
        while(true){
            System.out.print("$ ");
            String command = sc.nextLine();

            // Logic to exit the shell
            if (command.equals("exit")) {
                break;
            }

            // Logic to echo output the shell
            if (command.contains("echo")) {
                System.out.println(command.length() > 5 ? command.split("echo ")[1] : "");
                continue;
            }

            //As of now we will throw all commands as invalid.    
            System.out.println(command+": command not found");
        }

        sc.close();
    }
}
