package org.example.dataModels;

import org.example.businessLogic.Operations;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Polynomial implements Operations{
    TreeMap<Integer, Float> elements =new TreeMap<>(Collections.reverseOrder());
    public Polynomial() {}
    public TreeMap<Integer, Float> getElements() {
        return elements;
    }

    public void addElement(int key, float value) throws Exception{
        if(value==0)
            throw new Exception("Nu poti pune valori de 0!");
        if(key <0)
            throw new Exception("Nu poti pune puteri negative!");
        elements.put(key, value);
    }

    public String getFormat(){ // Returnam formatul de polinom din TreeMap
        if(elements.isEmpty()) {
            return "0";
        }

        StringBuilder answer = new StringBuilder();
        for (Map.Entry<Integer, Float> iterator: elements.entrySet()) {
            boolean isZero = iterator.getKey().equals(0);
            boolean isOne = iterator.getKey().equals(1);
            if(isZero){
                if(iterator.getValue()>0)
                    answer.append(String.format("+%.02f ", iterator.getValue()));
                else
                    answer.append(String.format("%.02f ", iterator.getValue()));
            }
            else if(isOne){
                if(iterator.getValue()==1)
                    answer.append("+X ");
                else if(iterator.getValue()==-1)
                    answer.append("-X ");
                else if(iterator.getValue()>0)
                    answer.append(String.format("+%.02fX ", iterator.getValue()));
                else
                    answer.append(String.format("%.02fX ", iterator.getValue()));
            }
            else {
                if(iterator.getValue()==1f)
                    answer.append(String.format("+X^%d ", iterator.getKey()));
                else if(iterator.getValue()==-1f)
                    answer.append(String.format("-X^%d ", iterator.getKey()));
                else if(iterator.getValue()>0)
                    answer.append(String.format("+%.02fX^%d ", iterator.getValue(), iterator.getKey()));
                else
                    answer.append(String.format("%.02fX^%d ", iterator.getValue(), iterator.getKey()));
            }
        }
        if(answer.charAt(0)=='+')
            answer.deleteCharAt(0);
        return answer.toString();
    }
    public int getMaxPow(){
        return elements.firstKey();
    }
    public float getMaxValue(){
        return elements.firstEntry().getValue();
    }
}
