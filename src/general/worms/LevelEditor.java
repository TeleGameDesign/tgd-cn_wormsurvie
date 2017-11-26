package general.worms;
import general.Main;
import general.ui.Button;
import general.ui.TGDComponent;
import general.ui.TextField;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelEditor extends BasicGameState{

    public static int ID = 43;
    public static Image texture;
    private GroundPolygon ground = new GroundPolygon(new Polygon(),0);
    private Button button;
    private Button newPolyGon;
    private TextField textField;
    private StateBasedGame game;
    private int currentType = 0;

    public static ArrayList<GroundPolygon> grounds = new ArrayList<>();
    private Button changeTexture;

    public LevelEditor()
	{

	}

	@Override
	public int getID() {
		return ID;
	}

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        game = stateBasedGame;
    }

    @Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        texture = new Image(ground.getImagePath());

        newPolyGon = new Button(gameContainer,Main.longueur-200,80, TGDComponent.AUTOMATIC,TGDComponent.AUTOMATIC);
        newPolyGon.setText("NOUVEAU POLYGONE");
        newPolyGon.setOnClickListener(new TGDComponent.OnClickListener() {
            @Override
            public void onClick(TGDComponent componenent) {
                grounds.add(ground);
                ground = new GroundPolygon(new Polygon(),currentType);

            }
        });



        changeTexture = new Button(gameContainer,Main.longueur-200,110, newPolyGon.getWidth(),TGDComponent.AUTOMATIC);
        changeTexture.setText("CHANGER TEXTURE");
        changeTexture.setOnClickListener(new TGDComponent.OnClickListener() {
            @Override
            public void onClick(TGDComponent componenent) {
                currentType ++;
                currentType = currentType%GroundPolygon.NB_IMAGE;
                ground.setImageType(currentType);

                try {
                    texture = new Image(ground.getImagePath());
                } catch (SlickException e) {
                    e.printStackTrace();
                }

            }
        });

        button = new Button(gameContainer,Main.longueur-200,50, newPolyGon.getWidth(),TGDComponent.AUTOMATIC);
        button.setText("SAUVEGARDER");
        button.setOnClickListener(new TGDComponent.OnClickListener() {
            @Override
            public void onClick(TGDComponent componenent) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Terrain.FOLDER_LEVEL+"/"+textField.getText()+".txt")));
                    grounds.add(ground);

                    for(int i=0;i<grounds.size();i++)
                    {
                        bw.write("new_polygone");
                        bw.newLine();
                        bw.write(grounds.get(i).getImageType()+"");
                        bw.newLine();

                        for(int j=0;j<grounds.get(i).getPolygon().getPoints().length/2;j++){

                            bw.write(""+grounds.get(i).getPolygon().getPoint(j)[0]);
                            bw.newLine();
                            bw.write(""+grounds.get(i).getPolygon().getPoint(j)[1]);
                            bw.newLine();
                        }
                    }
                    bw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        textField = new TextField(gameContainer,Main.longueur-200,20, newPolyGon.getWidth(),TGDComponent.AUTOMATIC);
        textField.setPlaceHolder("Nom du level");
        textField.setMaxNumberOfLetter(15);
        textField.setOnlyFigures(false);


    }

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
        try {
            button.update(arg0,arg1,arg2);
            newPolyGon.update(arg0,arg1,arg2);
            textField.update(arg0,arg1,arg2);
            changeTexture.update(arg0,arg1,arg2);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        texture.draw(0,0,Main.longueur,Main.hauteur);
        newPolyGon.render(container, game, g);
        button.render(container,game,g);
        textField.render(container,game,g);
        changeTexture.render(container,game,g);
        g.setColor(Color.white);





        if(ground.getPolygon().getPoints().length>0)
        {
            for(int i=0;i<ground.getPolygon().getPoints().length/2;i++){
                g.setColor(Color.white);
                float[] f = ground.getPolygon().getPoint(i);
                g.drawOval(f[0]-1,f[1]-1,5,5);
                g.setColor(Color.black);
                g.drawOval(f[0],f[1],3,3);
            }
        }

        for(int i=0;i<grounds.size();i++){
            g.draw(grounds.get(i).getPolygon());
        }

        /*
        for(int i=0;i<grounds.size();i++){
            g.drawImage(grounds.get(i).getOuter(),0,0);
            g.drawImage(grounds.get(i).getInner(),0,5);
        }*/


        if(ground.getPolygon()!=null && ground.getPolygon().getPoints().length>0)
            g.draw(ground.getPolygon());
	}

	public void mouseReleased(int button, int x,int y){

	}
	
	public void mousePressed(int button, int oldx,int oldy){
	    if(!this.button.contains(oldx,oldy) &&
                !newPolyGon.contains(oldx,oldy) &&
                !textField.contains(oldx,oldy) &&
                !changeTexture.contains(oldx,oldy)){
            ground.getPolygon().addPoint(oldx,oldy);
/*
            BufferedImage image2 = null;
            try {
                image2 = ImageIO.read(new File("images/Worms/Terrain/Grass.png"));
                setAlpha(image2, (byte) 125);

                BufferedImage image = ImageIO.read(new File(ground.getImagePath()));
                setAlpha(image, (byte) 125);

                Texture text = BufferedImageUtil.getTexture("", image);
                Texture text2 = BufferedImageUtil.getTexture("", image2);

                Image texture = new Image(text.getImageWidth(), text.getImageHeight());
                texture.setTexture(text);

                ground.setInner(texture);

                Image textureContour = new Image(text2.getImageWidth(), text2.getImageHeight() );
                textureContour.setTexture(text2);

                ground.setOuter(textureContour);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SlickException e) {
                e.printStackTrace();
            }*/


        }


	}


    public void setAlpha(BufferedImage image,byte alpha) {
        for (int cx=0;cx<image.getWidth();cx++) {
            for (int cy=0;cy<image.getHeight();cy++) {
                int rgba = new java.awt.Color(0 , 0, 0, 0).getRGB();
                if(!ground.contains(cx,cy)){
                    image.setRGB(cx, cy, rgba);
                }

            }

        }
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if(key==Input.KEY_BACK){
            if(ground.getPolygon().getPoints().length>0){
                removeLastPoint();
            }
        }else if(key == Input.KEY_ESCAPE){
            game.enterState(WormMenu.ID);
        }
    }

    private void removeLastPoint() {
        float[] f = ground.getPolygon().getPoints();
        Polygon p =new Polygon();
        for(int i=0;i<f.length/2;i++){
            p.addPoint(f[i],f[i+1]);
        }

    }

    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);

    }

    public static void reset() {
	    grounds.clear();
    }
}