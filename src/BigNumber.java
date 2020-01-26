public class BigNumber {

    private String number;


    // Constructor 1
    public BigNumber(String s) {
        this.number = s;
    }

    // Constructor 2
    public BigNumber(BigNumber b) {
        this.number = b.toString();
    }


    // Suma
    BigNumber add(BigNumber other) {

        StringBuilder number1 = new StringBuilder(quitarCeros(this.number));
        StringBuilder number2 = new StringBuilder(quitarCeros(other.toString()));
        StringBuilder result = new StringBuilder();
        int exceso = 0;

        // nos aseguramos de que los dos números son válidos
        if (!numeroValido(number1.toString()) || !numeroValido(number2.toString())) {
            return new BigNumber("0");
        }


        // si los números tienen diferente cantidad de dígitos
        // entonces rellenaremos con 0 a la izquierda el menor
        int diferencia = Math.abs(number1.length() - number2.length());
        if (number1.length() > number2.length()) {
            number2.reverse();
            for (int i = 0; i < diferencia; i++) {
                number2.append("0");
            }
            number2.reverse();

        } else if (number1.length() < number2.length()) {
            number1.reverse();
            for (int i = 0; i < diferencia; i++) {
                number1.append("0");
            }
            number1.reverse();
        }


        for (int i = 0, c = number2.length() - 1; i < number2.length(); i++, c--) {
            if ((number1.charAt(c) - 48) + (number2.charAt(c) - 48) + exceso >= 10) {
                result.append((number1.charAt(c) - 48 + exceso) + (number2.charAt(c) - 48) - 10);
                exceso = 1;
            } else {
                result.append((number1.charAt(c) - 48 + exceso) + (number2.charAt(c) - 48));
                exceso = 0;
            }
        }

        if (exceso != 0) {
            result.append(exceso);
        }

        result.reverse();

        return new BigNumber(result.toString());
    }



    // Resta
    BigNumber sub(BigNumber other) {

        StringBuilder number1 = new StringBuilder(quitarCeros(this.number));
        StringBuilder number2 = new StringBuilder(quitarCeros(other.toString()));
        StringBuilder result = new StringBuilder();
        int exceso = 0;

        // supondremos que el numero 1 es mas grande que el numero 2

        if (!numeroValido(number1.toString()) || !numeroValido(number2.toString())) {
            return new BigNumber("0");
        }

        // si los números tienen diferente cantidad de dígitos
        // entonces rellenaremos con 0 a la izquierda el menor
        int diferencia = Math.abs(number1.length() - number2.length());
        if (number1.length() > number2.length()) {
            number2.reverse();
            for (int i = 0; i < diferencia; i++) {
                number2.append("0");
            }
            number2.reverse();
        }


        // restamos
        for (int i = 0, c = number1.length() - 1; i < number1.length(); i++, c--) {
            if ((number1.charAt(c) - 48) < (number2.charAt(c) - 48) + exceso) {
                result.append((number1.charAt(c) - 48 + 10) - (number2.charAt(c) - 48 + exceso));
                exceso = 1;
            } else {
                result.append((number1.charAt(c) - 48) - (number2.charAt(c) - 48 + exceso));
                exceso = 0;
            }
        }

        result.reverse();

        return new BigNumber(result.toString());
    }



    // Multiplica
    BigNumber mult(BigNumber other) {

        StringBuilder number1 = new StringBuilder(quitarCeros(this.number));
        StringBuilder number2 = new StringBuilder(quitarCeros(other.toString()));
        StringBuilder mult_result = new StringBuilder();
        BigNumber result = new BigNumber("0");
        int acarreo = 0;
        int acarreo_aux;

        if (!numeroValido(number1.toString()) || !numeroValido(number2.toString())) {
            return new BigNumber("0");
        }

        for (int i = number2.length() - 1, counter = 0; i >= 0; i--, counter++) {
            for (int j = number1.length() - 1; j >= 0; j--) {
                if ((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + acarreo >= 10) {
                    acarreo_aux = ((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + acarreo) / 10;
                    mult_result.append(((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + acarreo) - (10 * acarreo_aux));

                    acarreo = acarreo_aux;
                } else {
                    mult_result.append((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + acarreo);
                    acarreo = 0;
                }
            }
            if (acarreo != 0) mult_result.append(acarreo);
            acarreo = 0;

            mult_result.reverse();

            // añadir los ceros necesarios
            for (int j = 0; j < counter; j++) {
                mult_result.append(0);
            }

            BigNumber aux = new BigNumber(mult_result.toString());



            result = result.add(aux);

            // limpiamos el string
            mult_result.delete(0,mult_result.length());
        }
        return result;
    }


    // Divideix
    BigNumber div(BigNumber other) {
        return other;
    }

    /*

    // Arrel quadrada
    BigNumber sqrt() { }
    // Potència
    BigNumber power(int n) { }
    // Factorial
    BigNumber factorial() { }
    // MCD. Torna el Màxim comú divisor
    BigNumber mcd(Bignumber other) { }

     */

    // Compara dos BigNumber. Torna 0 si són iguals, -1 si el primer és menor
    // i torna 1 si el segon és menor
    public int compareTo(BigNumber other) {

        String number1 = quitarCeros(this.number);
        String number2 = quitarCeros(other.toString());

        // comprobamos que los dos números son válidos
        if (!numeroValido(number1) || !numeroValido(number2)) {
            System.out.println("Ha habido un error.");
            return -2;
        }

        if (number1.length() != number2.length()) {
            // si la longitud de los números es diferente entre si
            if (number1.length() > number2.length()) return 1;
            return -1;

        } else {
            // si la longitud de los números es igual
            for (int i = 0; i < number1.length(); i++) {
                if (number1.charAt(i) > number2.charAt(i)) return 1;
                if (number1.charAt(i) < number2.charAt(i)) return -1;
            }
            return 0;
        }
    }


    // Torna un String representant el número
    @Override
    public String toString() {
        return this.number;
    }

    // Mira si dos objectes BigNumber són iguals
    @Override
    public boolean equals(Object other) {

        if (other instanceof BigNumber) {
            BigNumber b = (BigNumber) other;
            return quitarCeros(this.number).equals(quitarCeros(b.toString()));
        }
        return false;
    }

    public String quitarCeros(String s) {

        // Quita los ceros que pueda haber al principio de cada número

        StringBuilder number = new StringBuilder();
        int counter = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != 48) {
                break;
            } else {
                counter ++;
            }
        }

        // significa que es un numero de tipo: 0000
        if (counter == s.length()) {
            return "0";
        } else {
            for (int i = 0; i < s.length() - counter; i++) {
                number.append(s.charAt(i + counter));
            }
        }

        return number.toString();
    }


    // comprueba que todos los dígitos de n son válidos
    public boolean numeroValido(String n) {

        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) < 48 || n.charAt(i) > 57) {
                return false;
            }
        }
        return true;
    }
}
