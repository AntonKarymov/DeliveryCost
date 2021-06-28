package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PositiveSuite.class, NegativeSuite.class} )
public class FullTestsSuite {
}