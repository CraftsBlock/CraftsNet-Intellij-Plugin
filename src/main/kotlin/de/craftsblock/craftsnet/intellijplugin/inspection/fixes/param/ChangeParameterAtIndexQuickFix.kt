package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param

class ChangeParameterAtIndexQuickFix(
    private val index: Int,
    override val paramType: String
) : ChangeParameterQuickFix(paramType) {

    override fun index(): Int = index

}