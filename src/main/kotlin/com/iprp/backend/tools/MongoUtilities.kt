package com.iprp.backend.tools

import java.io.IOException


/**
 * Utilities for MongoDB like automated start.
 *
 * @author Kacper Urbaniec
 * @version 2020-10-20
 */
class MongoUtilities {

    companion object {
        fun start() {

            // Get operating system and build corresponding invokes
            val os = getOS()

            if (os == "win" || os == "uni") {
                // Build commands that invoke MongoDB start scripts
                val builder = ProcessBuilder()

                when(os) {
                    "win" -> builder.command(".\\tools\\run_mongodb.bat")
                    "uni" -> builder.command("bash", "tools/run_mongodb.sh")
                }

                try {
                    val process: Process = builder.start()
                    val exitVal = process.waitFor()
                    if (exitVal != 0) {
                        println("//=====")
                        println("|| Could not start MongoDB automatically")
                        println("|| Please do it manually")
                        println("\\\\======\n")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

        }

        // OS-Validator
        // See: https://stackoverflow.com/a/24861219/12347616

        private fun isWindows(os: String): Boolean {
            return os.contains("win")
        }

        private fun isMac(os: String): Boolean {
            return os.contains("mac")
        }

        private fun isUnix(os: String): Boolean {
            return os.contains("nix") || os.contains("nux") || os.contains("aix")
        }

        private fun isSolaris(os: String): Boolean {
            return os.contains("sunos")
        }

        private fun getOS(): String {
            val os = System.getProperty("os.name").toLowerCase()
            return when {
                isWindows(os) -> {
                    "win"
                }
                isMac(os) -> {
                    "osx"
                }
                isUnix(os) -> {
                    "uni"
                }
                isSolaris(os) -> {
                    "sol"
                }
                else -> {
                    "err"
                }
            }
        }
    }
}