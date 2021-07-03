package com.hotels.datatools.support

import com.hotels.datatools.data.HotelDataException

data class CommandArguments(val input: String, val inputFormat: String, val output: String, val outputFormat: String) {

    companion object {

        fun parse(args: Array<String>): CommandArguments? {
            try {
                return CommandArguments(
                        input = findInputFile(args),
                        inputFormat = findInputFormat(args),
                        output = findOutputFile(args),
                        outputFormat = findOutputFormat(args)
                )
            } catch (e: Exception) {
                println("Usage: --input-format=[csv] --input=<source_path> --output-format=[json|xml] --output=<target_path>")
            }
            return null
        }

        private fun findInputFormat(args: Array<String>) = args.find { it.startsWith("--input-format=") }
                .extractRequiredValue("--input-format=[csv|...]")

        private fun findOutputFormat(args: Array<String>) = args.find { it.startsWith("--output-format=") }
                .extractRequiredValue("--output-format=[json|xml|...]")

        private fun findInputFile(args: Array<String>) = args.find { it.startsWith("--input=") }
                .extractRequiredValue("--input=<path>")

        private fun findOutputFile(args: Array<String>) = args.find { it.startsWith("--output=") }
                .extractRequiredValue("--output=<path>")


        private fun String?.extractRequiredValue(description: String): String {

            val parts = this?.split("=")
            if (parts?.size == 2) {
                return parts[1]
            }
            throw HotelDataException("required option $description")
        }
    }
}