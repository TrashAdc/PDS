package com.mygdx.game;

/**
 * created by ryan v on 4/23/2017
 **/
public class Assassin extends Character {
    public Assassin(GameData.Player player){
        super(player);
    }

    @Override
    public void St_Special(){
        switch(currentSpecial){
            case N_SPECIAL:
                shurikenThrow();
                break;
            case S_SPECIAL:
                katanaSlash();
                break;
            case D_SPECIAL:
                ambush();
                break;
            case U_SPECIAL:
                airBlade();
                break;
        }

    }

    /*neutral special-
      the assassin throws 3 shurikens in a horizontal direction,
      dealing small damage and knockback.
     */
    private void shurikenThrow(){

    }

    /*side special-
      the assassin uses his katana to slash through the opponent,
      dealing damage to the area around and slightly above him.
     */
    private void katanaSlash(){

    }

    /*down special-
      the assassin goes invisible for a short period, preparing for an attack.
      after a short time, the assassin reappears and dashes in a horizontal direction.
     */
    private void ambush(){

    }

    /*up special-
      the assassin grabs his blade and dashes diagonally in to the air, dealing damage and knockback
      to anything in his way.
     */
    private void airBlade(){

    }




}