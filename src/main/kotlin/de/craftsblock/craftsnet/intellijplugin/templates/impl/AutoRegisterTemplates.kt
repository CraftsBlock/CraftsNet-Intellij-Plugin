package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class AutoRegisterTemplates : CustomDialogTemplateAction(
    "Auto register",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Auto register handler", AllIcons.FileTypes.Java, CraftsNetTemplates.AUTO_REGISTER_HANDLER)
)