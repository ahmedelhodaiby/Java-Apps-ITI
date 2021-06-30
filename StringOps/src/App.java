public class App {
    public static void main(String[] args) {

        System.out.println("\nWhich is the better string?");
        String string1 = "abc";
        String string2 = "ABC";
        System.out.println("\""+ Better.BetterString(string1, string2, (s1, s2) -> s1.charAt(1) > s2.charAt(1))+"\" is the better string");

        System.out.println("Is the string passed all alphabets?\n"+CheckString.isAlphabet("as7Ssa"));
    }
}
