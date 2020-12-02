package com.sam.lib.logger.window

import java.io.*
import java.util.*

object FileUtil {

    fun readFile(input: InputStream, code: String): String {
        try {
            val buf = CharArray(input.available())
            val br = BufferedReader(InputStreamReader(input, code))
            val size = br.read(buf)
            br.close()
            input.close()
            return String(buf, 0, size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun readFile(input: File): String {
        try {
            return input.readText(Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun saveFile(input: InputStream, file: File) {
        try {
            if (file.exists()) {
                file.delete()
            }
            val fs = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length = 0
            while ({ length = input.read(buffer);length }() != -1) {
                fs.write(buffer, 0, length)
            }
            fs.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            input.close()
        }
    }

    fun saveFile(bytes: ByteArray, file: File) {
        if (file.exists()) {
            file.delete()
        }
        file.writeBytes(bytes)

    }

    fun saveUTF(text: String, file: File) {
        file.writeText(text, Charsets.UTF_8)
    }

    fun appendText(text: String, file: File) {
        file.appendText(text, Charsets.UTF_8)
    }

    fun clear(file: File) {
        file.writeText("", Charsets.UTF_8)
    }

    fun deleteFile(file: File) {
        try {
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun createParentDir(path: String?) {
        try {
            if (path != null) {
                val file = File(path)
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun createDir(path: String) {
        try {
            File(path).mkdirs()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readLines(input: InputStream, code: String): List<String> {
        val list = ArrayList<String>()
        var line: String? = null
        try {
            val br = BufferedReader(InputStreamReader(input, code))
            line = br.readLine()
            while (line != null) {
                list.add(line)
                line = br.readLine()
            }
            br.close()
            input.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }


}
