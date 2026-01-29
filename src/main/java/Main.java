import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        String[] allowedCommands = {"echo" , "type", "exit"};

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
            if (command.contains("echo ") || command.split(" ")[0].equals("echo")) {
                System.out.println(command.length() > 5 ? command.split("echo ")[1] : "");
                continue;
            }

            // Type command logic
            if (command.contains("type")) {
                String commandName = command.split("type ")[1];
                if(command.length() > 5 &&  Arrays.stream(allowedCommands).anyMatch( command.split("type ")[1]::equals)){
                    System.out.println( commandName + " is a shell builtin");
                }
                else{
                    String pathEnv = System.getenv("PATH");
                    String envStr = "";
                    boolean found = false;
                    if(pathEnv != null && !pathEnv.isEmpty() ){
                        String[] envPaths = pathEnv.split(":");

                        for(String envPath : envPaths){
                            Path fullPath = Path.of(envPath,commandName);
                            if (Files.exists(fullPath) && Files.isExecutable(fullPath)) {
                                System.out.println(commandName + " is " + fullPath);
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        System.out.println(commandName +": not found");
                    }
                }
                continue;
            }

            //As of now we will throw all commands as invalid.    
            System.out.println(command+": command not found");
        }

        sc.close();
    }
}
