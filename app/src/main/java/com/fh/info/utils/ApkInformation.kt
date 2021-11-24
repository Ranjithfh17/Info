package com.fh.info.utils

import android.content.pm.ApplicationInfo
import com.fh.info.data.model.UsesFeatures
import com.jaredrummler.apkparser.ApkParser
import com.jaredrummler.apkparser.model.AndroidComponent
import com.jaredrummler.apkparser.model.CertificateMeta
import com.jaredrummler.apkparser.model.DexClass
import com.jaredrummler.apkparser.model.DexInfo
import net.dongliu.apk.parser.ApkFile
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.collections.ArrayList

object ApkInformation {

    /*fetch apk installed location*/

    fun ApplicationInfo.getGlEsVersion(): String {
        kotlin.runCatching {
            ApkParser.create(this).use {
                return it.androidManifest.apkMeta.glEsVersion.toString()
            }
        }.onFailure {
            ApkFile(sourceDir).use {
                return it.apkMeta.glEsVersion.toString()
            }
        }.getOrElse {
            throw Exception("Couldn't fetch GlES version due to ${it.localizedMessage}")
        }

    }


    /*fetch apk dex data*/

    fun ApplicationInfo.getDexData(): MutableList<DexInfo>? {
        kotlin.runCatching {
            ApkParser.create(this).use {
                return it.dexInfos
            }

        }.getOrElse {
            throw Exception("Couldn't fetch dex date due to ${it.localizedMessage}")
        }
    }

    /*get list of dex classes*/

    fun ApplicationInfo.getDexClasses(): MutableList<net.dongliu.apk.parser.bean.DexClass> {
        ApkFile(sourceDir).use {
            return it.dexClasses!!.toList() as MutableList<net.dongliu.apk.parser.bean.DexClass>
        }

    }


