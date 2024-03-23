package org.example.businessLogic;

import org.example.dataModels.Polynomial;

import java.util.Map;

public interface Operations{
    public static Polynomial addition(Polynomial p1, Polynomial p2) throws Exception{
        if(p1==null || p2==null)
            throw new Exception("Nu se poate face adunarea!");

        //if(p1.getElements().isEmpty() || p2.getElements().)

        Polynomial result = new Polynomial();
        for (Integer key : p1.getElements().keySet()){
            float value= p1.getElements().getOrDefault(key, 0f);
            value+= p2.getElements().getOrDefault(key, 0f);

            if(value!=0)
                result.addElement(key, value);
        }

        for (Integer key : p2.getElements().keySet()){
            float value= p1.getElements().getOrDefault(key, 0f);
            value+= p2.getElements().getOrDefault(key, 0f);

            if(value!=0)
                result.addElement(key, value);
        }
        return result;
    }
    public static Polynomial subtraction(Polynomial p1, Polynomial p2) throws Exception{
        if(p1==null || p2==null)
            throw new Exception("Nu se poate face scaderea!");

        Polynomial result = new Polynomial();
        for (Integer key : p1.getElements().keySet()){
            float value= p1.getElements().getOrDefault(key, 0f);
            value-= p2.getElements().getOrDefault(key, 0f);

            if(value!=0)
                result.addElement(key, value);
        }

        for (Integer key : p2.getElements().keySet()){
            float value= p1.getElements().getOrDefault(key, 0f);
            value-= p2.getElements().getOrDefault(key, 0f);

            if(value!=0)
                result.addElement(key, value);
        }
        return result;
    }
    public static Polynomial multiplication(Polynomial p1, Polynomial p2) throws Exception{
        if(p1==null || p2==null)
            throw new Exception("Nu se poate face inmultirea!");

        Polynomial result = new Polynomial();
        for (Map.Entry<Integer, Float> entryP1 : p1.getElements().entrySet()) {
            Polynomial temp = new Polynomial();
            for(Map.Entry<Integer, Float> entryP2 : p2.getElements().entrySet()){
                int key = entryP1.getKey() + entryP2.getKey();
                int value = (int) (entryP1.getValue() * entryP2.getValue());
                if(value!=0)
                    temp.addElement(key, value);
            }
            result = Operations.addition(result,temp);
        }

        return result;
    }

    public static Polynomial derivate(Polynomial p1) throws Exception{
        if(p1==null)
            throw new Exception("Nu se poate face derivarea!");

        Polynomial result = new Polynomial();
        p1.getElements().remove(0); // case when the it s a constant value
        for(Map.Entry<Integer, Float> entry : p1.getElements().entrySet()){
            int key=entry.getKey();
            float value = entry.getValue() * key;
            result.addElement(key-1, value);
        }
        return result;
    }

    public static Polynomial integration(Polynomial p1) throws Exception{
        if(p1==null)
            throw new Exception("Nu se poate face derivarea!");

        Polynomial result = new Polynomial();
        for(Map.Entry<Integer, Float> entry : p1.getElements().entrySet()){
            int key = entry.getKey();
            float value = (entry.getValue() * (1f /(key + 1)));
            result.addElement(key+1, value);
        }
        return result;
    }

    public static Polynomial[] divide(Polynomial p1, Polynomial p2) throws Exception{
        if(p1==null || p2==null)
            throw new Exception("Nu se poate face impartirea!");

        if(p2.getElements().isEmpty())
            throw new Exception("Nu se poate face impartirea la 0!");
        Polynomial quotient = new Polynomial();
        Polynomial remainder = p1;

        while(!remainder.getElements().isEmpty() && remainder.getMaxPow() - p2.getMaxPow() >=0) {
            int powQuotient = remainder.getMaxPow() - p2.getMaxPow();
            float coefQuotient = remainder.getMaxValue()/p2.getMaxValue();

            quotient.addElement(powQuotient, coefQuotient);

            Polynomial temp1 = new Polynomial();
            temp1.addElement(powQuotient, coefQuotient);

            Polynomial temp2 = Operations.multiplication(temp1, p2);
            remainder = Operations.subtraction(remainder, temp2);
        }

        Polynomial[] array = new Polynomial[2];
        array[0]=quotient;
        array[1]=remainder;
        return array;
    }
}
