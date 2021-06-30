import java.util.function.BiPredicate;

public class Better {
    //make a method called betterString that takes two Strings and a lambda  that says whether the first or the second is “better”.
    public static String BetterString(String s1, String s2, BiPredicate<String, String> p){
        if(p.test(s1, s2))
            return s1;
        else
            return s2;
    }
}