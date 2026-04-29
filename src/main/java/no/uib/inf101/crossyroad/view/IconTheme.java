package no.uib.inf101.crossyroad.view;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class IconTheme {

  Character color; 
  Map<Character, Image> iconMap; 

  public IconTheme() {
    this.iconMap = new HashMap<Character, Image>(); 

    iconMap = Map.of(
      'r', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/playerGif.gif")).getImage(), 
      'g', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/grassBlock.png")).getImage(), 
      'l', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/carLeft.png")).getImage(),
      'n', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/carRight.png")).getImage(),
      'e', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/highway.png")).getImage(),
      's', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/highway.png")).getImage(),
      'd', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/tree.png")).getImage(),
      'b', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/log.png")).getImage(),
      ' ', new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/null.png")).getImage());
  }

  /**
   * 
   * @param c the character which refers to a given image
   * @return the image of which the character is referring to 
   */
  public Image getCellImage(Character c){
        if(iconMap.keySet().contains(c)){
            return iconMap.get(c);
        }
        else{
            throw new IllegalArgumentException("Ingen tilgjengelige farger for dette tegnet");
        }
    }

}
