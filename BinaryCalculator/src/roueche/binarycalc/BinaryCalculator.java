package roueche.binarycalc;

import java.util.ArrayList;

public class BinaryCalculator
{
	
	/*	ADD BITFIELDS WITH TOO MANY CONDITION CHECKERS	*/
	
    public static BitField add(BitField a, BitField b)
    {
		if(null == a || null == b || a.size() != b.size()){
		    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
		}
		
		boolean carry = false;
		BitField out = new BitField(a.size());
		
		for(int i = 0; i < a.size(); i++) {
			
			/*
			 * This would be the too many conditions part. Basically, you have to add based on the condition
			 * of each index (true/false) and then set the output to the new value. You also have to keep
			 * track of whether or not you have to carry a value over (just like normal addition)
			 */
			
			if(a.get(i) == true && b.get(i) == true && carry == true) {
				out.set(i, true);
			} else if (a.get(i) == true && b.get(i) == true && carry == false || a.get(i) == true && b.get(i) == false && carry == true || a.get(i) == false && b.get(i) == true && carry == true) {
				out.set(i,  false);
				carry = true;
			} else if (a.get(i) == false && b.get(i) == false && carry == true || a.get(i) == false && b.get(i) == true && carry == false || a.get(i) == true && b.get(i) == false && carry == false) {
				out.set(i,  true);
				carry = false;
			} else {
				out.set(i, false);
			}
		}
		return out;
    }
    
    /*	SUBTRACT, NEGATE, AND COMPLEMENT	*/
    
    public static BitField subtract(BitField a, BitField b)
    {
		if(null == a || null == b || a.size() != b.size()){
		    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
		}
		BitField bNegated = negate(b);
		
		BitField result = add(a, bNegated);
		
		return result;
    }
    
    public static BitField negate(BitField a)
    {
    	BitField c = complement(a);
    	
    	BitField one = new BitField(a.size());
    	one.set(0,  true);
    	return add(c, one);
    }
    
    public static BitField complement(BitField a)
    {
    	BitField out = new BitField(a.size());
    	
    	for(int i = 0; i < a.size(); i++) {
    		out.set(i, !a.get(i));
    	}
    	
    	return out;
    }
    
    /*	MULTIPLY BITSETS	*/
    
    /*
     * For this I chose to create a list of BitFields that I could then call
     * from/add and then perform addition on. It was easier for me to think about
     */

    public static BitField multiply(BitField a, BitField b)
    {
		if(null == a || null == b || a.size() != b.size()){
		    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
		}
		
		BitField out = new BitField(a.size());			// output
		ArrayList<BitField> list = new ArrayList<>();	// make a list of BitFields to make setting easier later on
		
		for(int i = 0; i < b.size(); i++) {				// if 1 then add BitField to list
			if(b.get(i) == true) {
				list.add(a);
			}
			a = leftShift(a);							// left shift to offset
		}
		
		for(int i = 0; i < list.size(); i++) {
			out = add(list.get(i), out);				// adds the BitField at index i and the previous output BitField
		}
		
		return out;
    }
    
    /*	LEFT SHIFT BITFIELD MODIFIER	*/
    
    public static BitField leftShift(BitField a)
    {
    	BitField temp = new BitField(a.size());
    	
    	for(int j = a.size() - 1; j > 0; j--) {			// go through and set the BitField with a left shift
			temp.set(j, a.get(j - 1));
		}
    	temp.set(0, false);
    	
    	return temp;
    }
    
    /*	RIGHT SHIFT BITFIELD MODIFIER	*/
    
    public static BitField rightShift(BitField a)		
    {
    	BitField temp = new BitField(a.size());
    	
    	for(int j = 0; j < a.size() - 1; j++) {			// go through and set the BitField with a right shift
			temp.set(j, a.get(j + 1));
		}
    	temp.set(a.size() - 1, false);
    	
    	return temp;
    }
    
    /*	ZERO CHECKER HELPER METHOD	*/
    
