package generalTests;

import edu.touro.mco152.bm.observers.Observer;

public class MockObserver implements Observer {
    private boolean wasCalled;

    @Override
    public void update() {

    }

    @Override
    public void update(Object o) {
        wasCalled = true;
    }

    public boolean getWasCalled(){
        return wasCalled;
    }
}
