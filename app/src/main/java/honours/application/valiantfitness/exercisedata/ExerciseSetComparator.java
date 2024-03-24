package honours.application.valiantfitness.exercisedata;

import java.util.Comparator;

public class ExerciseSetComparator implements Comparator<ExerciseSetData> {
    @Override
    public int compare(ExerciseSetData exerciseSetData, ExerciseSetData t1) {

     return   Double.compare(exerciseSetData.getWeight(),t1.getWeight());

    }
}