    /*get all xml files within an apk*/
    fun getXmlFiles(path: String?): MutableList<String> {
        val xmlFiles: MutableList<String> = ArrayList()
        var zipFile: ZipFile? = null
        try {
            zipFile = ZipFile(path)
            val entries: Enumeration<out ZipEntry?> = zipFile.entries()
            while (entries.hasMoreElements()) {
                val entry: ZipEntry? = entries.nextElement()
                val name: String = entry!!.name
                if (name.endsWith(".xml") && name != "AndroidManifest.xml") {
                    xmlFiles.add(name)
                }

            }

        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close()
                } catch (exception: java.lang.Exception) {
                    /*NO_OP*/
                }
            }
        }

        xmlFiles.sort()
        return xmlFiles
    }


    fun getAllGraphicsFiles(path: String?): MutableList<String> {
        val graphicFiles: MutableList<String> = ArrayList()
        var zipFile: ZipFile? = null
        try {
            zipFile = ZipFile(path)
            val entries: Enumeration<out ZipEntry?> = zipFile.entries()
            while (entries.hasMoreElements()) {
                val entry: ZipEntry? = entries.nextElement()
                val name: String = entry!!.name

                if (name.endsWith(".png")
                    || name.endsWith(".jpg")
                    || name.endsWith(".jpeg")
                    || name.endsWith(".gif")
                    || name.endsWith(".webp")
                    || name.endsWith(".svg")
                ) {
                    graphicFiles.add(name)
                }
            }

        } catch (exception: Exception) {
            exception.printStackTrace()

        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close()

                } catch (e: Exception) {
                    /*NO-OP*/
                }
            }
        }

        graphicFiles.sort()
        return graphicFiles
    }


    fun getAllExtraFiles(path: String?): MutableList<String> {
        val extraFile: MutableList<String> = ArrayList()
        var zipFile: ZipFile? = null
        try {
            zipFile = ZipFile(path)
            val entries: Enumeration<out ZipEntry?> = zipFile.entries()
            while (entries.hasMoreElements()) {
                val entry: ZipEntry? = entries.nextElement()
                val name: String = entry!!.name
                if (name.endsWith(".json")
                    || name.endsWith(".css")
                    || name.endsWith(".html")
                    || name.endsWith(".properties")
                    || name.endsWith(".js")
                    || name.endsWith(".tsv")
                    || name.endsWith(".txt")
                    || name.endsWith(".proto")
                    || name.endsWith(".java")
                    || name.endsWith(".bin")
                    || name.endsWith(".ttf")
                    || name.endsWith(".md")
                    || name.endsWith(".pdf")
                ) {
                    extraFile.add(name)
                }
            }


        } catch (exception: Exception) {
            exception.printStackTrace()

        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close()

                } catch (e: Exception) {
                    /*NO-OP*/
                }
            }
        }

        extraFile.sort()
        return extraFile
    }


    /*fetch broadcast info form apk*/
    fun ApplicationInfo.getApkMetaData(): Any? {
        kotlin.runCatching {
            ApkParser.create(this).use {
                return it.androidManifest.apkMeta
            }
        }.onFailure {
            ApkFile(sourceDir).use {
                return it.apkMeta
            }
        }.getOrElse {
            throw Exception("Couldn't fetch app info due to ${it.localizedMessage}")
        }
    }

    fun ApplicationInfo.getServices(): MutableList<AndroidComponent> {
        kotlin.runCatching {
            ApkParser.create(this).use {
                return it.androidManifest.services

            }
        }.getOrElse {
            throw Exception("Couldn't fetch services due to ${it.localizedMessage}")
        }
    }

    fun ApplicationInfo.getActivities(): MutableList<AndroidComponent> {
        kotlin.runCatching {
            ApkParser.create(this).use {
                return it.androidManifest.activities
            }
        }.getOrElse {
            throw Exception("Couldn't fetch activities due to ${it.localizedMessage}")

        }
    }

    fun ApplicationInfo.getProviders(): MutableList<AndroidComponent> {
        kotlin.runCatching {
            ApkParser.create(this).use {
                return it.androidManifest.providers
            }
        }.getOrElse {
            throw Exception("Couldn't fetch Providers due to ${it.localizedMessage}")

        }
    }

    fun ApplicationInfo.getPermissions(): MutableList<String> {
        kotlin.runCatching {
            ApkFile(sourceDir).use {
                return it.apkMeta.usesPermissions
            }
        }
            .onFailure {
                it.printStackTrace()

                ApkParser.create(sourceDir).use { apkParser ->
                    return apkParser.apkMeta.usesPermissions
                }
            }
            .getOrElse {
                throw Exception("Couldn't fetch permission due to ${it.localizedMessage}")

            }
    }


    fun ApplicationInfo.getFeatures(): MutableList<UsesFeatures> {
        kotlin.runCatching {
            ApkParser.create(sourceDir).use {
                val list = mutableListOf<UsesFeatures>()
                for (i in it.androidManifest.apkMeta.usesFeatures) {
                    list.add(UsesFeatures(i.name, i.required))

                }

                return list
            }

        }.onFailure {
            ApkFile(sourceDir).use {
                val list = mutableListOf<UsesFeatures>()
                for (i in it.apkMeta.usesFeatures) {
                    list.add(UsesFeatures(i.name, i.isRequired))
                }
                return list
            }
        }.getOrElse {
            throw Exception("Couldn't fetch  features due to ${it.localizedMessage}")
        }
    }


    fun ApplicationInfo.getCertificate(): CertificateMeta {
        kotlin.runCatching {
            ApkParser.create(sourceDir).use {
                return it.certificateMeta
            }

        }.getOrElse {
            throw Exception("Couldn't fetch  Certificate due to ${it.localizedMessage}")

        }
    }

    fun ApplicationInfo.getReceivers(): MutableList<AndroidComponent> {
        kotlin.runCatching {
            ApkParser.create(sourceDir).use {
                return it.androidManifest.receivers
            }

        }.getOrElse {
            throw Exception("Couldn't fetch  receivers due to ${it.localizedMessage}")

        }
    }


    fun ApplicationInfo.getBinaryXmlFiles(path: String): String {
        kotlin.runCatching {
            ApkParser.create(this).use {
                return it.transBinaryXml(path)
            }

        }.onFailure {
            ApkFile(sourceDir).use {
                return it.transBinaryXml(path)
            }
        }.getOrElse {
            throw Exception("Couldn't fetch  binary XML files due to ${it.localizedMessage}")

        }
    }


    fun ApplicationInfo.extractManifest(): String? {
        kotlin.runCatching {
            ApkParser.create(sourceDir).use {
                return it.manifestXml
            }
        }.onFailure { throwable ->
            throwable.printStackTrace()
            ApkFile(sourceDir).use {
                return it.manifestXml
            }
        }.getOrElse {
            throw Exception("Couldn't parse manifest file due to error : ${it.message}")
        }
    }


}
