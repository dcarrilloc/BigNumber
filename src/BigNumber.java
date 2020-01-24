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
        System.out.println(result);

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
        StringBuilder result = new StringBuilder();
        int exceso = 0;
        int exceso_aux;
        int counter = 0;

        if (!numeroValido(number1.toString()) || !numeroValido(number2.toString())) {
            return new BigNumber("0");
        }


        for (int i = number2.length() - 1; i >= 0; i--) {
            for (int j = number1.length() - 1; j >= 0; j--) {
                if ((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + exceso > 10) {
                    exceso_aux = ((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + exceso) / 10;
                    //result.append(((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + exceso) - (10 * exceso_aux));
                    int x = (((number1.charAt(j) - 48) * (number2.charAt(i) - 48) + exceso) - (10 * exceso_aux));


                    counter++;

                    exceso = exceso_aux;
                } else {
                    result.append(0);
                    result.append((number1.charAt(j) - 48) - (number2.charAt(i) - 48) + exceso);
                }
            }
            if (exceso != 0) {
                result.append(exceso);
            }
            exceso = 0;





        }


        return new BigNumber(result.toString());
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
        for (int i = 0; s.charAt(i) == 48; i++) {
            counter++;
        }

        for (int i = 0; i < s.length() - counter; i++) {
            number.append(s.charAt(i + counter));
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
