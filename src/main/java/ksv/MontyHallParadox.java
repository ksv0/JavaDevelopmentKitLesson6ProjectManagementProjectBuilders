package ksv;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.HashMap;
import java.util.Map;

public class MontyHallParadox {

    public static void main(String[] args) {
        int numberOfDoors = 500; // Можно изменить количество дверей
        int numberOfTests = 10000;
        MontyHallGame game = new MontyHallGame(numberOfDoors);

        Map<Integer, Result> results = new HashMap<>();

        for (int i = 1; i <= numberOfTests; i++) {
            boolean switchDoor = game.play();
            if (switchDoor) {
                results.put(i, Result.WIN);
            } else {
                results.put(i, Result.LOSE);
            }
            game.reset();
        }

        int wins = (int) results.values().stream().filter(result -> result == Result.WIN).count();
        int losses = numberOfTests - wins;

        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
    }

    @Data
    @AllArgsConstructor
    static class MontyHallGame {
        private final int numberOfDoors;
        private final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        private int prizeDoor;
        private int chosenDoor;
        private int[] openedDoors;

        public MontyHallGame(int numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
            prizeDoor = randomDataGenerator.nextInt(0, numberOfDoors - 1);
            chosenDoor = randomDataGenerator.nextInt(0, numberOfDoors - 1);
            openedDoors = generateOpenedDoors();
        }

        public boolean play() {
            return chosenDoor == prizeDoor;
        }

        public void reset() {
            prizeDoor = randomDataGenerator.nextInt(0, numberOfDoors - 1);
            chosenDoor = randomDataGenerator.nextInt(0, numberOfDoors - 1);
            openedDoors = generateOpenedDoors();
        }

        private int[] generateOpenedDoors() {
            int[] doors = new int[Math.max(0, numberOfDoors - 2)];
            int count = 0;
            for (int i = 0; i < numberOfDoors; i++) {
                if (i != prizeDoor && i != chosenDoor) {
                    doors[count++] = i;
                }
                if (count == doors.length) break;
            }
            return doors;
        }
    }

    enum Result {
        WIN,
        LOSE
    }
}