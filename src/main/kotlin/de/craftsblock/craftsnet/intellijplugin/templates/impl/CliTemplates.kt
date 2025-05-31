package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class CliTemplates : CustomDialogTemplateAction(
    "CLI",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Command executor", AllIcons.FileTypes.Java, CraftsNetTemplates.COMMAND_EXECUTOR)
)