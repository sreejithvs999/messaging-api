package com.hotels.datatools.support

import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import java.io.BufferedWriter
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandArgumentsTest {



    @Test
    fun testCommandArguments_return_parsed_values() {


        val commandLine = arrayOf("--input-format=csv", "--input=source.csv", "--output-format=json", "--output=target.json")
        val options = CommandArguments.parse(commandLine)?: return

        assertEquals(options.input, "source.csv")
        assertEquals(options.inputFormat, "csv")
        assertEquals(options.output, "target.json")
        assertEquals(options.outputFormat, "json")

    }


}