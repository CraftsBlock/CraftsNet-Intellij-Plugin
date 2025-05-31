package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class MiddlewareTemplates : CustomDialogTemplateAction(
    "Middlewares",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Middleware", AllIcons.FileTypes.Java, CraftsNetTemplates.MIDDLEWARE),
    TemplateKind("HTTP middleware", AllIcons.FileTypes.Java, CraftsNetTemplates.HTTP_MIDDLEWARE),
    TemplateKind("Websocket middleware", AllIcons.FileTypes.Java, CraftsNetTemplates.WEBSOCKET_MIDDLEWARE)
)