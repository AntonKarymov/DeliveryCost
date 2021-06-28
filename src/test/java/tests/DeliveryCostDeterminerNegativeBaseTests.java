package tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@Epic(value = "Negative tests")
@RunWith(DataProviderRunner.class)
public class DeliveryCostDeterminerNegativeBaseTests extends DeliveryCostDeterminerBaseTest {
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @DataProvider
    public static Object[][] negativeDistanceTestData() {
        return new Object[][]{
                {0, "IllegalArgumentException should be thrown if distance <=0"},
                {-1, "IllegalArgumentException should be thrown if distance <=0"},
        };
    }

    @DataProvider
    public static Object[][] negativeFragileTestData() {
        return new Object[][]{
                {true, 31, "IllegalArgumentException should be thrown if cargo fragile and distanceInKm > 30"},
        };
    }

    @Test
    @Feature("Distance cost")
    @UseDataProvider("negativeDistanceTestData")
    public void negativeDistanceTest(int distanceInKm, String caseDescription) {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Distance should be > 0");
        assertDistanceCost(distanceInKm, 50, caseDescription);
    }

    @Test
    @Feature("Fragile cost")
    @UseDataProvider("negativeFragileTestData")
    public void negativeFragileTest(boolean isFragile, int distanceInKm, String caseDescription) {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Fragile cargo distance should be <=30");
        assertFragileCost(isFragile, distanceInKm, 50, caseDescription);
    }
}