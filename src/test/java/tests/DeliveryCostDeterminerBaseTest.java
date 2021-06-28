package tests;

import delivery.DeliveryCostDeterminer;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Step;
import org.junit.Assert;


public class DeliveryCostDeterminerBaseTest {
    //todo usualy creates separate class for working with Allure
    private void updateTestCaseName(String testCaseName){
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateTestCase(testResult -> testResult.setName(testCaseName));
    }

    @Step("Check distanceCost function with distance in km: {distanceInKm}")
    public void assertDistanceCost(int distanceInKm, int expectedDistanceCost, String caseDescription) {
        updateTestCaseName(caseDescription);
        Assert.assertEquals(caseDescription, expectedDistanceCost, DeliveryCostDeterminer.distanceCost(distanceInKm));
    }

    @Step("Check sizeCost function with isBig: {isBig}")
    public void assertSizeCost(boolean isBig, int expectedIsBigCost, String caseDescription) {
        updateTestCaseName(caseDescription);
        Assert.assertEquals(caseDescription, expectedIsBigCost, DeliveryCostDeterminer.sizeCost(isBig));
    }

    @Step("Check fragileCost function with isFragile: {isFragile}, distance in km: {distanceInKm}")
    public void assertFragileCost(boolean isFragile, int distanceInKm, int expectedFragileCost, String caseDescription) {
        updateTestCaseName(caseDescription);
        Assert.assertEquals(caseDescription, expectedFragileCost, DeliveryCostDeterminer.fragileCost(isFragile, distanceInKm));
    }

    @Step("Check determineDeliveryCost function with distance in km: {distanceInKm}, isBig: {isBig}, isFragile: {isFragile}, load level: {loadLevel}")
    public void assertDeliveryCost(int distanceInKm, boolean isBig, boolean isFragile, DeliveryCostDeterminer.LoadLevel loadLevel, double expectedDeliveryCost, String caseDescription) {
        updateTestCaseName(caseDescription);
        Assert.assertEquals(caseDescription, expectedDeliveryCost, DeliveryCostDeterminer.determineDeliveryCost(distanceInKm, isBig, isFragile, loadLevel), 0.0);
    }
    @Step("Check multiplyCost function with cost: {cost}, load level: {loadLevel}")
    public void assertMultiplyCost(int cost, DeliveryCostDeterminer.LoadLevel loadLevel, double expectedCostAfterMultiply, String caseDescription) {
        updateTestCaseName(caseDescription);
        Assert.assertEquals(caseDescription, expectedCostAfterMultiply, DeliveryCostDeterminer.multiplyCost(cost, loadLevel), 0.0);
    }
}
