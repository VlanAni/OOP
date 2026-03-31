package ru.vladimirdev.snake.domain;

import ru.vladimirdev.snake.domain.enums.GameState;
import ru.vladimirdev.snake.domain.food.Apple;
import ru.vladimirdev.snake.domain.food.Food;
import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.snake.Snake;
import ru.vladimirdev.snake.services.Judge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Random;

public class GameField {
    private final int N;
    private final int M;
    private final int foodAmount;
    private final int wallAmount;

    private final Snake player;
    private final Map<Point, Food> foods;
    private final Set<Point> walls;

    private final Judge judge;
    private GameState state;

    public GameField(int N, int M, int foodAmount, int wallAmount, Judge judge) {
        if (N < 5 || M < 5 ||
                foodAmount < 1 || wallAmount < 0 ||
                judge == null) {
            throw new IllegalArgumentException("uncorrected arguments");
        }

        this.N = N;
        this.M = M;
        this.foodAmount = foodAmount;
        this.wallAmount = wallAmount;
        this.judge = judge;

        this.player = new Snake(N / 2, M / 2);
        this.foods = new HashMap<>();
        this.walls = new HashSet<>();
        this.state = GameState.PLAYING;

        genWalls();
        genFood();
    }

    public void update() {
        if (state != GameState.PLAYING) return;

        player.move();
        checkColl();

        if (state == GameState.PLAYING && judge.isVictory(this)) {
            state = GameState.VICTORY;
        }
    }

    public Snake getPlayer() {
        return player;
    }

    public GameState shareState() {
        return state;
    }

    private void checkColl() {
        Point head = player.myHead();

        if (head.shareX() < 0 || head.shareX() >= N || head.shareY() < 0 || head.shareY() >= M) {
            state = GameState.GAME_OVER;
            return;
        }

        if (walls.contains(head)) {
            state = GameState.GAME_OVER;
            return;
        }

        for (int i = 1; i < player.myTail().size(); i++) {
            if (head.equals(player.myTail().get(i))) {
                state = GameState.GAME_OVER;
                return;
            }
        }

        if (foods.containsKey(head)) {
            Food food = foods.get(head);
            food.affect(player);
            foods.remove(head);
            spawnFood();
        }
    }

    private void genWalls() {
        Random random = new Random();
        for (int i = 0; i < wallAmount; i++) {
            Point place;
            do {
                place = new Point(random.nextInt(N), random.nextInt(M));
            } while (walls.contains(place) ||
                    player.myTail().contains(place) ||
                    isNearHead(place));
            walls.add(place);
        }
    }

    private boolean isNearHead(Point p) {
        Point head = player.myHead();
        return Math.abs(p.shareX() - head.shareX()) <= 1 && Math.abs(p.shareY() - head.shareY()) <= 1;
    }

    private void genFood() {
        for (int i = 0; i < foodAmount; i++) {
            spawnFood();
        }
    }

    private void spawnFood() {
        Random random = new Random();
        Point place;
        do {
            place = new Point(random.nextInt(N), random.nextInt(M));
        } while (walls.contains(place) ||
                foods.containsKey(place) ||
                player.myTail().contains(place));

        Food newFood = new Apple(place);
        foods.put(place, newFood);
    }
}