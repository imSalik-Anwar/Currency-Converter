import java.util.HashMap;

public class ValidateArguments {

    public static boolean isInitArgumentValid(String[] args) {
        return args.length == 3 && args[0].equals("init") && args[1].equals("cc") && args[2].equals("app");
    }

    public static boolean isConvertArgumentValid(String[] args) {
        if(args.length == 3 && args[0].equals("convert") && args[1].length() == 3 && args[2].length() == 3) return true;
        return false;
    }

    public static boolean isAddArgumentValid(String[] args) {
        return args.length == 3 && args[0].equals("add") && args[1].equals("fav") & args[2].length() == 3;
    }

    public static boolean isRemoveArgumentValid(String[] args) {
        return args.length == 3 && args[0].equals("remove") && args[1].equals("fav") & args[2].length() == 3;
    }

    public static boolean isListArgumentValid(String[] args){
        return args.length == 3 && args[0].equals("list") && args[1].equals("fav") && args[2].equals("cur");
    }

    public static boolean isListCommandArgumentValid(String[] args) {
        return args.length == 3 && args[0].equals("list") && args[1].equals("cc") && args[2].equals("commands");
    }

    public static boolean isStopApplicationArgumentValid(String[] args) {
        return args.length == 3 && args[0].equals("stop") && args[1].equals("cc") && args[2].equals("app");
    }
}
