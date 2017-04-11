package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

/**
 * created by ryan v on 4/10/2017
 **/
//a class to draw text to the screen more easily
public class TextWriter {

    public enum TextLayout {
        LEFT,
        CENTER,
        RIGHT
    }

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    private TextLayout layout;

    private BitmapFont font;
    private String text;


    public TextWriter(String fontPath, String str, int size, Color color){

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size; //size of the text
        fontParameter.color = color; //color of the text

        font = fontGenerator.generateFont(fontParameter);
        text = str; //what the text is

        layout = TextLayout.LEFT;

    }

    public void drawText(SpriteBatch batch, int posX, int posY){ //draw text
        int offset = (int) getOffset();
        font.draw(batch, text, posX - offset, posY);
    }
    public void drawText(SpriteBatch batch, String str, int posX, int posY){ //draw text + update the string
        text = str;
        int offset = (int) getOffset();
        font.draw(batch, text, posX - offset, posY);
    }

    public int getSize(){
        return fontParameter.size;
    } //get/set size
    public void setSize(int size){
        fontParameter.size = size;
        font = fontGenerator.generateFont(fontParameter);
    }

    public Color getColor() {
        return fontParameter.color;
    } //get/set color
    public void setColor(Color color) {
        fontParameter.color = color;
        font = fontGenerator.generateFont(fontParameter);
    }

    public String getText(){
        return text;
    } //get/set string
    public void setText(String str){
        text = str;
    }

    public void updateLayout (TextLayout textLayout) {
        layout = textLayout;
    } //updates layout to left, right or center
    private float getOffset(){
        GlyphLayout gl = new GlyphLayout();
        gl.setText(font, text);

        if (layout == TextLayout.RIGHT)
            return gl.width;
        else if (layout == TextLayout.CENTER)
            return gl.width / 2;
        else
            return 0;
    } //will return how much the text needs to be moved back to be put in position


}
