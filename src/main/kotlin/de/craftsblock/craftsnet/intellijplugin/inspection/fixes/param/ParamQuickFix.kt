package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param

import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.CustomQuickFix

abstract class ParamQuickFix(
    internal open val paramName: String,
    internal open val paramType: String
) : CustomQuickFix()