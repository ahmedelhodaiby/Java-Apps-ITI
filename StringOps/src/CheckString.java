public class CheckString {

    //check whether a string contains only alphabets or not.
    public static boolean isAlphabet(String s){
        if(s != null && s != "" ){
            return s.chars().allMatch(Character::isLetter);
        }
        return false;
    }
}