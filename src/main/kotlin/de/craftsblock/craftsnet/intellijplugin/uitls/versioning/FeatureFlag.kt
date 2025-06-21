package de.craftsblock.craftsnet.intellijplugin.uitls.versioning

enum class FeatureFlag(private val major: Int, private val minor: Int, private val patch: Int) {

    BASE(3, 3, 5),
    MIDDLEWARES(3, 4, 0),
    PRINTING_RETURN_VALUES(3, 4, 3),
    ;

    fun leastVersion(): Version {
        return Version(major, minor, patch)
    }

    fun isCompatible(version: Version): Boolean {
        if (major < version.major) return true
        if (major > version.major) return false

        if (minor < version.minor) return true
        if (minor > version.minor) return false

        return patch <= version.patch
    }

}