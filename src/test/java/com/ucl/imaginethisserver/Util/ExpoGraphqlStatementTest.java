package com.ucl.imaginethisserver.Util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpoGraphqlStatementTest {

    private final Logger logger = LoggerFactory.getLogger(ExpoGraphqlStatementTest.class);

    @Test
    void createQueryAccountIDStatement() {
        String result = ExpoGraphqlStatement.createQueryAccountIDStatement("testAccount");
        logger.info(result);
        assertTrue(result.contains("testAccount"));
        assertFalse(result.contains("$accountName"));
    }

    @Test
    void createUserInvitationForAccount() {
        String result = ExpoGraphqlStatement.createUserInvitationForAccount("testID", "test@test.com", "[VIEW]");
        logger.info(result);
    }
}