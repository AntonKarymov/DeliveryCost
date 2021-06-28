package delivery;

public class DeliveryCostDeterminer {
    //todo for easy implementation all methods public, static and enum inside class
    public enum LoadLevel {
        VERY_HIGHT_LEVEL(1.6),
        HIGHT_LEVEL(1.4),
        INCREASED_LEVEL(1.2),
        OTHER(1.0);

        private double multiplicator;

        LoadLevel(double multiplicator) {
            this.multiplicator = multiplicator;
        }
    }


    public static double determineDeliveryCost(int distanceInKM, boolean isBig, boolean isFragile, LoadLevel loadLevel) {
        double MINIMUM_DELIVERY_COST = 400;
        double finalCostInRub = multiplyCost(distanceCost(distanceInKM) + sizeCost(isBig) + fragileCost(isFragile, distanceInKM), loadLevel);

        return finalCostInRub < 400 ? MINIMUM_DELIVERY_COST : finalCostInRub;
    }

    public static double multiplyCost(int cost, LoadLevel loadLevel) {
        return cost * loadLevel.multiplicator;
    }

    public static int distanceCost(int distanceInKm) {
        if (distanceInKm <= 0) {
            throw new IllegalArgumentException("Distance should be > 0");
        }

        if (distanceInKm > 30) {
            return 300;
        } else if (distanceInKm > 10) {
            return 200;
        } else if (distanceInKm > 2) {
            return 100;
        } else {
            return 50;
        }
    }

    public static int sizeCost(boolean isBig) {
        return isBig ? 200 : 100;
    }

    public static int fragileCost(boolean isFragile, int distanceInKM) {
        if (isFragile && distanceInKM > 30) {
            throw new IllegalArgumentException("Fragile cargo distance should be <=30");
        }
        return isFragile ? 300 : 0;
    }
}