    public static boolean zeroChecker(BitField a) 
    {
    	for(int i = 0; i < a.size(); i++) {
    		if(a.get(i) == true) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /*	ABSOLUTE VALUE HELPER METHOD	*/
    
    public static BitField[] absoluteValue(BitField a, BitField b) 
    {
    	BitField[] valuesArr = new BitField[2];				// chose to store in array just as a way of accessing it
    	
    	for(int i = 0; i < a.size(); i++) {
    		if(a.get(i))
    			a = negate(a);								// change to positive
    		
    		if(b.get(i))
    			b = negate(b);								// change to positive
    	}
    	
    	valuesArr[0] = a;
    	valuesArr[1] = b;
    	
    	return valuesArr;
    }
    
    /*	DIVIDE BITSETS	*/
    
    public static BitField[] divide(BitField a, BitField b)
    {
		if(null == a || null == b || a.size() != b.size()){
		    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
		}
		
		/*	CHECK FOR ALL ZEROS	*/
		
		if(zeroChecker(b) == true) {
			return null;
		}
		
		/*	INITIALIZE VARIABLES AND COMPUTE ABSOLUTE VALUE	*/
		
		BitField[] absolute = new BitField[2];				// created BitField[] as a way to access absoluteValue.get(i)
		
		absolute = absoluteValue(a, b);
		
		BitField divisor = new BitField(a.size() * 2);		// set to twice as many bits as quotient
		BitField remainder = new BitField(a.size() * 2);	// set to twice as many bits as quotient
		BitField quotient = new BitField(a.size());
		
		for(int i = 0; i < a.size(); i++) {
			remainder.set(i, absolute[0].get(i));
			divisor.set(i, absolute[1].get(i));
		}
		
		/*	COMPUTE DIVISION FOLLOWING DIAGRAM	*/
		
		for(int i = 0; i < a.size(); i++)					
			divisor = leftShift(divisor);					// left shift divisor to add needed expansion spaces
		
		for(int i = 0; i < a.size() + 1; i++) {
			remainder = subtract(remainder, divisor);		// REM = REM - DIV
			
			if(remainder.getMSB() == true) {				// REM < 0
				remainder = add(remainder, divisor);		// REM = REM + DIV
				quotient = leftShift(quotient);				// LSL Q
				quotient.set(0, false);						// Q[0] = 0
			} else {
				quotient = leftShift(quotient);				// LSL Q
				quotient.set(0, true);						// Q[0] = 1
			}
			divisor = rightShift(divisor);					// SHIFT DIV RIGHT
		}
		
		/*	CHECK DIFFERENT CONDITIONS	*/
		
		if(a.getMSB() || b.getMSB())						// negative or negative - quotient
            if(!quotient.getMSB())
                quotient = negate(quotient);
                        
        if(a.getMSB() && b.getMSB()) {						// negative and negative - quotient/remainder
            if(quotient.getMSB())
                quotient = negate(quotient);
           
            if(!remainder.getMSB())
                remainder = negate(remainder);
        }
                        
        if(!a.getMSB() && !b.getMSB()) {					// positive and positive - quotient/remainder
            if(quotient.getMSB())
                quotient = negate(quotient);
           
            if(remainder.getMSB())
                remainder = negate(remainder);                
        } 
        
        if(a.getMSB() && !b.getMSB())						// negative(a) and positive(b) - remainder
            if(!remainder.getMSB())
                remainder = negate(remainder);
        
        if(!a.getMSB() && b.getMSB())						// positive(a) and negative(b) - remainder
            if(remainder.getMSB())
                remainder = negate(remainder);
		
        /*	END CHECKS	*/
        
        BitField truncate = new BitField(a.size());			// truncate to trim off extra spaces
        for(int i = 0; i < a.size(); i++) {
        	truncate.set(i, remainder.get(i));
        }
		
		// Return both the quotient and the remainder
		BitField[] out = new BitField[2];
		out[0] = quotient; // quotient
		out[1] = truncate; // truncated remainder
		
		return out;
    }
}
