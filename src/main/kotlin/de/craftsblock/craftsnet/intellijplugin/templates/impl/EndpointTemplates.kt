package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class EndpointTemplates : CustomDialogTemplateAction(
    "Endpoint",
    "Neuen Endpoint erstellen",
    AllIcons.FileTypes.Java,
    TemplateKind("Route", AllIcons.FileTypes.Java, CraftsNetTemplates.ROUTE),
    TemplateKind("Websocket", AllIcons.FileTypes.Java, CraftsNetTemplates.SOCKET)
)