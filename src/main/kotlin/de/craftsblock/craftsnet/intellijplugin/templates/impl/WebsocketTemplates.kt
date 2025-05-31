package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class WebsocketTemplates : CustomDialogTemplateAction(
    "Websocket",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Extension", AllIcons.FileTypes.Java, CraftsNetTemplates.WEBSOCKET_EXTENSION)
)