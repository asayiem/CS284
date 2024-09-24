//Abu Sayiem
//CS-284E
//I pledge that I abide by the Stevens Honor System.



public class BinaryNumber{
    private int[] data;
    private int length;


    // binary number constructor to create a binary number of length of all zeros.
    public BinaryNumber(int length){
		if (length <= 0) {
			throwException("Length has to be greater than 0");
		}
        this.length = length;
        this.data = new int[length];
    }
    // binary number constructor to create a binary number from a string.
    public BinaryNumber(String str){
        this.length = str.length();
        this.data = new int[length];

        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int n = Character.getNumericValue(c);
            if (n != 0 && n != 1){
                throwException("String must only contain 1's and 0's");
            }
            this.data[i] = n;
        }
    }

    private static void throwException(String message) {
        throw new IllegalArgumentException(message);
    }


    // returns the length of the binary number.
    public int getLength(){
        return this.length;
    }
    // returns the interger array that represts the binary number.
    public int[] getInnerArray(){
        return this.data;
    }

    // returns the digit of a binary number given an index.
    public int getDigit(int index){
        if (index < 0 || index >= length) {
            throwException("Index out of bounds: " + index);
        }
        return data[index];
    }
    // returns the decimnal notation of a binary number.
    public int toDecimal(){
        int decimal = 0;
        for(int i = length - 1; i >= 0; i--){
            decimal += data[i] * Math.pow(2, length - 1 - i);
        }
        return decimal;
    }

    // shifts all the values of the binary number to the left or right.
    public void bitShift(int direction, int amount){
        if(direction == -1){
            // left
            for(int i = 0; i < length - amount; i++){
                data[i] = data[i + amount];
            }
            for (int i = length - amount; i < length; i++) {
                data[i] = 0;
            }


        }else if(direction == 1){
            // right
            for(int i = length - 1; i >= amount; i--){
                data[i] = data[i - amount];
            }
            for(int i = 0; i < amount; i++){
                data[i] = 0;
            }
            }
            else{
                throwException("Invalid Shift Direction");
            }

        }

    public static int[] bwor(BinaryNumber bn1, BinaryNumber bn2){
        if (bn1.getLength() != bn2.getLength()) {
            throwException("The lengths of both binary numbers must be equal.");
        }
        int[] bwor = new int[bn1.getLength()];
		    for (int i = 0; i < bn1.getLength(); i++) {
			    if (bn1.getDigit(i) == 1 || bn2.getDigit(i) == 1) {
				bwor[i] = 1;
			} else {
				bwor[i] = 0;
			}
		}
		return bwor;
    }

    public static int[] bwand(BinaryNumber bn1, BinaryNumber bn2){
        if (bn1.getLength() != bn2.getLength()) {
            throwException("The lengths of both binary numbers must be equal.");
        }
        int[] bwand = new int[bn1.getLength()];

        for (int i = 0; i < bn1.getLength(); i++) {
            if (bn1.getDigit(i) == 1 && bn2.getDigit(i) == 1) {
                bwand[i] = 1;
            } else {
                bwand[i] = 0;
            }
        }
        return bwand;

    }
        
     // adds 0's to the start of the binary number
     public void prepend(int amount) {
        int l = this.getLength() + amount;
        int[] copy = new int[l];

        for (int k = 0; k < this.getLength(); k++) {
            copy[k + amount] = this.getDigit(k);
        }

        this.data = copy;
        this.length += amount;
    }

    // adds two binary numbers
    public void add(BinaryNumber aBinaryNumber) {
        int maxLength = Math.max(this.getLength(), aBinaryNumber.getLength());

        int x = maxLength - this.getLength();
        int y = maxLength - aBinaryNumber.getLength();

        if (x > 0) {
            this.prepend(x);
        }

        if (y > 0) {
            aBinaryNumber.prepend(y);
        }

        int[] result = new int[maxLength + 1];

        int carry = 0;
        for (int i = maxLength - 1; i >= 0; i--) {
            int sum = this.getDigit(i) + aBinaryNumber.getDigit(i) + carry;
            result[i + 1] = sum % 2;
            carry = sum / 2;
        }

        result[0] = carry;

        this.data = result;
        this.length = result.length;
    }


	public String toString() {
		String str = new String();
		for (int i = 0; i < this.getLength(); i++) {
			str += this.getDigit(i);
		}
		return str;
	}
}