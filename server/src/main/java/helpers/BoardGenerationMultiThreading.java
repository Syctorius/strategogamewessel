package helpers;

import enums.Tile;
import enums.TileType;
import interfaces.IGameUI;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BoardGenerationMultiThreading {
    IGameUI game;
    int width;
    int length;
    Tile[][] tilesInGame;
    TileType[][] tilesInThread;
    TileType tile = TileType.BLUELAND;
    MultiThreadingField threadBlue;
    MultiThreadingField threadRed;
    MultiThreadingField threadNeutral;
    private ExecutorService pool = Executors.newFixedThreadPool(3);


    public BoardGenerationMultiThreading() {

    }

    public Tile[][] createField(int width, int length) {
        this.width = width;
        this.length = length;
        tilesInGame = new Tile[width][length];
        threadBlue = new MultiThreadingField(TileType.BLUELAND, width, length, this);
        threadNeutral = new MultiThreadingField(TileType.NEUTRAL, width, length, this);
        threadRed = new MultiThreadingField(TileType.REDLAND, width, length, this);

        CompletableFuture.supplyAsync(() -> {

            threadBlue.run();
            return true;
        }, pool);
       CompletableFuture.supplyAsync(() -> {
            threadRed.run();
            return true;
        }, pool);
       CompletableFuture.supplyAsync(() -> {
            threadNeutral.run();
            return true;
        }, pool);


        CompletableFuture.allOf().join();

        return tilesInGame;


    }


    public synchronized void addTiles(Tile[][] tiles) {
        for (int i = 0; i < tilesInGame.length; ++i) {
            for (int j = 0; j < tilesInGame[i].length; ++j) {
                if (tilesInGame[i][j] == null) {
                    tilesInGame[i][j] = tiles[i][j];
                }
            }
        }


    }
}
