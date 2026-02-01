import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        String[] allowedCommands = {"echo" , "type", "exit" , "pwd"};

        Scanner sc = new Scanner(System.in);

        //implement a REPL (Read-Eval-Print Loop)
        while(true){
            System.out.print("$ ");
            String command = sc.nextLine().trim();

            // Logic to exit the shell
            if (command.equals("exit")) {
                break;
            }

            // Logic to echo output the shell
            if (command.contains("echo ") || command.split(" ")[0].equals("echo")) {
                System.out.println(command.length() > 5 ? command.split("echo ")[1] : "");
                continue;
            }

            // Logic to print the working directory
            if (command.equals("pwd")) {
                pwd();
                continue;
            }

            // Type command logic
            if (command.contains("type")) {
                typeMethod(command , allowedCommands);
                continue;
            }

            // Logic to check executable files & execute them.
            boolean commandFound  = false;
            String[] argus = command.trim().split(" ");
            String commandName = argus[0];

            String pathEnv = System.getenv("PATH");

            if(pathEnv != null && !pathEnv.isEmpty() ){
                String[] envPaths = pathEnv.split(":");

                for(String envPath : envPaths){
                    Path fullPath = Path.of(envPath,commandName);
                    if (Files.exists(fullPath) && Files.isExecutable(fullPath)) {
                        commandFound = true;
                        executeEXEwithArgs(argus);
                        break;
                    }
                }
            }

            if(!commandFound){
                System.out.println(command+": command not found");
            }

        }
        sc.close();
    }

    public static void typeMethod(String command,String[] allowedCommands){
        String commandName = command.split("type ")[1];
        if(command.length() > 5 &&  Arrays.stream(allowedCommands).anyMatch( command.split("type ")[1]::equals)){
            System.out.println( commandName + " is a shell builtin");
        }
        else{
            String pathEnv = System.getenv("PATH");
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
    }

    public static void executeEXEwithArgs(String[] argus) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(argus);
        pb.redirectErrorStream(true);

        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())
        );

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();

    }

    public static void pwd() {
        String cwd = System.getProperty("user.dir");
        System.out.println(cwd);
    }

}


