package github.com.tiagoribeine.controllers;
import github.com.tiagoribeine.exception.UnsupportedMathOperationException;
import github.com.tiagoribeine.math.SimpleMath;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.lang.Math;
import github.com.tiagoribeine.request.converters.NumberConverter;


import java.rmi.server.ExportException;

@RestController
@RequestMapping("/math") // URL base: http://localhost:8080/math: Endpoint de todas as operações começarão com Math
public class MathController {

    /*
     -> RequestMapping: A classe Controller possue vários metodos manipuladores para gerenciar as diferentes requisições HTTP
     é colocada sobre um metodo, fornece o mapeamento entre o caminho da requisição e o metodo manipulador. Também suporta algumas
     opções avançadas que podem ser usadas para especificar metodos avançados para diferentes tipos de requisição
     Pode-se especificar um metodo para manipular uma requisição GET, outro para POST na mesma URI, pode-se manter a URI base http://localhost:8080/math/
     E através do Request mapping usar um verbo para cada uma delas. Quando se coloca a Annotation na classe, funciona como a URL básica

     Quando usado na/no:
     * Classe: define o caminho base
     * Metodo: define o caminho específico + verbo HTTP
     */

    private SimpleMath math = new SimpleMath();

    //http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{numberOne}/{numberTwo}") //{numberOne} e {numberTwo} são VARIÁVEIS na URL
    public Double sum(
            //String será usado para tratar exceções e validar inputs de dados
            @PathVariable("numberOne") String numberOne, // Anotação usada para recuperar dados da URL
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        // Verificação de dados

        //Tipo numérico
        if(!NumberConverter.IsNumeric(numberOne) || !NumberConverter.IsNumeric(numberTwo) ) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    //http://localhost:8080/math/subtraction/3/5
    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public double subtraction(
        @PathVariable("numberOne") String numberOne,
        @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!NumberConverter.IsNumeric(numberOne) || !NumberConverter.IsNumeric(numberTwo)) throw  new UnsupportedMathOperationException("Please set a numeric value!");
        return math.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    };

    //http://localhost:8080/math/multiplication/3/5
    @RequestMapping("multiplication/{numberOne}/{numberTwo}")
    public double multiplication(
       @PathVariable("numberOne") String numberOne,
       @PathVariable("numberTwo") String numberTwo
    ) throws Exception{
        if (!NumberConverter.IsNumeric(numberOne) || !NumberConverter.IsNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    };

    //http://localhost:8080/math/division/3/5
    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public double division(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception{
        if(!NumberConverter.IsNumeric(numberOne) || !NumberConverter.IsNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        if(NumberConverter.convertToDouble(numberTwo) == 0) throw new UnsupportedMathOperationException("Divisor must be != 0");
        return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    //http://localhost:8080/math/squareroot/25
    @RequestMapping("/squareroot/{number}")
    public double square_root(
            @PathVariable("number") String number
    ) throws Exception {
        if (!NumberConverter.IsNumeric(number)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        if (NumberConverter.convertToDouble(number)<0) throw new UnsupportedMathOperationException("Number must be > 0");
        return math.square_root(NumberConverter.convertToDouble(number));
    }

    //http://localhost:8080/math/power/3/3
    @RequestMapping("/power/{numberOne}/{numberTwo}")
    public double power(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!NumberConverter.IsNumeric(numberOne) || !NumberConverter.IsNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.power(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    };

    //http://localhost:8080/math/mean/3/3
    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public double mean(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!NumberConverter.IsNumeric(numberOne) || !NumberConverter.IsNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }
}
