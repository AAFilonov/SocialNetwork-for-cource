package org.practical3.utils;


import org.junit.jupiter.api.Test;
import org.practical3.model.transfer.Answer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommonsTest {

    @Test
    void GivenAnswerWithNull_WhenSerializeFromJackson_ThenCorreclyDeserializeWithGson() throws IOException {
        Answer expected = new Answer("OK",null,2);

        String jacksonStr = Commons.toJson(expected);
        Answer actual = Commons.fromJson(jacksonStr,  Answer.class);

        assertEquals(expected.AffectedRows,actual.AffectedRows );

    }







}