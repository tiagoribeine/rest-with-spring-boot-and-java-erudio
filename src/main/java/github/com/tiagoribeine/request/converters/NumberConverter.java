package github.com.tiagoribeine.request.converters;
import github.com.tiagoribeine.exception.UnsupportedMathOperationException;

public class NumberConverter {

    public static Double convertToDouble(String strNumber) throws IllegalArgumentException{

        if (strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    };

    public static boolean IsNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+"); //[numeros negativos/postivos]?[0 a 9]*\\ após a virgula [0 a 9]
    }
}
