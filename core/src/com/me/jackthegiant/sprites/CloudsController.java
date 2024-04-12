package com.me.jackthegiant.sprites;

import static com.me.jackthegiant.scenes.Gameplay.PPM;
import static com.me.jackthegiant.scenes.Gameplay.W_HEIGHT;
import static com.me.jackthegiant.scenes.Gameplay.W_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.me.jackthegiant.collecctables.Coin;
import com.me.jackthegiant.collecctables.Collectable;
import com.me.jackthegiant.collecctables.Life;

import java.util.Random;

public class CloudsController {
    private float cameraY;
    private World world;
    Array<Cloud> clouds = new Array<>();
    Array<Collectable> collectables = new Array<>();

    private float lastCloudPositionY = W_HEIGHT / 2f;

    public CloudsController(World world) {
        this.world = world;
        createClouds();
    }

    private void createClouds() {

        float distance_between_clouds = 250f;
        float x = W_WIDTH / 2f;

        float minX = W_WIDTH / 2f - 110;
        float maxX = W_WIDTH / 2f + 110;

        clouds.add(new Cloud(world, x, lastCloudPositionY, "cloud_0.png"));
        lastCloudPositionY -= distance_between_clouds;

        boolean prev_dark = false;
        boolean prev_left = false;

        for (int i = 0; i < 8; i++) {
            String name;

            if (new Random().nextBoolean() || prev_dark) {
                name = "cloud_" + i % 3 + ".png";
                prev_dark = false;
            } else {
                name = "dark_cloud.png";
                prev_dark = true;
            }

            float randomX;

            if (!prev_left) {
                randomX = getRandomX(maxX - 40, maxX);
                prev_left = true;
            } else {
                randomX = getRandomX(minX + 40, minX);
                prev_left = false;
            }

            Cloud cloud = new Cloud(world, randomX, lastCloudPositionY, name);
            clouds.add(cloud);
            lastCloudPositionY -= distance_between_clouds;

            if (!name.contains("dark") && i != 0) {

                int rand = new Random().nextInt(10);
                if (rand > 5) {
                    int ra2 = new Random().nextInt(2);

                    if (ra2 == 0) {
                        Life l1 = new Life(world, "life.png", cloud.getX() + cloud.getWidth() / 2f / PPM, cloud.getY() + 5, this);
                        collectables.add(l1);
                    } else {
                        Coin c1 = new Coin(world, "Coin_collectable.png", cloud.getX() + cloud.getWidth() / 2f / PPM, cloud.getY() + 5, this);
                        collectables.add(c1);
                    }
                }
            }
        }


    }

    public void remove(Collectable collectable) {
        collectables.removeValue(collectable, true);
    }

    private float getRandomX(float minX, float maxX) {
        return new Random().nextFloat() * (maxX - minX) + minX;
    }

    public void drawClouds(SpriteBatch batch) {
        for (Cloud cloud : clouds) {
            batch.draw(cloud, cloud.getX(), cloud.getY(), cloud.getWidth() / PPM, cloud.getHeight() / PPM);
        }
    }

    public void drawCollectable(SpriteBatch batch) {
        for (Collectable collectable : collectables) {
            collectable.update();
            batch.draw(collectable, collectable.getX(), collectable.getY(), collectable.getWidth() / PPM, collectable.getHeight() / PPM);
        }
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

    public void createAndArrangeNewClouds() {
        for (int i = 0; i < clouds.size; i++) {
            if ((clouds.get(i).getY() - W_HEIGHT / 2 / PPM) > cameraY) {
                clouds.get(i).getTexture().dispose();
                clouds.removeIndex(i);
            }
        }

        if (clouds.size == 4) {
            createClouds();
        }
    }

    public void removeOffScreenCollectables() {
        for (int i = 0; i < collectables.size; i++) {
            if ((collectables.get(i).getY() - W_HEIGHT / 2 / PPM) > cameraY) {
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
            }
        }
    }
}
