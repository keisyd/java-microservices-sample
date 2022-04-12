package com.example.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationUnitTest {

    private static final long NONEXISTENT_ID = 1000;

    // @Autowired
    // private OrderRepository repository;
    @Before
    public void setUp() {
        // repository.clear();
    }

    @Test
    public void testCreditOfNegativeValue() throws Exception {

    }
}
