package com.diegomalone.bakingapp.utils;

import com.diegomalone.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by malone on 13/02/18.
 */

public class ModelUtils {

    public static Step getNextStep(ArrayList<Step> stepList, Step currentStep) {
        if (stepList == null) return null;

        for (int i = 0; i < (stepList.size() - 1); i++) {
            if (stepList.get(i).getId() == currentStep.getId()) {
                return stepList.get(i + 1);
            }
        }

        return null;
    }

    public static Step getPreviousStep(ArrayList<Step> stepList, Step currentStep) {
        if (stepList == null) return null;

        for (int i = 1; i < stepList.size(); i++) {
            if (stepList.get(i).getId() == currentStep.getId()) {
                return stepList.get(i - 1);
            }
        }

        return null;
    }

    public static boolean hasNextStep(ArrayList<Step> stepList, Step currentStep) {
        return getNextStep(stepList, currentStep) != null;
    }

    public static boolean hasPreviousStep(ArrayList<Step> stepList, Step currentStep) {
        return getPreviousStep(stepList, currentStep) != null;
    }
}
