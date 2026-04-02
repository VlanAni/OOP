package ru.vladimirdev.snake.domain.field;

import ru.vladimirdev.snake.domain.enums.Direction;
import ru.vladimirdev.snake.domain.enums.GameState;
import ru.vladimirdev.snake.domain.food.Apple;
import ru.vladimirdev.snake.domain.food.Food;
import ru.vladimirdev.snake.domain.point.Point;
import ru.vladimirdev.snake.domain.snake.Snake;
import ru.vladimirdev.snake.domain.judge.Judge;

import java.util.*;

public class GameField {
    private final int N;
    private final int M;
    private final int foodAmount;
    private final int wallAmount;

    private final Random random = new Random();

    private final Snake player;
    private final Map<Point, Food> foods;
    private final Set<Point> walls;

    private final Judge judge;
    private GameState state;

    public GameField(int N, int M, int foodAmount,
                     int wallAmount, Judge judge) {
        if (N < 5 || M < 5 || judge == null) {
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

    public GameField(int N, int M, Judge judge,
                     Snake player, Set<Point> walls,
                     Map<Point, Food> foods) {
        if (N < 5 || M < 5 || player == null) {
            throw new IllegalArgumentException("uncorrected arguments");
        }

        this.N = N;
        this.M = M;
        this.judge = judge != null ? judge : field -> false;
        this.player = player;
        this.walls = walls != null ? walls : new HashSet<>();
        this.foods = foods != null ? foods : new HashMap<>();

        this.foodAmount = this.foods.size();
        this.wallAmount = this.walls.size();
        this.state = GameState.PLAYING;
    }

    public void update() {
        if (state != GameState.PLAYING) return;

        player.move();
        checkColl();

        if (state == GameState.PLAYING && judge.isVictory(this)) {
            state = GameState.VICTORY;
        }
    }

    public void changeDirection(Direction d) {
        player.switchDirect(d);
    }

    public Snake getPlayer() {
        return player;
    }

    public int getPlayerLength() {
        return player.getLength();
    }

    public GameState getState() { return state; }

    public Map<Point, Food> getFoods() {
        return Collections.unmodifiableMap(this.foods);
    }

    public Set<Point> getWalls() {
        return Collections.unmodifiableSet(this.walls);
    }

    private void checkColl() {
        Point head = player.getHead();

        if (head.getX() < 0 || head.getX() >= N || head.getY() < 0 || head.getY() >= M) {
            state = GameState.GAME_OVER;
            return;
        }

        if (walls.contains(head)) {
            state = GameState.GAME_OVER;
            return;
        }

        for (int i = 1; i < player.getBody().size(); i++) {
            if (head.equals(player.getBody().get(i))) {
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
        for (int i = 0; i < wallAmount; i++) {
            Point place;
            do {
                place = new Point(random.nextInt(N), random.nextInt(M));
            } while (walls.contains(place) ||
                    player.getBody().contains(place) ||
                    isNearHead(place));
            walls.add(place);
        }
    }

    private boolean isNearHead(Point p) {
        Point head = player.getHead();
        return Math.abs(p.getX() - head.getX()) <= 1 && Math.abs(p.getY() - head.getY()) <= 1;
    }

    private void genFood() {
        for (int i = 0; i < foodAmount; i++) {
            spawnFood();
        }
    }

    private void spawnFood() {
        Point place;
        do {
            place = new Point(random.nextInt(N), random.nextInt(M));
        } while (walls.contains(place) ||
                foods.containsKey(place) ||
                player.getBody().contains(place));

        Food newFood = new Apple();
        foods.put(place, newFood);
    }
}