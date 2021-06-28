package tests;

import delivery.DeliveryCostDeterminer;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;


@RunWith(DataProviderRunner.class)
@Epic(value = "Positive tests")
public class DeliveryCostDeterminerPositiveTests extends DeliveryCostDeterminerBaseTest {

    @DataProvider
    // 0 < distance <= 2 -> 50 rub
    // 2 < distance <= 10 -> 100 rub
    // 10 < distance <= 30 -> 200 rub
    // distance > 30 -> 300 rub
    public static Object[][] positiveDistanceTestData() {
        return new Object[][]{
                {1, 50, "Distance cost for: 0 <= distance <= 2 should be 50 rub"},
                {2, 50, "Distance cost for: 0 <= distance <= 2 should be 50 rub"},
                {3, 100, "Distance cost for: 2 < distance <= 10 should be 100 rub"},
                {10, 100, "Distance cost for: 2 < distance <= 10 should be 100 rub"},
                {11, 200, "Distance cost for: 10 < distance <= 30 should be 200 rub"},
                {30, 200, "Distance cost for: 10 < distance <= 30 should be 200 rub"},
                {31, 300, "Distance cost for: distance > 30 should be 300 rub"},
        };
    }

    @DataProvider
    // isBig = true -> 200 rub
    // isBig = false -> 100 rub
    public static Object[][] positiveSizeTestData() {
        return new Object[][]{
                {true, 200, "Big size cost should be 200 rub"},
                {false, 100, "Small size cost should be 100 rub"},
        };
    }

    @DataProvider
    // isFragile = true -> 300 rub if distance <= 30
    // isFragile = false -> 0 rub
    public static Object[][] positiveFragileTestData() {
        return new Object[][]{
                {true, 300, 20, "Fragile cargo cost should be 300 rub if distance <= 30"},
                {true, 300, 30, "Fragile cargo cost should be 300 rub if distance <= 30"},
                {false, 0, 20, "Not fragile cargo cost should be 0 rub"},
                {false, 0, 30, "Not fragile cargo cost should be 0 rub"},
                {false, 0, 50, "Not fragile cargo cost should be 0 rub"},
        };
    }

    @DataProvider
    public static Object[][] positiveMultiplyCostTestData() {
        return new Object[][]{
                {1, DeliveryCostDeterminer.LoadLevel.VERY_HIGHT_LEVEL, 1.6, "VERY_HIGHT_LEVEL should multiply to 1.6"},
                {1, DeliveryCostDeterminer.LoadLevel.HIGHT_LEVEL, 1.4, "HIGHT_LEVEL should multiply to 1.4"},
                {1, DeliveryCostDeterminer.LoadLevel.INCREASED_LEVEL, 1.2, "INCREASED_LEVEL should multiply to 1.2"},
                {1, DeliveryCostDeterminer.LoadLevel.OTHER, 1.0, "OTHER should multiply to 1.0"},
        };
    }

    @DataProvider
    public static Object[][] positiveMinimumDeliveryTestData() {
        return new Object[][]{
                {1, false, false, DeliveryCostDeterminer.LoadLevel.OTHER, 400, "Minimum delivery cost should be return"},
                {12, true, false, DeliveryCostDeterminer.LoadLevel.OTHER, 400, "Minimum delivery cost should be return"},
                {26, false, true, DeliveryCostDeterminer.LoadLevel.INCREASED_LEVEL, 720, "Delivery cost should be return 720"},
        };
    }

    @Test
    @Feature("Distance cost")
    @UseDataProvider("positiveDistanceTestData")
    public void positiveDistanceCost(int distanceInKm, int expectedDistanceCost, String caseDescription) {
        assertDistanceCost(distanceInKm, expectedDistanceCost, caseDescription);
    }

    @Test
    @Feature("Size cost")
    @UseDataProvider("positiveSizeTestData")
    public void positiveSizeCost(boolean isBig, int expectedSizeCost, String caseDescription) {
        assertSizeCost(isBig, expectedSizeCost, caseDescription);
    }

    @Test
    @Feature("Multiply cost  depend on load level")
    @UseDataProvider("positiveMultiplyCostTestData")
    public void positiveMultiplyCost(int cost, DeliveryCostDeterminer.LoadLevel loadLevel, double expectedCost, String caseDescription) {
        assertMultiplyCost(cost, loadLevel, expectedCost, caseDescription);
    }

    @Test
    @Feature("Fragile cost")
    @UseDataProvider("positiveFragileTestData")
    public void positiveFragileCost(boolean isBig, int distanceInKm, int expectedFragileCost, String caseDescription) {
        assertFragileCost(isBig, expectedFragileCost, distanceInKm, caseDescription);
    }

    @Test
    @Feature("Minimum delivery cost")
    @UseDataProvider("positiveMinimumDeliveryTestData")
    public void positiveMinimumDeliveryCost(int distanceInKm, boolean isBig, boolean isFragile, DeliveryCostDeterminer.LoadLevel loadLevel, double expectedDeliveryCost, String caseDescription) {
        assertDeliveryCost(distanceInKm, isBig, isFragile, loadLevel, expectedDeliveryCost, caseDescription);
    }
}
