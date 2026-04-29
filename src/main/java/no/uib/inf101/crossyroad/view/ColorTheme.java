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
        Color.YELLOW,'l', 
        Color.BLUE, ' ', 
        new Color(0,0,0,0),
        'b', new Color(139, 69, 19),
        'e', Color.GRAY,
    'd', new Color(0, 100, 0),
    's', Color.GRAY);
    }

    public Color getCellColor(Character c){
        if(colorCorrMap.keySet().contains(c)){
            return colorCorrMap.get(c);
        }
        else{
            throw new IllegalArgumentException("Ingen tilgjengelige farger for tegnet:" + c);
        }
    }

}
