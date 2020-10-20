package com.larbotech.karate;

import com.intuit.karate.junit5.Karate;

class RunnerIT {

    @Karate.Test
    Karate testAll() {
        return Karate.run().relativeTo(getClass());
    }

}