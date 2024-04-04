package com.me.jackthegiant.sprites;

import static com.me.jackthegiant.GameMain.W_HEIGHT;
import static com.me.jackthegiant.GameMain.W_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class CloudsController {
    private float cameraY;
    private World world;
    Array<Cloud> clouds = new Array<>();
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

            clouds.add(new Cloud(world, randomX, lastCloudPositionY, name));
            lastCloudPositionY -= distance_between_clouds;
        }
    }

    private float getRandomX(float minX, float maxX) {
        return new Random().nextFloat() * (maxX - minX) + minX;
    }

    public void drawClouds(SpriteBatch batch) {
        for (Cloud cloud : clouds) {
            batch.draw(cloud, cloud.getX(), cloud.getY());
        }
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

    public void createAndArrangeNewClouds() {
        for (int i = 0; i < clouds.size; i++) {
            if ((clouds.get(i).getY() - W_HEIGHT / 2) > cameraY) {
                clouds.get(i).getTexture().dispose();
                clouds.removeIndex(i);
            }
        }

        if(clouds.size == 4) {
            createClouds();
        }
    }
}
