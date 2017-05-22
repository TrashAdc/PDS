package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * created by ryan v on 5/22/2017
 **/

//this class is for character selection
public class CharacterSelect {
    private int p1Pos, p2Pos;
    private float pos1, pos2;
    private Sprite knight, assassin, mage, knightInfo, assassinInfo, mageInfo;
    private Sprite p1Spr, p2Spr, p1Info, p2Info, p1Ind, p2Ind, presse, select, infoPress;
    private boolean p1Press, p2Press;

    public CharacterSelect(){
        p1Pos = 0;
        p2Pos = 0;
        pos1 = Window.camera.viewportWidth / 3.75f;
        pos2 = Window.camera.viewportWidth / 1.5f;

        knight = new Sprite(new Texture("core/assets/image/spr_knight_idle.png"));
        knight.setSize(5f, 5f * Window.yConst);
        assassin = new Sprite(new Texture("core/assets/image/spr_assassin_idle.png"));
        assassin.setSize(5f, 5f * Window.yConst);
        mage = new Sprite(new Texture("core/assets/image/spr_mage_idle.png"));
        mage.setSize(5f, 5f * Window.yConst);

        knightInfo = new Sprite(new Texture("core/assets/image/knightInfo.png"));
        knightInfo.setSize(30f, 50f * Window.yConst);
        assassinInfo = new Sprite(new Texture("core/assets/image/assassinInfo.png"));
        assassinInfo.setSize(30f, 50f * Window.yConst);
        mageInfo = new Sprite(new Texture("core/assets/image/mageInfo.png"));
        mageInfo.setSize(30f, 50f * Window.yConst);

        p1Spr = knight;
        p2Spr = knight;
        p1Info = knightInfo;
        p2Info = knightInfo;


        p1Ind = new Sprite(new Texture("core/assets/image/indicator1.png"));
        p1Ind.setSize(3f, 3f * Window.yConst);
        p2Ind = new Sprite(new Texture("core/assets/image/indicator2.png"));
        p2Ind.setSize(3f, 3f * Window.yConst);

        presse = new Sprite(new Texture("core/assets/image/presse.png"));
        presse.setSize(50f, 5f);

        select = new Sprite(new Texture("core/assets/image/selectachar.png"));
        select.setSize(30f, 5f);
        infoPress = new Sprite(new Texture("core/assets/image/specialinfo.png"));
        infoPress.setSize(50f, 5f);
    }

    public void movePos(){ //press keys to change characters
        if (Window.key.A && !p1Press) {
            p1Pos--;
            p1Press = true;
        }
        else if (Window.key.D && !p1Press) {
            p1Pos++;
            p1Press = true;
        }
        if (p1Pos < 0)
            p1Pos = 2;
        else if (p1Pos > 2)
            p1Pos = 0;

        if (Window.key.left && !p2Press) {
            p2Pos--;
            p2Press = true;
        }
        else if (Window.key.right && !p2Press) {
            p2Pos++;
            p2Press = true;
        }
        if (p2Pos < 0)
            p2Pos = 2;
        else if (p2Pos > 2)
            p2Pos = 0;


        if (!Window.key.A && !Window.key.D)
            p1Press = false;
        if (!Window.key.right && !Window.key.left)
            p2Press = false;

    }

    public void drawSelections(SpriteBatch batch){
        switch (p1Pos){
            case 0:
                p1Spr = knight;
                p1Info = knightInfo;
                break;
            case 1:
                p1Spr = assassin;
                p1Info = assassinInfo;
                break;
            case 2:
                p1Spr = mage;
                p1Info = mageInfo;
                break;
        }
        p1Spr.setPosition(pos1, Window.camera.viewportHeight / 2);
        p1Spr.draw(batch);

        switch (p2Pos){
            case 0:
                p2Spr = knight;
                p2Info =knightInfo;
                break;
            case 1:
                p2Spr = assassin;
                p2Info = assassinInfo;
                break;
            case 2:
                p2Spr = mage;
                p2Info = mageInfo;
                break;
        }
        p2Spr.setPosition(pos2, Window.camera.viewportHeight / 2);
        p2Spr.draw(batch);

        p1Ind.setPosition(pos1, Window.camera.viewportHeight / 1.5f);
        p1Ind.draw(batch);
        p2Ind.setPosition(pos2, Window.camera.viewportHeight / 1.5f);
        p2Ind.draw(batch);

        drawPresse(batch);

        infoPress.setPosition(5f, Window.camera.viewportHeight / 5f);
        infoPress.draw(batch);
        select.setPosition((Window.camera.viewportWidth / 2f) - (select.getWidth() / 2f), Window.camera.viewportHeight / 1.25f);
        select.draw(batch);

        if (Window.key.B){
            p1Info.setPosition(0f, -Window.camera.viewportHeight / 2 + 1f);
            p1Info.draw(batch);
        }
        if (Window.key.numpad2){
            p2Info.setPosition(Window.camera.viewportWidth / 2,  -Window.camera.viewportHeight / 2 + 1f);
            p2Info.draw(batch);
        }

    }

    public void drawPresse(SpriteBatch batch){
        presse.setPosition(5, Window.camera.viewportHeight / 10f);
        presse.draw(batch);
    }

    public int characterSelected(GameData.Player player){
        if (player == GameData.Player.PLAYER1){
            if (!Window.key.E)
                return -1;
            else
                return p1Pos;
        }

        if (player == GameData.Player.PLAYER2){
            if (!Window.key.enter)
                return -1;
            else
                return p2Pos;
        }

        return -1;
    }


}
