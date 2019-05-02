package com.ewarwick.teamcity.testPlugin.settings


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jetbrains.buildServer.serverSide.ServerPaths
import jetbrains.buildServer.serverSide.crypt.EncryptUtil
import jetbrains.buildServer.util.Hash
import jetbrains.buildServer.util.StringUtil

import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.Properties

data class potato(val foo: String) {

}

class stuff() {

    val potatos: ArrayList<potato> = arrayListOf()
    val moar: HashMap<String, potato> = hashMapOf()

}

class SandboxAdminSettings() {
    lateinit var serverPaths: ServerPaths

    constructor(serverPaths: ServerPaths) : this() {
        this.serverPaths = serverPaths
        init()
    }

    val CS_PROPERTIES_FILE = "/com.ewarwick/taggingplugin.json"

    var serverAddress = ""
    var username = ""
    var password: String? = ""
    var domain = ""
    var ignoreSsl = false
    var disabled = true
    var foo: stuff = stuff()
    fun init() {
        loadProperties()
    }

    @Throws(IOException::class)
    fun saveProperties() {
        val keyFile = propFile() ?: throw RuntimeException("Property file not found")
        var mapper = jacksonObjectMapper()

        val outFile = FileWriter(keyFile)
        mapper.writeValue(outFile, foo)
        outFile.close()
    }

    fun loadProperties() {
        val keyFile = propFile() ?: return

        var inFile: FileReader? = null
        try {
            inFile = FileReader(keyFile)
            var mapper = jacksonObjectMapper()
            val result = mapper.readValue<stuff>(inFile!!.readText())
            foo = result


            inFile.close()
        } catch (e: IOException) {
        } finally {
            try {
                inFile?.close()
            } catch (e: IOException) {
            }

        }
    }

    private fun propFile(): File? {
        val keyFile = File(serverPaths.configDir + CS_PROPERTIES_FILE)
        if (!keyFile.exists()) {
            try {
                val created = keyFile.createNewFile()
                if (!created) {
                }
            } catch (e: IOException) {
                return null
            }

        }
        return keyFile
    }

    private fun scramble(str: String?): String? {
        return if (StringUtil.isEmpty(str)) str else EncryptUtil.scramble(str!!)
    }

    private fun unscramble(str: String): String? {
        return if (StringUtil.isEmpty(str)) str else EncryptUtil.unscramble(str)
    }
}