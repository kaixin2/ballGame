package PoolGame.Builder;

import java.util.HashMap;
import java.util.Map;

import PoolGame.Config.BallConfig;
import PoolGame.Items.Ball;
import PoolGame.Items.Ball.BallType;
import PoolGame.Strategy.GameReset;
import PoolGame.Strategy.PocketOnce;
import PoolGame.Strategy.PocketTwice;

/** The class that builds all the different balls */
public class BallBuilderDirector {
    private Map<String, BallBuilder> builders;

    /** Initialise a ball builder director with no knowledge of how to build balls */
    public BallBuilderDirector() {
        this.builders = new HashMap<>();
    }

    /**
     * Associate a key with a ball builder.
     * @param key A string to associate with a builder
     * @param builder The ball builder to associate with the provided key
     */
    public void register(String key, BallBuilder builder) {
        this.builders.put(key, builder);
    }

    /** Register the defaults for the ball builder director. */
    public void registerDefault() {
        this.register("white", new WhiteBallBuilder(BallType.CUEBALL, new GameReset()));
        this.register("red", new RedBallBuilder(BallType.NORMALBALL, new PocketOnce()));
        this.register("blue", new BlueBallBuilder(BallType.NORMALBALL, new PocketTwice()));
    }

    /**
     * Construct the ball based on the config
     * @param config The ball configuration
     * @return The ball instance configured with the config provided
     */
    public Ball construct(BallConfig config) {
        String key = config.getColour();
        BallBuilder builder = this.builders.get(key);
        if (builder == null) {
            return null;
        }
        builder.setXPos(config.getPositionConfig().getX());
        builder.setYPos(config.getPositionConfig().getY());
        builder.setXVel(config.getVelocityConfig().getX());
        builder.setYVel(config.getVelocityConfig().getY());
        builder.setMass(config.getMass());
        return builder.finaliseBuild();
    }
}
