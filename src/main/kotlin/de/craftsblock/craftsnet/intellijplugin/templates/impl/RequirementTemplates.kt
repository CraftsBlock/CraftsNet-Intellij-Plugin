package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class RequirementTemplates : CustomDialogTemplateAction(
    "Requirements",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Web requirement", AllIcons.FileTypes.Java, CraftsNetTemplates.WEB_REQUIREMENT),
    TemplateKind("Websocket requirement", AllIcons.FileTypes.Java, CraftsNetTemplates.WEBSOCKET_REQUIREMENT)
)