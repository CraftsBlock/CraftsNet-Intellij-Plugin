package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class LoggingTemplates : CustomDialogTemplateAction(
    "Logging",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Logger", AllIcons.FileTypes.Java, CraftsNetTemplates.LOGGER)
)