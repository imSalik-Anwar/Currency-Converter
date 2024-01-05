import java.util.HashMap;
import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        HashMap<String, String> favoriteCurrencies = new HashMap<>();

        try{ // check if init command is correct
			if(!ValidateArguments.isInitArgumentValid(args)){
				throw new InvalidArgumentsException("Invalid init arguments.");
			}
            while(true){
                // take inputs for further operations
                System.out.print("Type command: ");
                String[] cla = new String[3];
                for (int i = 0; i < 3; i++) cla[i] = sc.next();

                if(cla[0].equals("convert")){
                    try {
                        Operations.convert(cla);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                } 
                else if(cla[0].equals("add")){
                    try {
                        Operations.addFavorite(favoriteCurrencies, cla);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                } 
                else if(cla[0].equals("remove")){
                    try {
                        Operations.removeFavorite(favoriteCurrencies, cla);
                        System.out.println("Currency was deleted.");
                        System.out.println();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }
                else if(cla[0].equals("list") && cla[1].equals("fav")){
                    try {
                        Operations.listFavorite(favoriteCurrencies, cla);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                } 
                else if(cla[0].equals("list") && cla[1].equals("cc")){
                    try {
                        Operations.listCommands(cla);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }
                else if(cla[0].equals("stop")){
                    try {
                        // validate arguments
                        if(!ValidateArguments.isStopApplicationArgumentValid(cla)){
                            throw new InvalidArgumentsException("Command not found. Enter valid command.");
                        } 
                            break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Check your command.");
                    System.out.println();
                }
            }

		} catch (Exception e){
			System.out.println(e.getMessage()+" Try running application with valid init arguments.");
            System.exit(0);
		}
    }
}
