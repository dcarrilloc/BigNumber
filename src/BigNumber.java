class BigNumber {

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
        BigNumber result;
        String number2 = other.toString();






        return other;
    }



    // Resta
    BigNumber sub(BigNumber other) {
        return other;
    }



    // Multiplica
    BigNumber mult(BigNumber other) {
        return other;
    }



    // Divideix
    BigNumber div(BigNumber other) {
        return other;
    }



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
        }
        return 0;
    }


    // Torna un String representant el número
    @Override
    public String toString() {
        return this.number;
    }

    // Mira si dos objectes BigNumber són iguals
    @Override
    public boolean equals(Object other) {

        // comprobamos que los dos números son válidos
        if (!numeroValido(this.number) || !numeroValido(other.toString())) {
            System.out.println("Ha habido un error.");
            return false;
        }

        String primerNum = quitarCeros(this.number);
        String segundoNum = quitarCeros(other.toString());

        // creando arrays
        int[] number1 = new int[primerNum.length()];
        int[] number2 = new int[segundoNum.length()];

        for (int i = 0; i < number1.length; i++) {
            number1[i] = primerNum.charAt(i);
        }

        for (int i = 0; i < number2.length; i++) {
            number2[i] = segundoNum.charAt(i);
        }


        // comparando los dos números //

        if (number1.length != number2.length) {
            return false;
        } else {

            for (int i = 0; i < number1.length; i++) {
                if (number1[i] != number2[i]) {
                    return false;
                }
            }
            return true;
        }
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
