package no.uib.inf101.crossyroad.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorTheme{

    Character color;
    Map<Character,Color> colorCorrMap;

    public ColorTheme(){
        this.colorCorrMap = new HashMap<Character, Color>();
        colorCorrMap = Map.of('r', 
        Color.RED, 'g', 
        Color.GREEN, 'y', 
        Color.YELLOW,'b', 
        Color.BLUE, ' ', 
        Color.BLACK);
    }

    public Color getCellColor(Character c){
        if(colorCorrMap.keySet().contains(c)){
            return colorCorrMap.get(c);
        }
        else{
            throw new IllegalArgumentException("Ingen tilgjengelige farger for dette tegnet");
        }
    }

}
