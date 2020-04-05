import java.util.Random;
import java.util.Scanner;

public class Routine {

    private static final ExerciseOption[][] routineArray =
            {
                    // Group A
                    {
                            new ExerciseOption("tricep press ups", 4, 10),
                            new ExerciseOption("press ups", 6, 18),
                            new ExerciseOption("diamond press ups", 4, 10),
                            new ExerciseOption("lower chair press ups", 10, 20),
                            new ExerciseOption("upper chair press ups", 4, 10),
                            new ExerciseOption("shoulder press", 6, 15),
                            new ExerciseOption("shoulder dumbell raise", 6, 14),
                            new ExerciseOption("tricep dips", 10, 20),
                            new ExerciseOption("slow-mo press up (seconds)", 15, 35)
                    },
                    // Group B
                    {
                            new ExerciseOption("rope climbs or bar holds", 1, 4),
                            new ExerciseOption("pull ups where you touch your toes to the bar", 2, 6),
                            new ExerciseOption("monkey bars", 1, 1),
                            new ExerciseOption("pull ups either side of bar", 2, 5),
                            new ExerciseOption("wide pull ups", 3, 8),
                            new ExerciseOption("elastic band pulls across chest", 4, 12),
                            new ExerciseOption("band pull with band attached to tree", 10, 24),
                            new ExerciseOption("bicep curls", 8, 26)
                    }
            };

    private static Random random = new Random();
    private static Scanner keyboard = new Scanner(System.in);

    private static boolean inProgress = true;
    private static Group[] routine;
    private static int currentGroupIndex = 0;
    private static int sets = 1;

    public static void main(String[] args) {
        routine = initialiseRoutine();
        do {
            Exercise nextExercise = nextExercise(currentGroupIndex);
            displayExercise(nextExercise);
            waitForInput();
            incrementCurrentGroupIndex();
        } while (inProgress);
    }

    private static Group[] initialiseRoutine() {
        Group[] routine = new Group[routineArray.length];
        for (int i = 0; i < routineArray.length; i++) {
            routine[i] = new Group(routineArray[i]);
        }
        return routine;
    }

    private static Exercise nextExercise(int currentGroupIndex) {
        Group currentGroup = routine[currentGroupIndex];
        ExerciseOption exerciseOption = getRandomExerciseOption(currentGroup);
        return getExerciseFromOption(exerciseOption);
    }

    private static ExerciseOption getRandomExerciseOption(Group group) {
        ExerciseOption[] exerciseOptions = group.exerciseOptions;
        int exerciseIndex = random.nextInt(exerciseOptions.length);
        return exerciseOptions[exerciseIndex];
    }

    private static Exercise getExerciseFromOption(ExerciseOption option) {
        int upperBound = option.repCountUpperBound;
        int lowerBound = option.repCountLowerBound;
        int repCount = random.nextInt(upperBound + 1 - lowerBound) + lowerBound;
        return new Exercise(option.name, repCount);
    }

    private static void displayExercise(Exercise exercise) {
        System.out.println("Exercise : " + exercise.name + ", reps : " + exercise.repCount);
        System.out.println("Number of sets completed :" + sets++);
    }

    private static void waitForInput() {
        System.out.println("Continue? (y/n)");
        String next = keyboard.next();
        if (next.equals("n")) {
            inProgress = false;
        } else if (!next.equals("y")) {
            throw new IllegalArgumentException("Input '" + next + "' not recognised");
        }
    }

    private static void incrementCurrentGroupIndex() {
        currentGroupIndex++;
        if (currentGroupIndex >= routine.length) {
            currentGroupIndex = 0;
        }
    }

    private static class Group {
        public ExerciseOption[] exerciseOptions;

        public Group(ExerciseOption[] exerciseOptions) {
            this.exerciseOptions = exerciseOptions;
        }
    }

    private static class ExerciseOption {
        public String name;
        public int repCountLowerBound;
        public int repCountUpperBound;

        public ExerciseOption(String name, int repCountLowerBound, int repCountUpperBound) {
            this.name = name;
            this.repCountLowerBound = repCountLowerBound;
            this.repCountUpperBound = repCountUpperBound;
        }
    }

    private static class Exercise {
        public String name;
        public int repCount;

        public Exercise(String name, int repCount) {
            this.name = name;
            this.repCount = repCount;
        }
    }
}
