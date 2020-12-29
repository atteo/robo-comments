package org.atteo.robot_comments.gerrit;

import org.junit.jupiter.api.Test;

import com.google.gerrit.extensions.restapi.RestApiException;

public class GerritTest {
    @Test
    public void shouldConnect() throws RestApiException {
        var gerrit = new Gerrit("http://ci.twoje-zdrowie.pl/gerrit/", "spotbugs", "W$arM~z`e#3Nqa");

        //gerrit.upload();
    }
}
