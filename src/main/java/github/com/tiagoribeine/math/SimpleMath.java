package github.com.tiagoribeine.math;

import github.com.tiagoribeine.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class SimpleMath {

    public Double sum(double numberOne,double numberTwo){
           return numberOne + numberTwo;
    }
    public Double subtraction(double numberOne,double numberTwo){
           return numberOne - numberTwo;
    }

    public Double multiplication(double numberOne,double numberTwo){
           return numberOne * numberTwo;
    }

    public Double division(double numberOne,double numberTwo){
           return numberOne / numberTwo;
    }
    public Double mean(double numberOne,double numberTwo){
        return (numberOne + numberTwo)/2;
    }
    public double square_root(double number){
        return Math.sqrt(number);
    }
    public double power(double numberOne, double numberTwo){
        return Math.pow(numberOne, numberTwo);
    };
}
