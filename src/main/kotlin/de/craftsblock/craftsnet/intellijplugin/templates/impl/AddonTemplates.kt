package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class AddonTemplates : CustomDialogTemplateAction(
    "Addons",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Addon", AllIcons.FileTypes.Java, CraftsNetTemplates.ADDON),
    TemplateKind("Service loader", AllIcons.FileTypes.Java, CraftsNetTemplates.SERVICE_LOADER)
)