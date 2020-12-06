class Test
{
    public static void main(String[] args)
    {

        ISA computer1 = new ISA();
        String Snull = "0000000000000000000000000000000000000000";
        
        computer1.setAC(Snull);
        computer1.setMQ(Snull);

        // Setting the memory of instruction from 0
        // Setting the memory of data from 500

        String Memory[] = computer1.getMemory();
        Memory[500] = ISA.IntegerToBinary(7);
        Memory[501] = ISA.IntegerToBinary(-8);
        Memory[502] = ISA.IntegerToBinary(10);
        
        String X1 = ISA.IntegerToBinary(500).substring(28);// 7
        String X2 = ISA.IntegerToBinary(501).substring(28);// -8
        String X3 = ISA.IntegerToBinary(502).substring(28);// 10
        String X4 = ISA.IntegerToBinary(600).substring(28);// 
        
        // Random ISA instructions
        System.out.println("\n 1st program \n");
        
        // LOAD |M(x1)| and ADD M(x2)
        Memory[0] = "00000011" + X1 + "00000111" + X2;
        // ADD M(x3) and SUB M(x3)
        Memory[1] = "00000101" + X3 + "00000110" + X3;
        // LSH and RSH
        Memory[2] = "00010100" + "XXXXXXXXXXXX" + "00010101" + "XXXXXXXXXXXX";
        // STOR M(x4) and LOAD MQ M(x4)
        Memory[3] = "00100001" + X4 + "00001001" + X4;
        // LOAD MQ and LOAD -M(x2)
        Memory[4] = "00001010" + X2 + "00000010" + X2;
        // JUMP M(8,0:19) and JUMP M(8,20:39)
        Memory[5] = "00001101" + "000000001000" + "00001110" + "000000001000";
        // JUMP M(5,20:39) and JUMP + M(9,20:39)
        Memory[8] = "00001110" + "000000000101" + "00010000" + "000000001001";
        // "XXXXXXXXXXXXXXXXXXXX" and DIV M(x1)
        Memory[9] = "XXXXXXXX" + "XXXXXXXXXXXX" + "00001100" + X1;
        // HALT and HALT
        Memory[10] = "0000000000000000000000000000000000000000";
        
        // fetch
        // decode
        // execute
        computer1.cycles();

/****************************************************************************************************************************/

        System.out.println("\n 2nd program \n");
        ISA computer2 = new ISA();

        Memory = computer2.getMemory();
        computer2.setAC(Snull);
        computer2.setMQ(Snull);

        Memory[500] = ISA.IntegerToBinary(10);// a = 10
        Memory[501] = ISA.IntegerToBinary(15);// b = 15

        // LOAD M(x1) and SUB M(x2)
        Memory[0] = "00000001" + X1 + "00000110" + X2;
        // JUMP + (5,LEFT) and JUMP (2,RIGHT)
        Memory[1] = "00001111" + "000000000101" + "00001110" + "000000000010";
        // "XXXXXXXXXXXXXXX" and  LOAD M(x2)
        Memory[2] = "XXXXXXXX" + X1 + "00000001" + X2;
        // SUB M(x1) and STORE M(x3)
        Memory[3] = "00000110" + X1 + "00100001" + X3;
        // HALT and HALT
        Memory[4] = "00000000" + X3 + "00000000" + "000000000000";
        // STOR M(x3) and HALT
        Memory[5] = "00100001" + X3 + "00000000" + "000000000000";

        // fetch
        // decode
        // execute
        computer2.cycles();

        System.out.println("\na = "+ Memory[500]);
        System.out.println("b = " + Memory[501]);
        System.out.println("c = " + Memory[502]);
        
    }
}
