package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param

class ChangeFirstParameterQuickFix(
    override val paramType: String
) : ChangeParameterQuickFix(paramType) {

    override fun index(): Int = 0

}