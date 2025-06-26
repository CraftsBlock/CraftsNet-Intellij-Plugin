package de.craftsblock.craftsnet.intellijplugin.uitls.versioning

data class Version(
    var major: Int,
    var minor: Int,
    var patch: Int
) {

    constructor(version: List<Int>) : this(version[0], version[1], version[2])

    fun isFeatureFlagAvailable(flag: FeatureFlag): Boolean {
        return flag.isCompatible(this)
    }

    fun reset() {
        major = 0
        minor = 0
        patch = 0
    }

    override fun toString(): String {
        return "$major.$minor.$patch"
    }

}
