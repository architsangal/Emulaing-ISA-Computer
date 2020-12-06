class ISA
{

    public void cycles()
    {
        boolean isHalt = false;
        while(!isHalt)
        {
            this.MAR = this.PC;

            if(IsJump && !Ljump && Rjump)
            {
                IsJump =false;
                Ljump = false;
                Rjump = false;
                this.MBR = "XXXXXXXXXXXXXXXXXXXX"+this.Memory[BinaryToInteger(this.MAR)].substring(20);
            }
            else // if(IsJump && Ljump && !Rjump) or if there was not a jump at all
            {
                IsJump =false;
                Ljump = false;
                Rjump = false;
                this.MBR = this.Memory[BinaryToInteger(this.MAR)];
            }
        
            // For LHS instruction
            if(!this.MBR.substring(0,20).equals("XXXXXXXXXXXXXXXXXXXX"))
            {
                // IBR <- MBR(20:39)
                this.IBR = this.MBR.substring(20); 

                // IR <- MBR(0:7)
                this.IR = this.MBR.substring(0,8); 

                // MAR <- MBR(8:19)
                this.MAR = this.MBR.substring(8,20);

                int end = CONTROLLER(this.IR);

                if(end == 1)
                {
                    System.out.println("HALT");
                    break;
                }

                // if LHS is not a jump
                if(!IsJump)
                {
                    // IR <- IBR(0:7)
                    this.IR = this.IBR.substring(0,8);
                    
                    // MAR <- IBR(8:19)
                    this.MAR = this.IBR.substring(8);
                    
                    end = CONTROLLER(this.IR);
                    if(end ==1)
                        break;
                }
            }
            else // LHS is not a valid instruction
            {
                // IR <- MBR(20:27)
                this.IR = this.MBR.substring(20,28);

                // MAR <- MBR(28:39)
                this.MAR = this.MBR.substring(28);

                int end = CONTROLLER(this.IR);

                if(end ==1)
                    break;
            }

            if(!IsJump)
            {
                int intPC = BinaryToInteger(this.PC);
                intPC++;
                this.PC = IntegerToBinary(intPC).substring(28);// PC is 12 bits
            }

            System.out.println("IR  = " + this.IR);
            System.out.println("PC  = " + this.PC);
            System.out.println("MAR = " + this.MAR);
            System.out.println("IBR = " + this.IBR);
            System.out.println("AC  = " + this.AC);
            System.out.println("MQ  = " + this.MQ);
            System.out.println("MBR = " + this.MBR + "\n");
            
        }
    }

    // CONTROLLER has decode, control path, etc..
    // here we would do this by switch case 
    public int CONTROLLER(String op)
    {
        switch(op)
        {
            case "00001010":
                                this.LOAD_MQ();
                                return 0;
            case "00001001":
                                this.LOAD_MQ_MX(this.MAR);
                                return 0;
            case "00100001":
                                this.STOR_MX(this.MAR);
                                return 0;
            case "00000001":
                                this.LOAD_MX(this.MAR);
                                return 0;
            case "00000010":
                                LOAD_MINUS_MX(this.MAR);
                                return 0;
            case "00000011":
                                this.LOAD_ABSOLUTE_MX(this.MAR);
                                return 0;
            case "00000100":
                                this.LOAD_MINUS_ABSOLUTE_MX(this.MAR);
                                return 0;
            case "00001101":
                                this.JUMP_LHS(this.MAR);
                                return 0;
            case "00001110":
                                this.JUMP_RHS(this.MAR);
                                return 0;
            case "00001111":
                                this.JUMP_PLUS_LHS(this.MAR);
                                return 0;
            case "00010000":
                                this.JUMP_PLUS_RHS(this.MAR);
                                return 0;
            case "00000101":
                                this.ADD_MX(this.MAR);
                                return 0;
            case "00000111":
                                this.ADD_MODULUS_MX(this.MAR);
                                return 0;
            case "00000110":
                                this.SUB_MX(this.MAR);
                                return 0;
            case "00001000":
                                this.SUB_MODULUS_MX(this.MAR);
                                return 0;
            case "00001011":
                                this.MUL_MX(this.MAR);
                                return 0;
            case "00001100":
                                this.DIV_MX(this.MAR);
                                return 0;
            case "00010100":
                                this.LSH();
                                return 0;
            case "00010101":
                                this.RSH();
                                return 0;
            case "00010010":
                                this.STOR_LEFT_MX(this.MAR);
                                return 0;
            case "00010011":
                                this.STOR_RIGHT_MX(this.MAR);
                                return 0;
            case "00000000":
                                return 1;
            default:
                                return 0;
        }
    }
    public void LOAD_MQ()
    {
        this.AC = this.MQ;
    }

    public void LOAD_MQ_MX(String X)
    {
        int location = BinaryToInteger(X);
        if(location < 0)
            return;
        this.MBR = this.Memory[location];
        this.MQ = this.MBR;
    }

    public void STOR_MX(String X)
    {
        int location = BinaryToInteger(X);
        if(location < 0)
            return;
        this.MBR = this.AC;
        this.Memory[location] = this.MBR;
    }

    public void LOAD_MX(String X)
    {
        int location = BinaryToInteger(X);
        if(location < 0)
            return;
        this.MBR = this.Memory[location];
        this.AC = this.MBR;
    }

    // output i.e. Sign of number is changed by ALU
    public void LOAD_MINUS_MX(String X)
    {
        int location = BinaryToInteger(X);
        if(location < 0)
            return;
        this.MBR = this.Memory[location];
        int negative = -1*BinaryToInteger(this.MBR);
        this.AC = IntegerToBinary(negative);
    }

    // output i.e. Absolute of number is calculated by ALU
    public void LOAD_ABSOLUTE_MX(String X)
    {
        int location = BinaryToInteger(X);
        if(location < 0)
            return;
        this.MBR = this.Memory[location];
        int negative = Math.abs(BinaryToInteger(this.MBR));
        this.AC = IntegerToBinary(negative);
    }

    // output i.e. Sign of absolute of number is changed by ALU
    public void LOAD_MINUS_ABSOLUTE_MX(String X)
    {
        int location = BinaryToInteger(X);
        if(location < 0)
            return;
        this.MBR = this.Memory[location];
        int negative = -1*Math.abs(BinaryToInteger(this.MBR));
        this.AC = IntegerToBinary(negative);
    }

    public void JUMP_LHS(String X)
    {
        this.PC = X;
        IsJump = true;
        Ljump = true;
        Rjump = false;
    }    

    public void JUMP_RHS(String X)
    {
        this.PC = X;
        IsJump = true;
        Ljump = false;
        Rjump = true;
    }

    public void JUMP_PLUS_LHS(String X)
    {
        if(!(BinaryToInteger(this.AC)<0))
        {
            this.PC = X;
            IsJump = true;
            Ljump = true;
            Rjump = false;
        }
        else
            return;
    }

    public void JUMP_PLUS_RHS(String X)
    {
        if(!(BinaryToInteger(this.AC)<0))
        {
            this.PC = X;
            IsJump = true;
            Ljump = false;
            Rjump = true;
        }
        else
            return;
    }

    // ALU used again here to do addtion
    public void ADD_MX(String X)
    {
        this.MBR = this.Memory[BinaryToInteger(X)];
        String added_value = IntegerToBinary(BinaryToInteger(this.MBR)+BinaryToInteger(this.AC));
        //if(added_value.length()>= 40)
        //{
            // MSB go to AC
            // LSB go to MQ
            // I don't find how to exactly do that so I am leaving this part for now.
            //return;
        //}
        this.AC = added_value;
    }

    // ALU used again here to do addtion
    public void ADD_MODULUS_MX(String X)
    {
        this.MBR = this.Memory[BinaryToInteger(X)];
        String added_value = IntegerToBinary(Math.abs(BinaryToInteger(this.MBR))+BinaryToInteger(this.AC));
        //if(added_value.length()>= 40)
        //{
            // MSB go to AC
            // LSB go to MQ
            // I don't find how to exactly do that so I am leaving this part for now.
            //return;
        //}
        this.AC = added_value;
    }

    // ALU used again here to do substraction
    public void SUB_MX(String X)
    {
        this.MBR = this.Memory[BinaryToInteger(X)];
        String sub_value = IntegerToBinary(BinaryToInteger(this.AC) - BinaryToInteger(this.MBR));
        //if(sub_value.length()>= 40)
        //{
            // MSB go to AC
            // LSB go to MQ
            // I don't find how to exactly do that so I am leaving this part for now.
            //return;
        //}
        this.AC = sub_value;
    }

    // ALU used again here to do substraction
    public void SUB_MODULUS_MX(String X)
    {
        this.MBR = this.Memory[BinaryToInteger(X)];
        String sub_value = IntegerToBinary(BinaryToInteger(this.AC) - Math.abs(BinaryToInteger(this.MBR)));
        //if(sub_value.length()>= 40)
        //{
            // MSB go to AC
            // LSB go to MQ
            // I don't find how to exactly do that so I am leaving this part for now.
            //return;
        //}
        this.AC = sub_value;
    }

    // ALU is used for multiplication
    public void MUL_MX(String X)
    {
        this.MBR = this.Memory[BinaryToInteger(X)];
        int val = BinaryToInteger(this.MQ) * BinaryToInteger(this.MBR);
        String Svalue;
        if(val >= 0)
        {
            Svalue = IntegerToBinary(val);
            for(int i=Svalue.length();i<80;i++)
                Svalue = "0"+Svalue;             
        }
        else
        {
            Svalue = IntegerToBinary(-val);
            int i;
            for(i=Svalue.length();i<79;i++)
                Svalue = "0"+Svalue;
            if(i==79)
                Svalue = "1"+Svalue;
        }
        
        this.AC = Svalue.substring(0,40);
        this.MQ = Svalue.substring(40);
        
    }

    // ALU is used for division
    public void DIV_MX(String X)
    {
        this.MBR = this.Memory[BinaryToInteger(X)];
        int int_MBR = BinaryToInteger(this.MBR);
        int int_AC = BinaryToInteger(this.AC);
        
        if(int_MBR != 0)
        {
            if(int_AC/int_MBR >=0)
            {
                this.MQ = IntegerToBinary(int_AC / int_MBR);
                this.AC = IntegerToBinary(int_AC % int_MBR);
            }
            else
            {
                if(int_AC % int_MBR == 0)
                {
                    this.MQ = IntegerToBinary(int_AC / int_MBR);
                    this.AC = IntegerToBinary(int_AC % int_MBR);    
                }
                else
                {    
                    if(int_AC < 0)
                    {
                        this.MQ = IntegerToBinary(int_AC / int_MBR - 1);
                        this.AC = IntegerToBinary(int_AC - int_MBR * (int_AC / int_MBR - 1));
                    }
                    else
                    {
                        this.MQ = IntegerToBinary(int_AC / int_MBR );
                        this.AC = IntegerToBinary(int_AC - int_MBR * (int_AC / int_MBR));
                    }
                    // -7/4 = -7 - (-2)*4    7/-4 = 7 - (-1)*(-4) = 3
                    // -5/2 = -5 - (-3)*2    5/-2 = 5 + -(-2)*(-2) = 1
                }
            }
        }
        else
            return;
    }

    // ALU used
    public void LSH()
    {
        // in ALU it happens by shifting the bits to left
        int int_AC = BinaryToInteger(this.AC);
        this.AC = IntegerToBinary(int_AC * 2);
    }

    // ALU used
    public void RSH()
    {
        // in ALU it happens by shifting the bits to left
        int int_AC = BinaryToInteger(this.AC);
        this.AC = IntegerToBinary(int_AC / 2);
    }

    public void STOR_LEFT_MX(String X)
    {
        int location = BinaryToInteger(X);
        this.MBR = this.AC.substring(28);
        this.Memory[location] = this.Memory[location].substring(0,8) + this.MBR + this.Memory[location].substring(20);
    }

    public void STOR_RIGHT_MX(String X)
    {
        int location = BinaryToInteger(X);
        this.MBR = this.AC.substring(28);
        this.Memory[location] = this.Memory[location].substring(0,28) + this.MBR;
    }



    public static String IntegerToBinary(int integer)
    {
        String binary = Snull;
        if(integer >= 0)
        {
            binary = Integer.toBinaryString(integer);
            if(binary.length()>=40)
                binary = Snull;
            for(int i=binary.length();i<40;i++)
                binary = "0"+binary;
            return binary;
        }

        binary = Integer.toBinaryString(-integer);
        if(binary.length()>=40)
            binary = Snull;
        int i=0;
        for(i=binary.length();i<39;i++)
            binary = "0"+binary;
        if(i==39)
            binary = "1"+binary;
        return binary;
    }
    
    public static int BinaryToInteger(String X)
    {
        int integer = Integer.parseInt(X.substring(1),2);
        if(X.charAt(0) == '1')
        {
            return -integer;
        }
        return integer;
    }
    
    public String[] getMemory() {
        return Memory;
    }
    public String getAC() {
        return AC;
    }
    public String getIR() {
        return IR;
    }
    public String getMAR() {
        return MAR;
    }
    public String getMBR() {
        return MBR;
    }
    public String getPC() {
        return PC;
    }
    public String getMQ() {
        return MQ;
    }
    public String getIBR() {
        return IBR;
    }

    public void setAC(String aC) {
        AC = aC;
    }
    public void setIR(String iR) {
        IR = iR;
    }
    public void setMAR(String mAR) {
        MAR = mAR;
    }
    public void setMBR(String mBR) {
        MBR = mBR;
    }public void setPC(String pC) {
        PC = pC;
    }
    public void setMemory(String[] memory) {
        Memory = memory;
    }
    public void setMQ(String mQ) {
        MQ = mQ;
    }
    public void setIBR(String iBR) {
        IBR = iBR;
    }

    private boolean Ljump;
    private boolean Rjump;
    private boolean IsJump;
    private String[] Memory = new String[1000];
    private String MAR;
    private String PC = "000000000000";
    private String MBR;
    private String IR;
    private String AC;
    private String MQ;
    private String IBR;
    private static final String Snull = "0000000000000000000000000000000000000000";
}
/*
    ///////////////////////////////////////////////////////////////
    public static void main(String args[])
    {
        int i = 5;
        MUL_MX("");
    }
    //////////////////////////////////////////////////////////////

}

//*/